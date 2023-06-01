import json
import jsonschema


#Schema from the given 'data.json' file 
# "A location provides a list of persons and what date they attended"
"""
 - Location
  - Persons []
    - Person
    - Dates []
"""
original_schema = {
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "location": {"type": "string"},
            "persons": {
                "type": "array",
                "items": {
                    "type": "object",
                    "properties": {
                        "person": {"type": "string"},
                        "dates": {
                            "type": "array",
                            "items": {
                                "type": "string",
                                "format": "date-time"
                            }
                        }
                    },
                    "required": ["person", "dates"]
                }
            }
        },
        "required": ["location", "persons"]
    }
}

#Schema to map data to better suit my implementaion of the 'Bonus' case
#However it is probably not the best solution but is objectively easier to 
# move into data structures than the other JSON descriptions:
#"A location provides a list of persons and what date they attended"
#"A person provides a list of locations and what date they attended"
#This is "An object with a date, location and list of visitors."
"""
 - Date
 - Location
 - Visitors []
"""
new_schema = {
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "date": {
                "type": "string",
                "format": "date-time"
            },
            "location": {
                "type": "string"
            },
            "visitors": {
                "type": "array",
                "items": {
                    "type": "string"
                }
            }
        },
        "required": [
            "date",
            "location",
            "visitors"
        ]
    }
}

#Load provided data
with open("data.json", "r") as f:
    data = json.load(f)


#Validate input fits the correct schema so we can be confident with the operations in the next part of the code
try:
    jsonschema.validate(data, original_schema)
    print("Validation successful")
except jsonschema.exceptions.ValidationError as err:
    print(err)


#Pull out dates associated with locations from the original data, do not duplicate dates
#When a duplicate is found the person associated with it is added to the list of visitors
#Looking at the sample data this obviously creates a lot more data entries than the other two schemas
#However for now my goal is to make something work thats logical to me and then
#figure out better ways to improve it later on
new_data = []
for locations in data:
    location = locations['location']
    for person in locations['persons']:
        for date in person['dates']:
            date_already_added = False
            for added_date in new_data:
                if added_date['date'] == date and added_date['location'] == location:
                    date_already_added = True
                    added_date['visitors'].append(person['person'])
            if not date_already_added:
                new_data.append({"date": date, "location": location,  "visitors": [person['person']]})
#Sort by date ( presonal prefference )
new_data = sorted(new_data, key=lambda x: x['date'])
        
#Validate that new_data matches the schema that has been defined previously
try:
    jsonschema.validate(new_data, new_schema)
    print("Validation successful")
except jsonschema.exceptions.ValidationError as err:
    print(err)

with open("DateOnLocation.json", "w") as outfile:
    json.dump(new_data, outfile, indent=4)