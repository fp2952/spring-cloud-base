FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD auth-center-provider-1.0-SNAPSHOT.jar app.jar
ADD wait-for-it.sh /wait-for-it.sh
#RUN bash -c 'touch /app.jar'
RUN chmod +x /wait-for-it.sh
# install bash
RUN apk update
RUN apk upgrade
RUN apk add --no-cache bash
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 18001