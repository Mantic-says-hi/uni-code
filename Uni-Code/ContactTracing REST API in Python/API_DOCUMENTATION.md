# API Documentation
This API provides endpoints to retrieve data related to the VMS Technical Challenge.     
The API is built using Flask, coded in Python and supports GET requests.

## /persons - GET method
Endpoint to retrieve a list of persons at a specific location on a given date.  
   
### Parameters

- `location` (required): A valid URL string representing the location where the persons were at.
- `date` (required): A string representing the date in the format `yyyy-mm-ddThh:mm:ss.sssZ`.

### Response

A JSON array containing a list of persons who were at the given location on the given date.   
If there are no results for the given parameters, an empty array will be returned.

### Example request:
`GET /persons?location=King's%20Landing&date=2021-02-23T00:00:00.000Z`

### Example response:
`["Arya Stark","Ellaria Sand","Robb Stark","Viserys Targaryen"]`


## /locations - GET method
Endpoint to retrieve a list of locations where a specific person was on a given date.

### Parameters

- `person` (required): A valid URL string representing the name of the person for whom to retrieve the locations.
- `date` (required): A string representing the date in the format `yyyy-mm-ddThh:mm:ss.sssZ`.

### Response

A JSON array containing a list of locations where the given person was on the given date.  
If there are no results for the given parameters, an empty array will be returned.

### Example request:
`GET /locations?person=Robert%20Baratheon&date=2021-02-01T00:00:00.000Z`

### Example response:
`["Casterly Rock","Tarth","The Wall"]`

## /closecontacts - GET method
Endpoint to retrieve a list of close contacts for a specific person on a given date.  

### Parameters

- `person` (required): A valid URL string representing the name of the person for whom to retrieve the close contacts.
- `date` (required): A string representing the date in the format `yyyy-mm-ddThh:mm:ss.sssZ`.

### Response

A JSON array containing a list of close contacts for the given person on the given date.  
If there are no results for the given parameters, an empty array will be returned.

### Example request:
`GET /closecontacts?person=Shae&date=2021-02-01T00:00:00.000Z`

### Example response:
`["Gilly","Grey Worm","Jaime Lannister","Ellaria Sand","Robb Stark","Viserys Targaryen","Tywin Lannister"]`