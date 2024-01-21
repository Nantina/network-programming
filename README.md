# Network Programming Project in Java

## Introduction
This Java application is developed as part of a network programming assignment for the Computer Networks course. The assignment focuses on the development of an experimental network application using the Java programming language and is divided into two parts. 

The goal of the first part is to implement an experimental network application, familiarize oneself with asynchronous serial communication mechanisms, and collect statistical measurements for various parameters affecting communication quality over real communication channels. The file for this part is stored inside the `Network-Programming` folder.

Regarding the second part, the primary goals include implementation of asynchronous serial communication mechanisms, exploration of real-time digital audio transmission over User Datagram Protocol (UDP) and Transmission Control Protocol (TCP), and the collection of statistical measurements for various parameters influencing communication quality over real communication channels. The java file as well as a class file for the second part can be found in the `Socket-Programming` folder.

## Overview

The application can be independently developed on a personal computer within the laboratory environment or any other suitable space. However, specific measurements are required to be conducted in collaboration with the Ithaca server of the experimental virtual lab. The [Ithaca server](http://ithaki.eng.auth.gr/netlab/index.html) serves as the reference point for the activities outlined in this project.

Both parts of the assignement are developed using the `Eclipse IDE`.

## First Part (Network Progeamming)

### Ithaki-Smart-Modem
The provided `Modem` class enables serial communication with a virtual modem, simulating a connection to the Ithaki server over the internet. The modem can be created with a specified speed, allowing communication from 1 Kbps to 80 Kbps.

```java
Modem modem = new Modem(speed);
```

The `modem` object provides methods for reading and writing data to and from the virtual modem.
```java
modem.read()
modem.write()
```

### Application Objectives 
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


## Second Part (Socket Programming)

## Overview

The `socketProgr` class is the central component of the application, managing communication with a server using User Datagram Protocol (UDP). The code is organized into three methods: `echo()`, `image()`, and `audio()`, each corresponding to one of the specified functionalities.

## Echo Functionality

The `echo()` method establishes a connection with the server, sends an echo code, and receives a response. The echo code can be selected as either a standard echo or a temperature-related echo. The communication involves the use of UDP and demonstrates basic message exchange.

## Image Capture Functionality

The `image()` method implements the image capture functionality, allowing the client to request image packets from the server. The received packets are then reconstructed to form a complete image file (in JPEG format). This functionality showcases the utilization of UDP for continuous data transmission.

## Audio Reception Functionality

The `audio()` method focuses on receiving audio data from the server and playing it through the client's audio output. The audio code is sent to the server, and the received data is played in real-time using the Java Sound API. This feature highlights the real-time data streaming capabilities over UDP.

## Usage

To test each functionality independently, uncomment the corresponding method call in the `main` function. The server addresses and port numbers are specified within the class, and adjustments can be made as needed.




