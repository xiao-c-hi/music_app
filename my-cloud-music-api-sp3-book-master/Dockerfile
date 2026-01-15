#继承基础镜像
#用Debian镜像打包完大概1.5g
#用alpine大概1g，也是docker官方推荐使用的镜像
FROM openjdk:17-jdk-alpine

#更改镜像为阿里云镜像
#目的是加速安装
#https://developer.aliyun.com/mirror/alpine
RUN echo http://mirrors.aliyun.com/alpine/v3.11/main > /etc/apk/repositories \
	&& echo http://mirrors.aliyun.com/alpine/v3.11/community >> /etc/apk/repositories

#设置时区

# 安装时区设置
# 复制上海时区
# 指定为上海时区
# 删除其他时区配置，节省空间

#查看日期信息date -R
RUN apk update \
    && apk add tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && apk del tzdata

#因为内嵌了tomcat
#而tomcat默认会使用/tmp
VOLUME /tmp

# 创建一个code目录
RUN mkdir /code

# 将当前目录(Dockerfile文件所在目录)所有文件拷贝到code目录
COPY . /code

# 切换工作目录到code目录
WORKDIR /code

# 添加可执行权限
# 可以在编排文件中调用
# 也可以在ENTRYPOINT指定
RUN chmod +x ./run.sh

# 清空缓存
RUN ./gradlew clean

#构建jar包
#-x test:表示跳过测试
RUN ./gradlew build -x test -info

#将支付宝证书拷贝到jar同级目录
COPY ./src/main/resources/data/ /code/build/libs/data/

#暴露端口
#一个是dev
#一个是prod
EXPOSE 8080

#http
EXPOSE 51050
EXPOSE 41060


#执行脚本
#因为我们要使用环境变量
ENTRYPOINT ["/code/run.sh"]