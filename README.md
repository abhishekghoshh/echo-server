
gradle clean build -x test

docker build -t echo:latest .

docker run -d \
   --name echo \
   -p 8000:8000 \
   --restart unless-stopped \
   echo:latest

docker logs -f echo

docker exec -it echo bash