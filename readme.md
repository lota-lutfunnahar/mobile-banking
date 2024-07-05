##ibcn-api 
Assuming we have "openjdk version "1.8.0_242" and "Gradle 6.0.1" in our PC


1.Open terminal and clone repository in local PC
    
    git clone http://172.16.7.33/ib/.git


2.Change directory to the repo
    
    cd bkb-api
    

3.Default branch is "master"


4.Update local "master" branch with origin
    
    git pull


5.Run gradle clean and all existing tests
    
    gradle clean test


6.If all tests passed, then run the IB application
    
    gradle clean run


7.When application is run successfully, it will print in log
    
    Started AccountsSpringBootApplication .....


8.Open another tab on terminal and run follwing curl command to get response
    
curl --request GET \
  --url http://172.16.7.12:8081/v1/accounts/0200000065855/minimal-info \
  --header 'authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiTWFuYWdlciIsIm5hbWUiOiJLb3dzYXIifQ.63CmrWIePNZvhnxNjPi2SSvBRQa0b5XwMVyisTITGmE' \
  --header 'content-type: application/json' \
  --header 'request-id: 12345jkuu7iu7u' \
  --header 'request-time: 2020-01-16T11:36:54.336775Z' \
  --header 'request-timeout-in-seconds: 30'
