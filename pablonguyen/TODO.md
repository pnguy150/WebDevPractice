# look at app maain and see how it is instantiated and started
## POST (create a person)
### check existence of that person in mongodb
#### if a person with such SSN already existed return http error
#### otherwise send the request to a AWS queue


## DELETE (delete a person)
### similar to POST but this is delete
### 

## PUT (update a person)
### similar to POST but this is update
### 

## GET (by SSN as path parameter a person)
### return the person with matching ssn

## GET (by first name amd last name as query parameters)
### return list of person with matching firstname, lastName


## Use the SampleThread, wired on the SQS Client
### this thread should keep receiving the request data from the queue
### if the request is creating a person, then insert to MongoDB
### if the request is updating a person, then update to MongoDB
### if the request is delete a person, then delete to MongoDB






