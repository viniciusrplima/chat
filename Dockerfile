FROM openjdk

EXPOSE $PORT

RUN mkdir app/
WORKDIR /app/

CMD java -cp chat-1.0-SNAPSHOT.jar chat.server.ChatServer