# Chat

A simple chat created in Java lang. 

### Compiling

The program is written in Java and was created with Maven. You must have Maven installed to compile.
Just run the following command in chat folder. The .jar file will stand at target/ folder.

```
mvn package
```

### Running Server

Maybe you want to select port to bind server. Go to class ServerMain and change the value of PORT constant. After compiling you can run server by the command below.

```
java -cp target/chat-1.0-SNAPSHOT.jar chat.server.ChatServer
```

### Running Client

You can run client to connect to server that you have configured previously by this command:

```
java -cp target/chat-1.0-SNAPSHOT.jar chat.client.gui.ChatClient
```

Before connection the program will show you a popup that will asks you some informations, like address, port and username. The address is the ip address of the server, whether you are running the client in the same machine of the server you must not change this. Whether you changed the port of the server you must change the value at port field. The username is the name used as identification.

After you sent the informations you can chat with others clients of the server.
