##University Enrollment

Server Startup Details:
-----------------------
During server startup - admin account created by com.university.enrollment.startup.UniversityServerStartupProcessor class
Two types are users allowed to access the system - ADMIN (ROLE_ADMIN) and USER(ROLE_USER).
Currently, all the api's are protected with ROLE_ADMIN.
Any student can also be created as ROLE_ADMIN based on the request, but ROLE_USER is default.


###Postman collection
Postman collection provided in postman_collection directory. It contains list of API's.

###Swagger Access: 
http://<<DOMAIN_NAME>>/swagger-ui.html

###CURLS for University application (Example data provided. Domain name and id to be changed accordingly)
All the api's exception error code, status code and message defined in /resources/messages/**
All the exception messages athrown based on client locale.

#### Create Semester
curl --location --request POST 'http://<<DOMAIN_NAME>>/semester/create' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=BFC76D0D1E80967EA79406E1008472B4' \
--data-raw '{
"name": "S2",
"description": "S1 semester"
}'

#### Update Semester
curl --location --request PUT 'http://<<DOMAIN_NAME>>/semester/update/<<SEMESTER_ID>>' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=097F6F5DC282BC6B41A37EE06044296E; JSESSIONID=BFC76D0D1E80967EA79406E1008472B4' \
--data-raw '{
"name": "Fall Semester",
"description": "Fall Semester - 2021 added - name changes"
}'

#### Cretae Class
curl --location --request POST 'http://<<DOMAIN_NAME>>/class/create' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=5C3EF275D907103ACDB1F90315B0FD46; JSESSIONID=9B3D651552AD301CD42BFB686F6F2E86; JSESSIONID=BFC76D0D1E80967EA79406E1008472B4' \
--data-raw '{
"name": "3A",
"credits" : 5,
"description": "Fall Semester - 2021 added"
}'

#### Update Class
curl --location --request PUT 'http://<<DOMAIN_NAME>>/class/update/<<CLASS_ID>>' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=5C3EF275D907103ACDB1F90315B0FD46; JSESSIONID=9B3D651552AD301CD42BFB686F6F2E86; JSESSIONID=BFC76D0D1E80967EA79406E1008472B4' \
--data-raw '{
"name": "Fall Semester",
"credits" : 6,
"description": "Fall Semester - 2021 added - new changes"
}'

#### Create User
curl --location --request POST 'http://<<DOMAIN_NAME>>/user/create' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=BFC76D0D1E80967EA79406E1008472B4' \
--data-raw '{
"userName" : "11212",
"firstName" : "MK",
"lastName" : "MK",
"password" : "mk2",
"role": "ROLE_ADMIN",
"className": "3B",
"nationality": "US"
}'

#### Update User
curl --location --request PUT 'http://<<DOMAIN_NAME>>/user/update' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=5C3EF275D907103ACDB1F90315B0FD46; JSESSIONID=9B3D651552AD301CD42BFB686F6F2E86; JSESSIONID=BFC76D0D1E80967EA79406E1008472B4' \
--data-raw '{
"id": <<STUDENT_ID>>,
"userName" : "11212",
"firstName" : "MK",
"lastName" : "MK",
"password" : "mk2",
"role": "ROLE_ADMIN",
"className": "3A",
"nationality": "US"
}'

#### Fetch student
curl --location --request GET 'http://<<DOMAIN_NAME>>/fetchStudents?id=<<STUDENT_ID>>' \
--header 'Cookie: JSESSIONID=BFC76D0D1E80967EA79406E1008472B4'

#### Fetch Class Students
curl --location --request GET 'http://<<DOMAIN_NAME>>/fetchStudents?class=<<CLASS_NAME>>' \
--header 'Cookie: JSESSIONID=BFC76D0D1E80967EA79406E1008472B4'


## Build application
Gradle need to installed and all the env variables to configured on the build system
<<CURRENT_DIR>>./gradlew clean build 

## Test cases
<<CURRENT_DIR>>./gradlew clean build test
after runing the above command, test cases report file available in
<<CURRENT_DIR>>/build/reports/tests/test/index.html

## Jenkins CI/CD Pipeline steps
1. Checkout the source code from the https://github.com/murali101/university-enrollment.git
2. Please look into the Jenkins file for steps in the build process - Build Configuration
3. Post build, kubernetes deployment file available in Kubernetes.yaml
