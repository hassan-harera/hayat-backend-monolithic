#!/bin/bash
#Docker build
mvn clean install -Dmaven.test.skip

#Docker build
docker build . -t hayat_app:latest
