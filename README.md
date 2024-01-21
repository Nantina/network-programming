# Network Programming Assignment - Java Serial Communications

## Introduction
This Java application is developed as part of a network programming assignment for the Computer Networks course. The goal of the assignment is to implement an experimental network application, familiarize oneself with asynchronous serial communication mechanisms, and collect statistical measurements for various parameters affecting communication quality over real communication channels.

## Development Environment
Make sure you have the Java Development Kit (JDK) installed, which is freely available at [Java SE Downloads](http://java.sun.com/javase/downloads/index.jsp). Additional learning resources can be found at [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/index.html). For comprehensive documentation, refer to [Java Platform, Standard Edition Documentation](http://java.sun.com/javase/6/docs/index.html).

## Ithaki-Smart-Modem
The provided `Modem` class enables serial communication with a virtual modem, simulating a connection to the Ithaki server over the internet. The modem can be created with a specified speed, allowing communication from 1 Kbps to 80 Kbps.

```java
Modem modem = new Modem(speed);
```

The `modem` object provides methods for reading and writing data to and from the virtual modem.
```java
modem.read()
modem.write()
```

## Application Objectives 
The application should be able to receive a large number of echo packets sequentially from the server. Each packet is identified with a unique echo request code, provided by the Ithaki server at the start.It can request and receive different type of packets.

### Echo Packets
Echo packets have the format:
```
PSTART DD-MM-YYYY HH-MM-SS PC PSTOP
```

DD-MM-YYYY: Date of packet
HH-MM-SS: Time of packet
PC: Packet counter (modulo 100)

### Image Requests
Use the image request code MXXXX5 to request the current frame from the videoCoder hosted at Ithaki Video Server. Optionally, include parameters like CAM=FIX or CAM=PTZ to specify the camera source.
For image requests, the received bytes can be stored in a binary file with a .jpeg or .jpg extension for viewing.


### GPS Requests

### Error Image Requests 

### Error





