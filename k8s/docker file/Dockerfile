FROM   openjdk
MAINTAINER   smallwei
##拆分成三个服务
 COPY  jar/admin-0.0.1-SNAPSHOT.jar /usr/java
 COPY jar/server-common-0.0.1-SNAPSHOT.jar /usr/jar/
 COPY jar/service-es-0.0.1-SNAPSHOT.jar /usr/jar/
CMD ["java", "-jar", "/usr/jar/admin-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "/usr/jar/server-common-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "/usr/jar/service-es-0.0.1-SNAPSHOT.jar"]
