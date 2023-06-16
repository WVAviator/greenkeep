#!/bin/bash

export AUTH0_CLIENT_ID=
export AUTH0_CLIENT_SECRET=

./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
