# Marketplace project 

## Requirements
* Docker

## How to build and run app
```shell
docker image build -t marketplace-api:v1 .
docker container run --name marketplace-api -d -p 8080:8080 marketplace:v1
```
