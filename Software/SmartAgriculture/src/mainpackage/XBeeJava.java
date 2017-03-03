/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.XBeeTimeoutException;
import com.rapplogic.xbee.api.wpan.RxResponse16;
import com.rapplogic.xbee.api.wpan.TxRequest16;
import com.rapplogic.xbee.api.wpan.TxStatusResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author DungNguyenz
 */
public class XBeeJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws XBeeException {
        // TODO code application logic here

		// init log4j
        XBee xbee = new XBee();                               //Initialize XBee
        xbee.open("COM4", 9600);                            //Open the COM Port that connect with Coordinator XBee
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            int numberSelected = 0;                         //Variable store command that will be sent to Router XBee
            int[] arr = new int[1];                         //Array use for sending command to Router XBee
            do //Loop use for prompting user to enter a input number
            {
                System.out.println("Press number 1 to start the Arduino: ");
                try {
                    numberSelected = Integer.parseInt(inputReader.readLine());  //Read the input and store in numberSelected variable
                } catch (Exception e) //Catch exception when user enter something which is not a number
                {
                    System.out.println("Enter integer number only");
                    continue;
                }
                if (numberSelected != 1) //Only accept number 1
                {
                    System.out.println("Wrong number, enter number 1 only!!");
                }
            } while (numberSelected != 1);
            while (true) //Infinite loop for sending the command continuously
            {
                try {

                    arr[0] = numberSelected;
                    // specify the remote XBee 16-bit MY address 
                    XBeeAddress16 destination = new XBeeAddress16(0x56, 0x78);
                    TxRequest16 tx = new TxRequest16(destination, arr);
                    
      
                    TxStatusResponse status = (TxStatusResponse) xbee.sendSynchronous(tx);       //Send the packet and get the status
                    XBeeResponse response = xbee.getResponse();
                    if (response.getApiId() == ApiId.RX_16_RESPONSE) //If the package response has data
                    {
                        if (status.isSuccess()) // the Router XBee received our packet 
                        {
                            System.out.println("Command sent.Getting humidity and temperature");
                        }
                        System.out.println("Received RX 16 packet " + ((RxResponse16) response));
                        System.out.println("Humidity : " + response.getPacketBytes()[7] + "%");         //Humidity data will be on 7th element of the packet array
                        int LowTemp = response.getPacketBytes()[8];                               //Low byte of Temperature 
                        int HighTemp = response.getPacketBytes()[9];                              //High byte of Temperature
                        int Temp = (HighTemp << 8) | LowTemp;                                         //Adding low byte and high byte to have the temperature
                        System.out.println("Temperature: " + (double) (Temp) / 100 + " degree");       //Divide by 100 to have the real temperature
                    }

//                       
                } catch (XBeeTimeoutException e) {
                    System.err.println("Request timed out. make sure you remote XBee is configured and powered on");
                }

            }
        } catch (Exception e) {
            System.err.println("Unexpected error" + e.getMessage());
        } finally {
            xbee.close();
        }
    }
}
