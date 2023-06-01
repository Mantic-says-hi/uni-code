# VMS Technical Challenge Submission - Matthew Matar

## Public End Point URL

- https://matarvmstc.azurewebsites.net/

The default domain to this is just a message to test that the deployment is working.

The usable end points are as follows and usage is explained in the 'API_DOCUMENTATION.md' file
- https://matarvmstc.azurewebsites.net/locations?person=PERSON&date=DATE
- https://matarvmstc.azurewebsites.net/persons?location=LOCATION&date=DATE
- https://matarvmstc.azurewebsites.net/closecontacts?person=PERSON&date=DATE

## Method
Aproaching this challenge I have no prior knowledge or experience in building and deploying a Web API.  
Given this, I am sure that I have not implemented this the proper/correct way, however for me it seems  
like I have done something that can be considered acceptable.

Before startying I also had no prior experience with programming using JSON data. I spent some time to research   
and learnt what a JSON schema was. From then I then I developed two schemas and manipulated the provided data   
to represent them.   

The reason why I did this was to frame the data in a structured format that I could easily  
understand how to use the data to solve the given problems. Specifically, made data structures that will return a   
correct answer upon receiving the appropriate key parameters (e.g., person and date). For all three problems I chose to  
use Maps (Python Dictionaries), since I am most comfortable using this structure.

## Deployment Platform - 'Azure'
 - I tried to create and deploy this project on AWS however it would not let me make an account   
 because it didn't accept my card to sign up
 - Instead Microsoft Azure let me make an account so I have deployed it through a Web App on that platform


## Usage through command line
- A screenshot example of how I tested the endpoints through the Windows command line   
![cmd](https://imgur.com/JPtrLmK.png)

## Usage through web browser
- A screenshot example of how I tested the endpoints through a web browser
![url](https://imgur.com/smgQP6G.png)

### Thank you for taking the time to review my submission