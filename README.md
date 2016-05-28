# github devevelopers skill-list
Displays list of github members with programming languages and count of repositories including this languages.

## how to start
 1. At first configure github organisation, github basic auth user and password (token) in `src/main/resources/application.yml` . Get your github access token here https://github.com/settings/tokens

 2. Start spring boot application with `./gradlew bootRun` on Linux or with `gradlew.bat bootRun` on Windows.

 3. In browser go to http://localhost:8080/ for html format or http://localhost:8080/knowledge for json format.

## todo's
* Use paging (only first result page is using now)
* Testing
* Persistence + Caching
* Deployment
* Monitoring