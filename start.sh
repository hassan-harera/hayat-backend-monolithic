#!/bin/bash
#Docker down
#eval $(minikube docker-env)

#Maven build
mvn clean package -Dmaven.test.skip

#Docker up
docker-compose -f ./docker-compose.yml --env-file ./.env up --build -d
