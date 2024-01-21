package classes;

import java.net.*;
import java.io.*;
import java.lang.System;
import javax.sound.sampled.*;


public class socketProgr {
	
	public int client_port = 48017;
	public int server_port = 38017;
	
	public String echo_code = "E3883";
	public String echo_code_T = "E3883T00";
	
	public String image_code = "M4386";
	public String image_code_cam = "M4034CAM=PTZ";
	
	public String audio_code = "A9160";
	public String ithakicopter_code = "Q4617";
	public String vehicle_code = "V1757";
	
	public static void main (String[] args) throws UnknownHostException, SocketException {
		//(new socketProgr()).echo();
		//(new socketProgr()).image();
		(new socketProgr()).audio();
	}
	
	public void echo() throws UnknownHostException, SocketException {
		
		byte[] txbuf;
		
		boolean temperature = true;
		if (temperature) {
			txbuf = echo_code_T.getBytes();
		}
		else {
			txbuf = echo_code.getBytes();
		}
		
		byte[] rxbuf = new byte[512];
		
		//InetAddress client_addr = InetAddress.getByName("82.198.46.211");
		InetAddress client_addr = InetAddress.getByName("192.168.2.8");
		//InetAddress client_addr = InetAddress.getLocalHost();
		InetAddress server_addr = InetAddress.getByName("155.207.18.208");
		
		DatagramSocket sock = new DatagramSocket(client_port, client_addr);
		
		DatagramPacket request = new DatagramPacket(txbuf, txbuf.length, server_addr, server_port);
		DatagramPacket response = new DatagramPacket(rxbuf, rxbuf.length);
		try {
			sock.send(request);
			sock.receive(response);
		}catch (Exception x) {
			System.out.println(x);
		}
		
		String requestmsg = new String (txbuf, 0, request.getLength());
		System.out.println(requestmsg);
		String responsemsg = new String (rxbuf, 0, response.getLength());
		System.out.println(responsemsg);
		
	}
	
	public void image() throws UnknownHostException, SocketException {
		byte[] txbuf;
		byte[] image = new byte[70000];
		int bytesRead;
		bytesRead=0;
		
		boolean cam = false;
		if (cam) {
			txbuf = image_code_cam.getBytes();
		}
		else {
			txbuf = image_code.getBytes();
		}
		
		byte[] rxbuf = new byte[128];
		
		InetAddress client_addr = InetAddress.getByName("192.168.2.8");
		InetAddress server_addr = InetAddress.getByName("155.207.18.208");
		
		DatagramSocket sock = new DatagramSocket(client_port, client_addr);
		
		DatagramPacket request = new DatagramPacket(txbuf, txbuf.length, server_addr, server_port);
		DatagramPacket response = new DatagramPacket(rxbuf, rxbuf.length);
		
		
		String requestmsg = new String (txbuf, 0, request.getLength());
		System.out.println(requestmsg);
		
		boolean packet_start = false;
		boolean packet_finished = false;
		
		for (int j=0; j < 2000; j++) {
			//System.out.print("Number of packets requested: ");
			//System.out.println(j+1);
			try {
				sock.send(request);
				sock.receive(response);
			}catch (Exception x) {
				System.out.println(x);
			}
			for (int i=0; i < rxbuf.length; i ++) {
				if (packet_start) {
					if (i<127) {
						if (rxbuf[i] == -1 && rxbuf[i + 1] == -39) {
							image[bytesRead] = -1;
							image[bytesRead+1] = -39;
							System.out.println("Found end of image delimiter");
							System.out.print("Total bytes read : ");
							System.out.println(bytesRead+1);
							packet_finished = true;
							break;
						}
						else {
							image[bytesRead] = rxbuf[i];
							bytesRead ++;
						}
					}
					else {
						image[bytesRead] = rxbuf[i];
						bytesRead ++;
					}
				}
				else { //this else statement is only executed before we find the start of image delimiter
					if (i<127) {
						if (rxbuf[i] == -1 && rxbuf[i + 1] == -40) {
							System.out.println("Found start of image delimiter");
							image[0] = -1;// storing the 2 bytes of the start delimiter in the 
							image[1] = -40;
							bytesRead = 2;
							i ++; // in order to avoid the "-40"
							packet_start = true;  // this is set to true in order to not get in this "else" statement
							continue; 
						}
					}
				}
			}
			if (packet_finished) {
				break;
			}
		}
		File f = new File("imageresponse.jpeg");
		try(FileOutputStream fos = new FileOutputStream(f)){
			fos.write(image);
			fos.close();
			System.out.println("image created");
		}catch (Exception x) {
			System.out.println(x);
		}	
	}
	
	public void audio() throws UnknownHostException, SocketException, LineUnavailableException {
		byte[] txbuf = audio_code.getBytes();
		byte[] rxbuf = new byte[128];
		
		InetAddress client_addr = InetAddress.getByName("192.168.2.8");
		InetAddress server_addr = InetAddress.getByName("155.207.18.208");
		
		DatagramSocket sock = new DatagramSocket(client_port, client_addr);
		
		DatagramPacket request = new DatagramPacket(txbuf, txbuf.length, server_addr, server_port);
		DatagramPacket response = new DatagramPacket(rxbuf, rxbuf.length);
		
		String requestmsg = new String (txbuf, 0, request.getLength());
		System.out.println(requestmsg);
		
		try {
			sock.send(request);
			sock.receive(response);
		}catch (Exception x) {
			System.out.println(x);
		}
		
		AudioFormat linearPCM = new AudioFormat(8000, 16, 1, true, false);
		SourceDataLine lineOut = AudioSystem.getSourceDataLine(linearPCM);
		lineOut.open(linearPCM,32000);
		lineOut.start();
		byte[] audioBufferOut = new byte[8000];
		lineOut.write(audioBufferOut,0,8000);
		lineOut.stop();
		lineOut.close();
	}

}