import json

with open("PersonAtLocations.json", "r") as f:
    peopleAtLocation = json.load(f)
with open("DateOnLocation.json", "r") as f:
    dateLocationVisitors = json.load(f)

case1Data = {}
for item in dateLocationVisitors:
    date = item['date']
    location = item['location']
    visitors = item['visitors']
    if date not in case1Data:
        case1Data[date] = {}
    case1Data[date][location] = visitors


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



case3Data = {}
for item in dateLocationVisitors:
    date = item['date']
    location = item['location']
    visitors = item['visitors']
    for person in visitors:
        if person not in case3Data:
            case3Data[person] = {}
        if date not in case3Data[person]:
            case3Data[person][date] = []
        for closeContact in visitors:
            if closeContact != person and closeContact not in case3Data[person][date]:
                case3Data[person][date].append(closeContact)
        



def personAtLocationOnDate(location, date):
    global case1Data
    return case1Data[date][location]

def locationOfPersonOnDate(person, date):
    global case2Data
    return case2Data[person][date]

def closeContactsOfPersonOnDate(person, date):
    global case3Data
    return case3Data[person][date]


people = personAtLocationOnDate("King's Landing", "2021-02-23T00:00:00.000Z")
locations = locationOfPersonOnDate("Robert Baratheon","2021-02-16T00:00:00.000Z")
closeContacts = closeContactsOfPersonOnDate("Shae", "2021-02-01T00:00:00.000Z")
print("\nExpecting : Arya Stark | Ellaria Sand | Robb Stark | Viserys Targaryen |\n")
print(people)
print("\n\nExpecting : Casterly Rock | Crossroads Inn | Greywater Watch |\n")
print(locations)
print("\n\nExpecting : Gilly | Grey Worm | Jamie Lannister | Ellaria Sand | Robb Stark | Viserys Targaryen | Tywin Lannister |\n")
print(closeContacts)