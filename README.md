###my_hiit
Webapp hosting backend APIs for Spotify HIIT UI

#####Create docker image
1. cd to `my_hiit`
2. run `mvn clean install`
3. run `docker build -t ndayal/my_hiit .`
4. run `docker run -p 8080:8080 ndayal/my_hiit`