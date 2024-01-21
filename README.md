# Network Programming Assignment - Java Serial Communications

## Introduction
This Java application is developed as part of a network programming assignment for the Computer Networks course. The goal of the assignment is to implement an experimental network application, familiarize oneself with asynchronous serial communication mechanisms, and collect statistical measurements for various parameters affecting communication quality over real communication channels.

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
By using an image request code (MXXXX) the current frame is returned from the videoCoder hosted at Ithaki Video Server. Also, there are optional parameters like CAM=FIX or CAM=PTZ to specify the camera source.
The sequence of bytes is divided into single images with the use of specified delimeters.
For image requests, the received bytes are stored in a binary file and are saved in the PC as an image file.

### GPS Requests

Another request code is the GPS one, returning information regarding the position, velocity, time etc. with ASCII character format. 

### Error Image Requests 

The Server, also, allows the experimentation with transmission errors due to noise interfearence in the communication channel.






