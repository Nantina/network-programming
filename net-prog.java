package javaModem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.nio.file.Files;

public class virtualModem {
    public static void main(String[] args) {
        (new virtualModem()).echo(); 
        (new virtualModem()).image(); 
        (new virtualModem()).gps(); 
        (new virtualModem()).errorimage(); 
        (new virtualModem()).error(); 
    }

    public void echo() {
        int k;
        Modem modem;
        modem = new Modem();
        modem.setSpeed(1000);
        modem.setTimeout(19000);
        modem.open("ithaki");
        String rxmessage = " ";
        for (;;) {
            try {
                k = modem.read();
                if (k == -1) break;
                rxmessage = rxmessage + (char) k;
                if (rxmessage.indexOf("\r\n\n\n") > -1) break;
                System.out.print((char) k);
            } catch (Exception x) {
                System.out.print(x);
                break;
            }
        }

        int l;
        String echoRequestCode = "EXXXX\r";
        long[][] timearray = new long[400][2]; // contains the time of sending and receiving of a packet
        for (int m = 0; m < 400; m++) {
            String message = " ";
            timearray[m][0] = System.currentTimeMillis();
            System.out.println(timearray[m][0]);
            modem.write(echoRequestCode.getBytes());
            for (;;) {
                try {
                    l = modem.read();
                    if (l == -1) break;
                    message = message + (char) l;
                    System.out.print((char) l);
                    if (message.indexOf("PSTOP") > -1) {
                        timearray[m][1] = System.currentTimeMillis();
                        System.out.println(timearray[m][1]);
                        break;
                    }
                } catch (Exception x) {
                    System.out.print(x);
                    break;
                }
            }
        }

        System.out.println("for chart G1");
        for (int m = 0; m < 400; m++) {
            System.out.println(timearray[m][1] - timearray[m][0]);
        }

        modem.close();
    }

    public void image() {
        int k;
        Modem modem;
        modem = new Modem();
        modem.setSpeed(1000);
        modem.setTimeout(19000);
        modem.open("ithaki");
        String rxmessage = " ";
        for (;;) {
            try {
                k = modem.read();
                if (k == -1) break;
                rxmessage = rxmessage + (char) k;
                if (rxmessage.indexOf("\r\n\n\n") > -1) break;
                System.out.print((char) k);
            } catch (Exception x) {
                System.out.print(x);
                break;
            }
        }

        int l;
        String imageRequestCode = "MΧΧΧΧ\r";
        byte[] image = new byte[128000];
        image[0] = -1; // placing the first byte of the delimiter
        String message = " ";
        modem.write(imageRequestCode.getBytes());
        int bytesRead = 1;
        for (;;) {
            try {
                l = modem.read();
                if (l == -1) break;
                message = message + (byte) l;
                if (message.indexOf("-1-40") > -1) {
                    image[bytesRead] = (byte) l; // saving all the bytes within the delimiters in the image[] array
                    bytesRead++;
                }
                if (message.indexOf("-1-39") > -1) {
                    System.out.println("reading complete");
                    break;
                }
            } catch (Exception x) {
                System.out.print(x);
                break;
            }
        }

        System.out.println(bytesRead);
        // creation of the image
        File f = new File("imagerequest.jpeg");
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(image);
            fos.close();
            System.out.println("image created");
        } catch (Exception x) {
            System.out.println(x + "There is an error");
        }

        modem.close();
    }

    public void gps() {
        int k;
        Modem modem;
        modem = new Modem();
        modem.setSpeed(1000);
        modem.setTimeout(19000);
        modem.open("ithaki");
        String rxmessage = " ";
        for (;;) {
            try {
                k = modem.read();
                if (k == -1) break;
                rxmessage = rxmessage + (char) k;
                if (rxmessage.indexOf("\r\n\n\n") > -1) break;
                System.out.print((char) k);
            } catch (Exception x) {
                System.out.print(x);
                break;
            }
        }

        int l;
        String gpsRequestCode = "PΧΧΧΧ\r";
        String extra_code = "XPPPPLL\r";
        String points = "";
        byte[] image = new byte[128000];
        String message = " ";
        String gpspoint = " ";
        modem.write(gpsRequestCode.getBytes());
        int charcount = 0;
        int pointscount = 0; // counts the gps points that are created
        for (;;) {
            try {
                l = modem.read();
                if (l == -1) break;
                message = message + (char) l;
                if (message.indexOf("START ITHAKI GPS TRACKING\r\n") > -1) {
                    if (message.indexOf("$GPGGA,") > -1) {
                        charcount++;
                        if (charcount > 12 && charcount < 17) {
                            gpspoint = gpspoint + (char) l;
                        } else if (charcount > 17 && charcount < 20) {
                            gpspoint = gpspoint + (char) l;
                        } else if (charcount > 24 && charcount < 30) {
                            gpspoint = gpspoint + (char) l;
                        } else if (charcount > 30 && charcount < 32) {
                            gpspoint = gpspoint + (char) l;
                            break;
                        }
                    }
                }
                if (message.indexOf("STOP ITHAKI GPS TRACKING\r\n") > -1) {
                    System.out.println("reading complete");
                    break;
                }
            } catch (Exception x) {
                System.out.print(x);
                break;
            }
        }

        modem.write(gpspoint.getBytes());
        int i = 0;
        for (int m = 0; m < 13; m++) {
            m = modem.read();
            image[i] = (byte) m;
            i++;
        }
        File f = new File("gpsrequest.jpeg");
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(image);
            fos.close();
            System.out.println("image created");
        } catch (Exception x) {
            System.out.println(x + "There is an error");
        }
        modem.close();
    }

    public void errorimage() {
        int k;
        Modem modem;
        modem = new Modem();
        modem.setSpeed(1000);
        modem.setTimeout(19000);
        modem.open("ithaki");
        String rxmessage = " ";
        for (;;) {
            try {
                k = modem.read();
                if (k == -1) break;
                rxmessage = rxmessage + (char) k;
                if (rxmessage.indexOf("\r\n\n\n") > -1) break;
                System.out.print((char) k);
            } catch (Exception x) {
                System.out.print(x);
                break;
            }
        }

        int l;
        String imageRequestCode = "GΧΧΧΧ\r";
        byte[] image = new byte[128000];
        image[0] = -1; // placing the first byte of the delimiter
        String message = " ";
        modem.write(imageRequestCode.getBytes());
        int bytesRead = 1;
        for (;;) {
            try {
                l = modem.read();
                if (l == -1) break;
                message = message + (byte) l;
                if (message.indexOf("-1-40") > -1) {
                    image[bytesRead] = (byte) l; // saving all the bytes within the delimiters in the image[] array 
                    bytesRead++;
                }
                if (message.indexOf("-1-39") > -1) {
                    System.out.println("reading complete");
                    break;
                }
            } catch (Exception x) {
                System.out.print(x);
                break;
            }
        }

        System.out.println(bytesRead);
        // creation of the image
        File f = new File("errorimagerequest.jpeg");
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(image);
            fos.close();
            System.out.println("image created");
        } catch (Exception x) {
            System.out.println(x + "There is an error");
        }

        modem.close();
    }

    public void error() {
        int k;
        Modem modem;
        modem = new Modem();
        modem.setSpeed(1000);
        modem.setTimeout(19000);
        modem.open("ithaki");
        String rxmessage = " ";
        for (;;) {
            try {
                k = modem.read();
                if (k == -1) break;
                rxmessage = rxmessage + (char) k;
                if (rxmessage.indexOf("\r\n\n\n") > -1) break;
                System.out.print((char) k);
            } catch (Exception x) {
                System.out.print(x);
                break;
            }
        }

        int l;
        String ACK = "QXXXX\r";
        String NACK = "RXXXX\r";
        char[] chararray = new char[16]; // contains the 16 characters which will be used to calculate the fcs 
        int[] fcsarray = new int[3];
        long[][] timearray = new long[1000][2]; // contains the time of sending and receiving of a packet
        int numofpacksent = 0; // the number of total packets sent
        int[] arraytimespacksent = new int[400]; // array that keeps the timespacksent of each packet sent
        int timespacksent = 0; // how many times a packet gets sent
        int FCS = 0;
        int myfcs = 0;
        for (int n = 0; n < 400; n++) {// running the loop 400 times to reach >4min running
            if (myfcs == FCS) {
                if (numofpacksent != 0) {
                    System.out.println("packet reseived");
                    timearray[numofpacksent][1] = System.currentTimeMillis();
                    System.out.println("packet send:" + timespacksent + " times");
                    arraytimespacksent[numofpacksent] = timespacksent;
                    timespacksent = 0;
                    numofpacksent++;
                    System.out.println("ACK");
                    System.out.println("new packet sent");
                    modem.write(ACK.getBytes());
                    timearray[numofpacksent][0] = System.currentTimeMillis();
                } else {
                    modem.write(ACK.getBytes());
                    numofpacksent++;
                    timespacksent++;
                    System.out.println("new packet sent");
                    timearray[numofpacksent][0] = System.currentTimeMillis();
                }
            } else {
                modem.write(NACK.getBytes());
                System.out.println("NACK");
                timespacksent++;
            }

            String message = " ";
            int fcscount = -1; // variable counting how many digits of the FCS have been read
            int a = -1; // variable counting how many characters of the 16-char sequence have been read
            for (;;) {
                try {
                    l = modem.read();
                    if (l == -1) break;
                    message = message + (char) l;
                    System.out.print((char) l);
                    if (message.indexOf("PSTOP") > -1) {
                        break;
                    }
                    if (message.indexOf("> ") > -1) {
                        if (fcscount < 3 && fcscount > -1) {
                            fcsarray[fcscount] = Character.getNumericValue((char) l);
                        }
                        fcscount++;
                    }
                    if (message.indexOf("<") > -1) {
                        if (message.indexOf(">") > -1) {
                            continue;
                        }
                        if (a > -1) {
                            chararray[a] = (char) l;
                        }
                        a++;
                    }
                } catch (Exception x) {
                    System.out.print(x);
                    break;
                }
            }

            myfcs = (chararray[0] ^ chararray[1] ^ chararray[2] ^ chararray[3] ^ chararray[4] ^ chararray[5]
                    ^ chararray[6] ^ chararray[7] ^ chararray[8] ^ chararray[9] ^ chararray[10] ^ chararray[11]
                    ^ chararray[12] ^ chararray[13] ^ chararray[14] ^ chararray[15]);
            System.out.print("\n");
            System.out.println("my fcs is:" + myfcs);
            System.out.print("system's fcs is: ");
            FCS = fcsarray[0] * 100 + fcsarray[1] * 10 + fcsarray[2] * 1;
            System.out.println(FCS);
        }

        System.out.println("Times of transmission and correct receive of each packet");
        for (int g = 0; g < (numofpacksent); g++) {
            for (int f = 0; f < 2; f++) {
                System.out.print(g + ". " + timearray[g][f] + " ");
            }
            System.out.print("\n");
        }

        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int morethanfive = 0;
        System.out.println("How many times each packet gets sent until correct");
        for (int d = 0; d < (numofpacksent); d++) {
            System.out.println(arraytimespacksent[d] + 1);
            if (arraytimespacksent[d] + 1 == 1) {
                one++;
            } else if (arraytimespacksent[d] + 1 == 2) {
                two++;
            } else if (arraytimespacksent[d] + 1 == 3) {
                three++;
            } else if (arraytimespacksent[d] + 1 == 4) {
                four++;
            } else if (arraytimespacksent[d] + 1 == 5) {
                five++;
            } else {
                morethanfive++;
            }
        }

        System.out.println("one: " + one);
        System.out.println("two: " + two);
        System.out.println("three: " + three);
        System.out.println("four: " + four);
        System.out.println("five: " + five);
        System.out.println("morethanfive: " + morethanfive);
        System.out.println("for chart G2");
        for (int d = 0; d < (numofpacksent); d++) {
            System.out.println(timearray[d][1] - timearray[d][0]);
        }
        System.out.println("total number of packets sent: " + numofpacksent);
        modem.close();
    }
}


