## Weather-Service ##

To start the application please follow the next steps:


 - Go the project root path
 - Compile and package the application by executing next command: **mvn -U clean package**
 - Run the application: **java -jar target/weather-0.0.1-SNAPSHOT.jar training.weather.WeatherApplication**
 - Test the application from any http client. Examples :
     - *localhost:8080/api/training/weather?city=london&date=2020-01-12T00:30:00*
     - *localhost:8080/api/training/weather?city=london&date=2020-01-13T00:00*
     - *localhost:8080/api/training/weather?city=london*
 
 