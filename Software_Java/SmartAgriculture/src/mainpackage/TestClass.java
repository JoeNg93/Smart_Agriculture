/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;


/* Libraries for XBee */
import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.RxResponse16;
import com.rapplogic.xbee.api.wpan.TxRequest16;
import com.rapplogic.xbee.api.wpan.TxStatusResponse;

/**
 *
 * @author DungNguyenz
 */
public class TestClass {

    private static XBee xbee;

    public static void main(String[] args) {
        xbee = new XBee();
        try {
            xbee.open("COM4", 9600); //Try to connect to COM Port, Baud Rate = 9600
        } catch (XBeeException ex) {
            System.out.println("Can't open COM PORT");
        }
        RunMethod a = new RunMethod();
        a.start();

    }

    private static class RunMethod extends Thread {

        public void run() {
            while (true) {

                XBeeResponse response = null;

                boolean isNodeInNetwork = false;
                
                try {
                    response = xbee.getResponse(); //Get the response of the node
                } catch (XBeeException exc) {
                    System.out.println("Timeout. Can't get response");
                }

                if (response.getApiId() == ApiId.RX_16_RESPONSE) {
                    System.out.println("Packet received: ");
                    System.out.println(response);

                    RxResponse16 response16 = (RxResponse16) response;
                    int[] addrArray = response16.getSourceAddress().getAddress();
                    int highAddr = addrArray[0];
                    int lowAddr = addrArray[1];
                    int addr = (highAddr << 8) + lowAddr;
                    System.out.println("Source address: " + String.format("0x%x", addr));
                }
            }
        }
    }
}
