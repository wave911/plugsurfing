# Getting Started

### Reference Documentation

### How to build
To build this application cimply run: *mvn clean install*.\
It will compile all the sources and will build the docker image locally.

### How to run
To run this application run the command from the repo root folder:  *docker-compose -f ./docker-compose.yaml up* \
It will start a *Musify service application* and a *Redis* containers

### What is used inside
It's a simple spring boot application, which has /musify/music-artist/details/{musicBainzId} endpoint. Redis is used 
as a cache solution to improve performance on queries for data, which was previously requested. For simplicity only 1 
external service is cached.
