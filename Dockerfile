FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/barclays-ecommerce-0.1 barclays-ecommerce.jar
ENTRYPOINT ["java","-jar", "barclays-ecommerce.jar"]