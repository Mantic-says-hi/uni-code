import numpy as np
import cv2 as cv
import os

class Blob:
    #Easier to create this object to remember if we've matched it and hold the values of x and w
    def __init__(blob,xPoint,width):
        blob.xPoint = xPoint
        blob.width = width
        blob.matched = False

def extractDigits(region):
    #Looking to keep track of digits so we can find 3
    numObjects = 0
    #Hashmap | Keys: X-Coordinates | Data: Image of digit
    digitImages = {}
    #Array   | X-Coordinates
    xCoords = []
    regionCopy = region.copy()
    #Convert to gray and do blob detection
    #region = cv.cvtColor(region, cv.COLOR_BGR2GRAY)
    region = cv.threshold(region, 0,255,cv.THRESH_OTSU)[1]
    _s, _, stats, _ = cv.connectedComponentsWithStats(region)
    
    for label in stats:
        x = label[cv.CC_STAT_LEFT]  
        y = label[cv.CC_STAT_TOP]
        h = label[cv.CC_STAT_HEIGHT]
        w = label[cv.CC_STAT_WIDTH]
        a = label[cv.CC_STAT_AREA]
        #Filter out all features that we might not want
        if (a >= 30 and w >= 5 and h >= 7):
            outDigit = regionCopy[y:y+h,x:x+w]           
            #Resize to match the size of the digits to be matched with
            outDigit = cv.resize(outDigit,(28,40), interpolation=cv.INTER_AREA)
            #Set to binary mask, easier to template match with binary
            outDigit = cv.threshold(outDigit, 0,255,cv.THRESH_OTSU)[1]
            digitImages[x] = outDigit
            #Remember the X-Coordinates
            xCoords.append(x)
            numObjects +=1

    
    return numObjects, digitImages, xCoords
  
def extractArea(imageSlice):
        #Array  | Sections of image we find interesting / usable
        areas = []
        imageSliceH,imageSliceW = imageSlice.shape
        centre = int(imageSliceW/2)
        #I know that I can do -+ 10 safely; since to get to this point the width must be > 27 
        workingImage = imageSlice[0:imageSliceH, (centre - 10) : (centre + 10)]
        workingImage = cv.inRange(workingImage,np.array([115]),np.array([255]))
        workingImage = cv.medianBlur(workingImage, 3)
        workingImage = cv.morphologyEx(workingImage, cv.MORPH_CLOSE, horizontalKernel, iterations=6)
        #Working image should now represent a strip with different height black and white bars
        _, _, stats, _  = cv.connectedComponentsWithStats(workingImage)
        for label in stats:
            y = label[cv.CC_STAT_TOP]
            h = label[cv.CC_STAT_HEIGHT]
            if  h >= 10 and h <= 50:
                regionImage = imageSlice[y:y+h, 0:imageSliceW]
                #Try to make image less squished for better template matching results
                if y - 5 >= 0 and y+h+5 < imageSliceH:
                    regionImage = imageSlice[y-5:y+h + 5, 0:imageSliceW]
                areas.append(regionImage)
        
        return areas

def initialProcessing(image):
    #Attributes and values deemed to be good from lots of testing with trackbars
    blurthresh = 7
    colourthresh = 100
    space = 25
    itr = 6
    image = cv.bilateralFilter(image,blurthresh,colourthresh,space)
    image = cv.medianBlur(image, 3)
    image = cv.morphologyEx(image, cv.MORPH_CLOSE, kernel, iterations=itr)
    #Image should resemble a blocky image with well defined colour intensity regions
    return image

def paintDarkSpots(processedImg, image):
    h,w = processedImg.shape
    intensityArray = []
    lowestPercent = 7
    #Get all intensity values in greyscale image then sort that array
    for x in range(h):
        for y in range(w):
            intensityArray.append(processedImg[x,y])
    intensityArray.sort()
    #Find the index of the lowest X percent
    thresholdIndex = (int((len(intensityArray)/100) * lowestPercent) - 1)
    #Take the intensity value of that index
    threshold = intensityArray[thresholdIndex]
    #Paint any parts of the image less than or equal to the threshold pure red
    for x in range(h):
       for y in range(w):
            if processedImg[x,y] <= threshold:
                image[x,y] = [0,0,255]

    return image

def findParallelBlobs(blobs):
    #Finds blobs that are parallel with eachother 
    #This is a property of the sign post, so hopefully we find it here
    xParallelStrips = []
    wParallelStrips = []
    blobArray = []
    #First simplify problem by making object for each blob
    for blob in blobs:
        x = blob[cv.CC_STAT_LEFT]
        w = blob[cv.CC_STAT_WIDTH]
        blobData = Blob(x,w)
        blobArray.append(blobData)

    tempX = []
    tempW = []
    index = 0
    #Since we made objects with the previous for loop...
    #It's easier to mark off what you have matched now
    for matcher in blobArray:
        #Ignore if Blob object already marked as matched
        if (not matcher.matched):
            for matchee in blobArray:
                if (matcher is not matchee) and (not matchee.matched):
                    xDifference = matcher.xPoint - matchee.xPoint
                    wDifference = matcher.width - matchee.width
                    #Similar X-Axis Point and width to find paralellism
                    if((xDifference > -12 and xDifference < 12) 
                    and(wDifference > -12 and wDifference < 12)):
                        matcher.matched = True
                        matchee.matched = True
                        tempX.append(matcher.xPoint)
                        tempX.append(matchee.xPoint)
                        tempW.append(matcher.width)
                        tempW.append(matchee.width)
            if matcher.matched:            
                xParallelStrips.append(min(tempX))
                wParallelStrips.append(min(tempW))
                tempX = []
                tempW = []
                index += 1
            

    return xParallelStrips, wParallelStrips, index

def extractDarkBobs(image):
    blobMask = cv.inRange(image,np.array([0,0,255]),np.array([0,0,255]))
    _, _, stats, _  = cv.connectedComponentsWithStats(blobMask)
    blobs = []
    for label in stats:
        h = label[cv.CC_STAT_HEIGHT]
        w = label[cv.CC_STAT_WIDTH]  
        #Filter out undersirable features
        if  w >= 27 and h >= 15:
            blobs.append(label)

    return blobs

def templateMatch(templates, potentialDigit, threshold):
    matches = 0
    for template in templates:
        res = cv.matchTemplate(potentialDigit,template,cv.TM_CCORR_NORMED)
        if res >= threshold:
            matches += 1
    percentMatched = matches / len(templates)

    return percentMatched        

def loadTemplates():
    templatePath  = '/home/student/digits/'
    templatePathExtra = '/home/student/matthew_matar_19499875/extraDigits/'
    #Hashmap | Key: Number of digit (10 & 11 for left & right) | Data: Array of images of templates 
    templates = {}
    zero = []
    one = []
    two = []
    three = []
    four = []
    five = []
    six = []
    seven = []
    eight = []
    nine = []
    left = []
    right = []
    templateNames = os.listdir(templatePath) + os.listdir(templatePathExtra)
    for name in templateNames:
        #Ensure file is a JPG
        if ".jpg" in  name:
            templateImg = cv.imread(templatePath + name,0)
            #If file name is not in the digits folder, look in the digitsExtra folder
            if(isinstance(templateImg,type(None))):
               templateImg = cv.imread(templatePathExtra + name,0)
            #Image to compare is binary so we ensure this is also binary
            templateImg = cv.threshold(templateImg, 0,255,cv.THRESH_OTSU)[1]
            #Ensure correct dimensions for better template matching
            templateImg = cv.resize(templateImg,(28,40), interpolation=cv.INTER_AREA)
            #Place image into array based on filename containing a word
            if "Zero" in name:
                zero.append(templateImg)
            if "One" in  name: 
                one.append(templateImg)
            if "Two" in  name: 
                two.append(templateImg)
            if "Three" in  name: 
                three.append(templateImg)    
            if "Four" in  name: 
                four.append(templateImg)
            if "Five" in  name: 
                five.append(templateImg)
            if "Six" in  name: 
                six.append(templateImg)
            if "Seven" in  name: 
                seven.append(templateImg)
            if "Eight" in  name: 
                eight.append(templateImg)
            if "Nine" in  name: 
                nine.append(templateImg)
            if "Left" in name:
                left.append(templateImg)
            if "Right" in name:
                right.append(templateImg)
    templates[0] = zero
    templates[1] = one
    templates[2] = two
    templates[3] = three
    templates[4] = four
    templates[5] = five
    templates[6] = six
    templates[7] = seven
    templates[8] = eight
    templates[9] = nine
    templates[10] = left
    templates[11] = right

    return templates

def getOutputString(region):
    numObjects,digitImages,xKeys = extractDigits(region)
    buildingString = ""
    if numObjects > 4:
        #DIGITS#             0     1     2     3     4     5     6     7     8     9
        thresholdDigit = [0.725,0.725,0.645,0.725,0.675,0.805,0.825,0.565,0.785,0.850]
        #DIRECTION#           LEFT  RIGHT
        thresholdDirection = [0.650,0.675]
        #Get proper order since comes in from first occuring Y-Value
        xKeys.sort()
        #Last image 'should' be the arrow 
        last = max(xKeys)
        for key in xKeys:
            number = 0
            digitValue = ""
            percentMatched = 0
            #If the image inside the hashmap at this key matches with a template...
            #the value of it will be appended to the output string
            if key != last:
                while number < 10:
                    result = templateMatch(templates[number], digitImages.get(key), thresholdDigit[number])
                    if result > 0 and result > percentMatched:
                        digitValue = str(number)
                    number += 1
                buildingString += digitValue
            #Only look for a match for direction when we logically need to
            else:
                #Gets rid of false matches from objects outside of the sign
                if len(buildingString) > 3:
                    buildingString = buildingString[-3:]
                #Make sure you have a building number before doing direction
                if len(buildingString) > 0:
                    left = templateMatch(templates[10], digitImages.get(key), thresholdDirection[0])
                    right = templateMatch(templates[11], digitImages.get(key), thresholdDirection[1])
                    if right > left:
                        buildingString += " to the right"
                    elif left > right:
                        buildingString += " to the left"
                    #Don't care if they are equal
    return buildingString

imagesPath = '/home/student/test/task2/'
savePathTask2 = "/home/student/matthew_matar_19499875/output/task2/"
filenames = os.listdir(imagesPath)
#Sort Filenames to ensure I read them in the right order
filenames = sorted(filenames)

#Horizontal biased kernel
horizontalKernel = np.ones((3,21), np.uint8)
#Standard kernel
kernel = np.ones((3,5), np.uint8)

fileNumber = 0
detections = 0
templates = loadTemplates()
numFiles = len(filenames)
for imageFile in filenames:
    fileNumber += 1
    #Let user know how the program is pregressing
    print("PROCESSING IMAGE: " + imageFile + "    (" + str(fileNumber) + " / " +  str(numFiles) + ")")
    
    img = cv.imread(imagesPath + imageFile)
    gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    
    outputString = ""

    processedImg = initialProcessing(gray)
    img = paintDarkSpots(processedImg, img)
    blobs = extractDarkBobs(img)
    if len(blobs) > 1:
        xCoords, wLengths, indexLength = findParallelBlobs(blobs)
        if(indexLength > 0):
            #Take a gamble and only use the longest width imageSlice : Informed decision
            longest = max(wLengths)
            for i in range(indexLength):
                if(wLengths[i] == longest):
                    h,w = gray.shape
                    #Extend out incase of slight rotation we still get all digits and arrows
                    #Also make sure not to go out of bounds of the image
                    if(xCoords[i] - 7 >= 0 and xCoords[i] + wLengths[i] + 7 <= w):
                        signSlice = gray[0:h,(xCoords[i] - 7):(xCoords[i] + wLengths[i] + 7)]
                    elif(xCoords[i] + wLengths[i] <= w ):
                        signSlice = gray[0:h,(xCoords[i]):(xCoords[i] + wLengths[i])]
                    else:
                        signSlice = gray[0:h,(xCoords[i]):(w)]
                    
    interestingRegions = extractArea(signSlice)                
    #For each region we detect try to get building number and directional data from it
    for region in interestingRegions:
        if region is not None:
            buildingNumber = getOutputString(region)
            if len(buildingNumber) > 2:
                outputString = outputString + ("Bulding " + buildingNumber + '\n')

    if signSlice is not None:
        detections += 1
        cv.imwrite(savePathTask2 + 'DetectedArea' + str(fileNumber).zfill(2)+ '.jpg', signSlice)
        #Write the bulding number to the file
        textOutput = open(savePathTask2 + "BuildingList" + str(fileNumber).zfill(2)+ '.txt' , "w")
        textOutput.write(outputString)
        textOutput.close()
        if len(outputString) == 0:
            detections -= 1
    
percentDetected = int((detections/fileNumber) * 100)     
print("TASK 2  COMPLETE: " + str(detections) + "/" + str(fileNumber) + " images have 1 or more buildings labeled ( " + str(percentDetected) + "% )" )        