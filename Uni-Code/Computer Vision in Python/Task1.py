import numpy as np
import cv2 as cv
import os

def extractDigits(region):
    #Looking to keep track of digits so we can find 3
    numDigits = 0
    #Hashmap | Key: X-Coordinates | Data: Image of digit
    digitImages = {}
    #Array   | X-Coordinates
    xCoords = []
    regionCopy = region.copy()
    #Convert to gray and do blob detection
    region = cv.cvtColor(region, cv.COLOR_BGR2GRAY)
    region = cv.threshold(region, 0,255,cv.THRESH_OTSU)[1]
    numLabels, _, stats, _ = cv.connectedComponentsWithStats(region)
    for label in stats:
        area = label[cv.CC_STAT_AREA]
        #Filter out noisy regions | Anything 'too' small
        if area < 25:
            numLabels -= 1   
    for label in stats:
        x = label[cv.CC_STAT_LEFT]  
        y = label[cv.CC_STAT_TOP]
        h = label[cv.CC_STAT_HEIGHT]
        w = label[cv.CC_STAT_WIDTH]
        a = label[cv.CC_STAT_AREA]
        #Filter out all features that we might not want
        #Num Labels less than 6 since we might get some blobs due to glare
        if (numLabels > 3 and numLabels < 6 and w/h > 0.225 
            and w/h < 0.850 and w >= 5 and h >= 10 and a >= 25):
            outDigit = regionCopy[y:y+h,x:x+w]           
            #Resize to match the size of the digits to be matched with
            outDigit = cv.resize(outDigit,(28,40), interpolation=cv.INTER_AREA)
            outDigit = cv.cvtColor(outDigit, cv.COLOR_BGR2GRAY)
            #Set to binary image, easier to template match with binary
            outDigit = cv.threshold(outDigit, 0,255,cv.THRESH_OTSU)[1]
            #Place the data with x-coord as the key | Remember the keys with xCoords
            digitImages[x] = outDigit
            xCoords.append(x)
            numDigits +=1  
    
    return numDigits, digitImages, xCoords
  
def imageProcessing(alternateIteration, image, crossing):
    #Histogram equalisation, CLAHE is good for reducing the glare
    cla = cv.createCLAHE(clipLimit=52.5, tileGridSize=(9,3))
    image = cla.apply(image)
    #Close and Cross image to help with our identification with standard kernel
    image = cv.morphologyEx(image, cv.MORPH_CLOSE, kernel, iterations=1)
    image = cv.morphologyEx(image, cv.MORPH_CROSS, kernel, iterations=crossing)
    #Final Close with a alternate kernal to try merge the digits in the image together
    image = cv.morphologyEx(image, cv.MORPH_CLOSE, alternateKenral, alternateIteration)
    #Create binary mask to be returned to do blob detection with
    image = cv.threshold(image, 0,255,cv.THRESH_OTSU)[1]

    return image

def extractArea(mask,region,outimage):
    #Array  | Hold images of regions we determine to be interesting
    region = []
    _, _, stats, _  = cv.connectedComponentsWithStats(mask)
    for label in stats:
        x = label[cv.CC_STAT_LEFT]  
        y = label[cv.CC_STAT_TOP]
        h = label[cv.CC_STAT_HEIGHT]
        w = label[cv.CC_STAT_WIDTH]  
        #Filter out undersirable features
        if (w/h > 1.05 and w/h < 2.5) and w >= 27 and h >= 15 and h <= 75 and w <=200:
            regionImage = outimage[y:y+h,x:x+w] 
            region.append(regionImage)
            
    return  region

def templateMatch(templates, potentialDigit, threshold):
    matches = 0
    #Returns percent oftemplates that matched with the input image; with the given threshold value
    for template in templates:
        res = cv.matchTemplate(potentialDigit,template,cv.TM_CCORR_NORMED)
        if res >= threshold:
            matches += 1
    percentMatched = matches / len(templates)

    return percentMatched        

def loadTemplates():
    #Take digits from two locations | Given digits | Self made digits
    templatePath  = '/home/student/digits/'
    templatePathExtra = '/home/student/matthew_matar_19499875/extraDigits/'
    #HashMap | Key: Number of digit | Data: Array of images of digit templates 
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
    templateNames = os.listdir(templatePath) + os.listdir(templatePathExtra)
    for name in templateNames:
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

    return templates

def operation(mask, alternateMorphology,crossing):
    regionImages = []
    xKeys = []
    digitImages = {}
    goodMatch = False
    detectedArea = None
    while not goodMatch and alternateMorphology < 10:
        #Do image processing
        mask = imageProcessing(alternateMorphology, mask, crossing)
        #Identify regions
        regionImages = extractArea(mask, regionImages, outimage)
        for region in regionImages:
            #Attempt to find and extract digits
            numDigits,potentialMatch,potentialKeys = extractDigits(region)
            #High posibility it is the digits if numDigits = 3 OR 4
            if numDigits == 3 or numDigits == 4:
                #Stop the loop
                goodMatch = True
                #Remember the supposed dectected region
                detectedArea = region
                xKeys = potentialKeys
                digitImages = potentialMatch
        #We exit the loop once this gets to 10 since the image will become super dialated
        #Confident to just give up on this image if nothing is found before this
        alternateMorphology+=1
    return xKeys, digitImages, goodMatch , detectedArea

imagesPath = '/home/student/test/task1/'
savePathTask1 = "/home/student/matthew_matar_19499875/output/task1/"
filenames = os.listdir(imagesPath)
#Sort Filenames to ensure I read them in the right order
filenames = sorted(filenames)

#Alternate kernel
alternateKenral = cv.getStructuringElement(cv.MORPH_RECT, (3,1))
#Standard kernel
kernel = np.ones((3,3), np.uint8)

#More varibales to be setup outside of file loop
fileNumber = 0
detections = 0
templates = loadTemplates()
numFiles = len(filenames)
for imageFile in filenames:
    fileNumber += 1
    print("PROCESSING IMAGE: " + imageFile + "    (" + str(fileNumber) + " / " +  str(numFiles) + ")")

    img = cv.imread(imagesPath + imageFile)
    outimage = img.copy()
    
    gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    mask = gray.copy()
    
    
    alternateMorphology = 3
    crossing = 5
    xKeys, digitImages, goodMatch, detectedArea = operation(mask,alternateMorphology,crossing)
    if not goodMatch:
        #Try Again with looser closing and crossing if we find no good match
        alternateMorphology = 1
        crossing = 3
        xKeys, digitImages, goodMatch, detectedArea = operation(mask,alternateMorphology,crossing)

    #THRESHOLD FOR#  0     1     2     3     4     5     6     7     8     9
    thresholds = [0.525,0.725,0.645,0.725,0.675,0.805,0.825,0.565,0.785,0.850]
    #I found different digit values have different sensitivities so I have done testing to come up with
    #these specific values
    #Sort keys so we can loop on the hashmap in the right order
    xKeys.sort()
    #xKeys represents the X value of each digit, since connected components goes by what ever
    #Y-value appears first, we need to look at the X to ensure right order
    buildingNumber = ""
    for key in xKeys:
        #Start matching at 0 finish at 9
        #Classification based on what ever digit has the higher percentage of matches
        number = 0
        digitValue = ""
        percentMatched = 0
        while number < 10:
            result = templateMatch(templates[number], digitImages.get(key), thresholds[number])
            if result > 0 and result > percentMatched:
                digitValue = str(number)
            number += 1
        buildingNumber += digitValue
    #Save detected area if we found an area
    if detectedArea is not None:
        detections += 1
        cv.imwrite(savePathTask1 + 'DetectedArea' + str(fileNumber).zfill(2)+ '.jpg', detectedArea)
        #Write the bulding number to the file
        textOutput = open(savePathTask1 + "Building" + str(fileNumber).zfill(2)+ '.txt' , "w")
        textOutput.write("Bulding " + buildingNumber)
        textOutput.close()
#Print out message when done
percentDetected = int((detections/fileNumber) * 100)     
print("TASK 1  COMPLETE: " + str(detections) + "/"+ str(fileNumber) 
    + " confident building detections ( " + str(percentDetected) + "% )" )
