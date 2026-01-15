#!/usr/bin/env sh

# 启动项目
java -Djava.security.egd=file:/dev/./urandom -jar /code/build/libs/app.jar --spring.profiles.active=$IXUEA_ENV --server.port=$IXUEA_PORT