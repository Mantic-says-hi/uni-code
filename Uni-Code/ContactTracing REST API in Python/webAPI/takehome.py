import json
from flask import Flask, request, jsonify

app = Flask(__name__)
#PersonAtLocations with schema of :
""" 
 - Date
 - Location
 - Visitors []
"""
with open("PersonAtLocations.json", "r") as f:
    peopleAtLocation = json.load(f)

#DateOnLocation with schema of :
"""
 - Person
  - Locations []
    - Location
    - Dates []
"""
with open("DateOnLocation.json", "r") as f:
    dateLocationVisitors = json.load(f)

"""The DateOnLocation.json gives the list of people with the date and location
so a double dictionary (map) will provide the list when given these two keys,
prefer having the date as the first key then location as the second for this data. 
It depends on the data set for which way round is better. On second thought this data 
could also be represented with a tree structure and acheive the same result"""
case1Data = {}
for item in dateLocationVisitors:
    date = item['date']
    location = item['location']
    visitors = item['visitors']
    if date not in case1Data:
        case1Data[date] = {}
    case1Data[date][location] = visitors

"""Uses PersonAtLocations.json to set each person as the primary key
and dates as a secondary key to access the list of visited location( s )"""
case2Data = {}
for item in peopleAtLocation:
    person = item['person']
    case2Data[person] = {}
    for locations in item['locations']:
        location = locations['location']
        dates = locations['dates']
        for date in dates:
            if date not in case2Data[person]:
                case2Data[person][date] = [location]
            else:
                case2Data[person][date].append(location)

"""Uses the DateOnLocation.json to group by people then date then contacts 
on that date without adding duplicate close contacts or themselves.
For now, I can't think of a better way to do this that will give me similar
access times."""
case3Data = {}
for item in dateLocationVisitors:
    date = item['date']
    visitors = item['visitors']
    for person in visitors:
        if person not in case3Data:
            case3Data[person] = {}
        if date not in case3Data[person]:
            case3Data[person][date] = []
        for closeContact in visitors:
            if closeContact != person and closeContact not in case3Data[person][date]:
                case3Data[person][date].append(closeContact)


"""Used as a test to see if I managed to get the code deployed properly"""
@app.route('/')
def test():
    return 'Is working?'
            

"""3 Get methods | Take in the required arguments and return the data if found
Returns empty strings if there is no data found from the request"""            
@app.route('/persons', methods=['GET'])
def personAtLocationOnDate():
    location = request.args.get('location')
    date = request.args.get('date')
    result = case1Data.get(date, {}).get(location, [])
    return jsonify(result)


@app.route('/locations', methods=['GET'])
def locationOfPersonOnDate():
    person = request.args.get('person')
    date = request.args.get('date')
    result = case2Data.get(person, {}).get(date, [])
    return jsonify(result)


@app.route('/closecontacts', methods=['GET'])
def closeContactsOfPersonOnDate():
    person = request.args.get('person')
    date = request.args.get('date')
    result = case3Data.get(person, {}).get(date, [])
    return jsonify(result)
   
if __name__ == '__main__':
    app.run()
