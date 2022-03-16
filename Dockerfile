FROM java:8

VOLUME /tmp

ADD target/temp-0.0.1-SNAPSHOT.jar app.jar

RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Dspring.profiles.active=online","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
