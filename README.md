
gradle clean build -x test

docker build -t abhishek1009/echo:latest .
docker image push abhishek1009/echo:latest

docker run -d --name echo -p 8000:8000 abhishek1009/echo:latest

docker logs -f echo

docker exec -it echo bash