FROM adoptopenjdk:15 AS builder

RUN cd /usr/local && \
    curl -O https://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz && \
    tar xfvz apache-maven-3.6.3-bin.tar.gz && \
    rm apache-maven-3.6.3-bin.tar.gz
COPY . /root
RUN export PATH=$PATH:/usr/local/apache-maven-3.6.3/bin && \
    cd /root && \
    mvn --no-transfer-progress clean install

FROM debian:10-slim
COPY --from=builder /root/target/runtime /usr/local/runtime
EXPOSE 8080
CMD ["/usr/local/runtime/bin/launcher"]
