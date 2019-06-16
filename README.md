# Coronet Excersise

Simple cache implementation over a custom tcp protocol.

Netty was chosen to implement a non-blocking, scaleable, binary communication.

-----------------------------------------------------------------------------------------------
  
Usage:  
- launch Server.java
- launch Main.java  
  
The Main class initializes a client, executes api calls from all types and shutsdown the client.
The Server needs to be shutdown manualy or it can be called again without shutting it down.

Difficulties:
  
The main issue is, obviously, making time to invest in the excersise.
Going threw the Netty documentation is neither one of the most pleasent experiences.
