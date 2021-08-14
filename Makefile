.PHONY: compile client server
.DEFAULT: compile


compile:
	mvn package

client: 
	java -cp target/chat-1.0-SNAPSHOT.jar chat.client.gui.ChatClient

server:
	java -cp target/chat-1.0-SNAPSHOT.jar chat.server.ChatServer
