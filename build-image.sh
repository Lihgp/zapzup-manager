#!/bin/bash

docker-compose -f resource/docker-compose.yml up -d
mvn clean install -T10 -U
docker-compose -f resource/docker-compose.yml down
cd zapzup-manager-application
docker build -t zapzup_manager/zapzup_manager:latest .
docker push zapzup_manager/zapzup_manager:latest
docker rmi zapzup_manager:latest