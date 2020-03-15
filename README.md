# Spotify Explorer code test
[![Build Status](https://travis-ci.org/softfly/spotify-explorer.svg?branch=master)](https://travis-ci.org/softfly/spotify-explorer)

**spotify-api-client** Client to connect to the Spotify API. The library can be used in other projects.<br/>
**spotify-explorer-react** The frontend app. I decided to take this opportunity to check out the new framwork for me - React.<br/>
**spotify-explorer-rest** In the future, this layer can be converted to microservice.<br/>
**spotify-explorer-app** Spring Boot container, ready to run locally the application.<br/>
<br/>
TODOs:
* I simplified some things, becuase
    * I wanted to deliver only one bootable JAR to run locally for the reviewer.<br/>
spotify-explorer-rest should be deployed on AWS E2, spotify-explorer-react should be deployed on AWS S3. But I didn't want to create too many seperated bootable JARs for code test.
    * I wanted to avoid the reviewer would have to install Node.JS.<br/>
spotify-explorer-react/build should not be kept in repo.
* More tests.
* I tried to save some of my time. If something should be added I am waiting for suggestions.

## Production environment
http://spotify-explorer.eu-west-2.elasticbeanstalk.com/<br/>
http://spotify-explorer.eu-west-2.elasticbeanstalk.com/api/spotify/v1/search?q=yuri%20buenaventura
## Installation
```
mvn clean install -DskipTests
```
## Usage
https://developer.spotify.com/dashboard/applications for get the API access keys.

Windows:

```
java -jar spotify-explorer-app\target\spotify-explorer-app-1.0-FINAL.jar --SPOTIFY_CLIENT_ID="xxx" --SPOTIFY_CLIENT_SECRET="xxx"
```
Linux:

```
java -jar spotify-explorer-app/target/spotify-explorer-app-1.0-FINAL.jar --SPOTIFY_CLIENT_ID="xxx" --SPOTIFY_CLIENT_SECRET="xxx"