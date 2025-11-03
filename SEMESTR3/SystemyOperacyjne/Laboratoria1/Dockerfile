FROM ubuntu:latest
WORKDIR /app
COPY hello-world.c /app/
RUN apt update && apt install gcc -y
RUN gcc hello-world.c -o hello
CMD ./hello