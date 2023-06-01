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


#Schema to map data to 
# "A person provides a list of locations and what date they attended"
"""
 - Person
  - Locations []
    - Location
    - Dates []
"""
new_schema = {
    "type": "array",
    "items": {
        "type": "object",
        "properties": {
            "person": {
                "type": "string"
            },
            "locations": {
                "type": "array",
                "items": {
                    "type": "object",
                    "properties": {
                        "location": {
                            "type": "string"
                        },
                        "dates": {
                            "type": "array",
                            "items": {
                                "type": "string",
                                "format": "date-time"
                            }
                        }
                    },
                    "required": [
                        "location",
                        "dates"
                    ]
                }
            }
        },
        "required": [
            "person",
            "locations"
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

#Go into each location and manipulate avaliable data to match the schema
#People that have visted multiple locations will add this to their list of locations with dates
new_data = []
for locations in data:
    location = locations['location']
    for person in locations['persons']:
        name = person['person']
        dates = person['dates']
        person_already_added = False
        for added_person in new_data:
            if added_person['person'] == name:
                person_already_added = True
                added_person['locations'].append({"location": location, "dates": dates})
        if not person_already_added:
            new_data.append({"person": name, "locations": [{"location": location, "dates": dates}]})


#Validate that new_data matches the schema that has been defined previously       
try:
    jsonschema.validate(new_data, new_schema)
    print("Validation successful")
except jsonschema.exceptions.ValidationError as err:
    print(err)


with open("PersonAtLocations.json", "w") as outfile:
    json.dump(new_data, outfile, indent=4)