#include <XBee.h> //Library for XBee,Baud rate now is 9600

#include <LiquidCrystal.h> //Library for LCD

// #include <TinyGPS++.h> //Library for GPS
// #include <SoftwareSerial.h> //Library for virtual RX,TX

 #include <DHT.h> //Library for DHT Sensor (Temperature)

// The first two rows and the last two rows of breadboard
// is allocated for 5V and GND pins

/* Define part for XBee */
// If using XBee explorer => connect 5V,GND from XBee Explorer to Arduino
// Also connect RX XBee Explorer to RX Arduino, TX XBee Explorer to TX Arduino
#define XBEE_BAUDRATE 9600 //Baud rate of XBEE
                            //Rx and Tx XBee must use the same baud rate

/* Define part for GPS */
#define rxPin 3 //Tx of GPS module will connect to pin rx(Digital pin 3) of arduino
#define txPin 4

/* Pre-define coordinate of ThaoDien and BachKhoa (for testing purpose) */
#define THAODIEN_LAT 10.80660
#define THAODIEN_LONG 106.72973

#define BACHKHOA_LAT 10.77272
#define BACHKHOA_LONG 106.65853

/* Define part for DHT */
/**
 * Connect pin 1 (on the left) of the sensor to +5V
 * NOTE: If using a board with 3.3V logic like an Arduino Due connect pin 1
 * to 3.3V instead of 5V!
 * Connect pin 2 of the sensor to whatever your DHTPIN is
 * Connect pin 4 (on the right) of the sensor to GROUND
 * Connect a 10K resistor from pin 2 (data) to pin 1 (power) of the sensor
 */
#define DHT_PIN 7 //Digital pin 7
#define DHT_TYPE DHT11


/* Define part for Humidity */
#define HUMIDITY_PIN A0 //Analog pin 0
//blue wire - data, red wire - Vcc, black wire - Gnd

/* Define part for Master Address */
#define MASTER_ADDRESS 0x1000

/* Define part for controlling motor */
// Only Digital Pin 3,5,6,9,10,11 are allocated for PWM
#define PWM1 5 // PWM Pin 6
#define PWM2 6 // PWM Pin 10

/* Define part for LCD pin */
// Connect pin 15 (next to D7) to 5V, pin 16 to GND
#define RS 13
#define EN 12
#define D4 11
#define D5 10
#define D6 9
#define D7 8

/* Define part for payload size (size of data sent to master node) */
#define PAYLOAD_SIZE 13

/* Define part for command sent from coordinator */
#define ACTIVE_MOTOR 0
#define ACTIVE_NODE 1
#define DEACTIVE_NODE 2

/* Initialize part */
// TinyGPSPlus gps;
// SoftwareSerial ss(rxPin,txPin);
LiquidCrystal lcd(RS,EN,D4,D5,D6,D7); //LCD on pin 13,12,11,10,9,8
XBee xbee;
DHT dht(DHT_PIN, DHT_TYPE);
Rx16Response rx16; //Response object to handle incomming messages 


const char degree=223;  //Special symbol of degrees
const int sensorPin = A0;  //Soil humidity on analog pin 0
char a='0';



/* GPS Transmission Variable */
uint8_t lowLowLat; //8 low bit of Low Lat
uint8_t highLowLat; //8 high bit of Low Lat
uint8_t lowHighLat; //8 low bit of High Lat
uint8_t highHighLat; //8 high bit of Low Lat

uint8_t lowLowLong; //8 low bit of Low Long
uint8_t highLowLong; //8 high bit of Low Long
uint8_t lowHighLong; //8 low bit of High Long
uint8_t highHighLong; //8 high bit of High Long

/* Humidity tranmission variable */
int humidity;

/* Temperature transmission variable */
float temperature;
uint8_t lowTemp;
uint8_t highTemp;

/* Tranmission all information variable */
uint8_t payload[PAYLOAD_SIZE]; //Array contains data to send to the master node

/* Number of packets transmitted */
uint8_t packetsTransmitted = 1;
uint8_t times = 0; // Total packets transmitted = packetsTransmitted + 255 * times;
                   // Whenver packetTransmitted reach 255, it will be resetted to 0 and times will increase by 1

/* Command choose variable */
volatile boolean startingToGetData = false; // This variable will be set by coordinator, 
                                   //coordinator can send command to active the node to start collecting data from the environment


/* Function declaration */
void getTemp(); // Get temperature and save to global variable
void getHumidity(); // Get humidity and save to global variable
void getGPSLocation(); // Get GPS Location and save to global variable
void sendInformationToMaster(); // Send the information to master node
void displayTempAndHumidToLCD(); // Display information on LCD
//void feedGPS(); // This function will return when GPS has read one full sentence
void runMotorForward(int speed, int time); // Run motor forward
void runMotorBackward(int speed, int time); // Run motor backward
void increaseNumOfPacketsTransmitted(); // Count the number of packets that have been transmitted
void checkCommandSentFromCoordinator(); // Check what type of command the coordinator send (run motor or active node)

void setup() {
    Serial.begin(XBEE_BAUDRATE); //Initialize Serial
//    lcd.begin(16,2); //Initialize LCD
//    ss.begin(9600); //SoftwareSerial begin ( for GPS ) Baud rate is 9600 (dont change this num)
    xbee.setSerial(Serial); //Xbee use hardware serial
    dht.begin();
    rx16 = Rx16Response();
}

void loop() {
    checkCommandSentFromCoordinator();
    if (startingToGetData == true) {
        getTemp();
        getHumidity();
//      feedGPS();
        getGPSLocation();
//      displayTempAndHumidToLCD();
        sendInformationToMaster();
        increaseNumOfPacketsTransmitted();
        delay(3000);
    }
}

/* Get temperature and save to global variable*/
void getTemp() {
    /* Get Temp for DS18S20 
    sensorTemp.requestTemperatures();
    float temperature = sensorTemp.getTempCByIndex(0);
    int temp = temperature * 100;
    lowTemp = temp & 0xff;
    highTemp = (temp >> 8) & 0xff;
    */

    /* Get Temp for DHT11 */
    temperature = dht.readTemperature();
    int temp = temperature * 100; //Nhan voi 100 de lay 2 so thap phan sau dau phay
    lowTemp = temp & 0xff;
    highTemp = (temp >> 8) & 0xff;
}

/* Get humidity and save to global variable */
void getHumidity() {
    humidity = analogRead(HUMIDITY_PIN);
    humidity = map(humidity, 1023, 0, 0, 100);
}

/* Display information on LCD */
void displayTempAndHumidToLCD() {
    lcd.setCursor(0,0);
    lcd.print("Humidity: ");
    lcd.setCursor(10,0);                // Set the cursor after Humidity:
    lcd.print(humidity);
    lcd.print("%");
    lcd.setCursor(0,1);
    lcd.print("Temp: ");
    lcd.setCursor(6,1);                //Set the cursor after Temperature:
    lcd.print(temperature);        
    lcd.print(degree);
    lcd.print("C");
}

/* Get GPS Location and save to global variable */
void getGPSLocation() {
    /* Real version (currently closed)*/
    /*
    if (gps.location.isUpdated()) { //If NMEA sequence is valid
        double realLat = gps.location.lat();
        unsigned long latt = realLat * 1000000L; //Nhan voi 1000000L de lay 6 so thap phan sau dau phay
        unsigned int lowLat = latt & 0xffff;
        unsigned int highLat = (latt >> 16) & 0xffff;
        lowLowLat = lowLat & 0xff;
        highLowLat = (lowLat >> 8) & 0xff;
        lowHighLat = highLat & 0xff;
        highHighLat = (highLat >> 8) & 0xff;

        double realLng = gps.location.lng();
        unsigned long lngg = realLng * 1000000L; //Nhan voi 1000000L de lay 6 so thap phan sau dau phay
        unsigned int lowLong = lngg & 0xffff;
        unsigned int highLong = (lngg >> 16) & 0xffff;
        lowLowLong = lowLong & 0xff;
        highLowLong = (lowLong >> 8) & 0xff;
        lowHighLong = highLong & 0xff;
        highHighLong = (highLong >> 8) & 0xff;
    }
    */   

    /* Demo version */
    double realLat = BACHKHOA_LAT;
    unsigned long latt = realLat * 1000000L; //Nhan voi 1000000L de lay 6 so thap phan sau dau phay
    unsigned int lowLat = latt & 0xffff;
    unsigned int highLat = (latt >> 16) & 0xffff;
    lowLowLat = lowLat & 0xff;
    highLowLat = (lowLat >> 8) & 0xff;
    lowHighLat = highLat & 0xff;
    highHighLat = (highLat >> 8) & 0xff;

    double realLng = BACHKHOA_LONG;
    unsigned long lngg = realLng * 1000000L; //Nhan voi 1000000L de lay 6 so thap phan sau dau phay
    unsigned int lowLong = lngg & 0xffff;
    unsigned int highLong = (lngg >> 16) & 0xffff;
    lowLowLong = lowLong & 0xff;
    highLowLong = (lowLong >> 8) & 0xff;
    lowHighLong = highLong & 0xff;
    highHighLong = (highLong >> 8) & 0xff;
}

/* Send the information to master node */
void sendInformationToMaster() {
    payload[0] = (uint8_t)humidity;
    payload[1] = lowTemp;
    payload[2] = highTemp;
    payload[3] = lowLowLat;
    payload[4] = highLowLat;
    payload[5] = lowHighLat;
    payload[6] = highHighLat;
    payload[7] = lowLowLong;
    payload[8] = highLowLong;
    payload[9] = lowHighLong;
    payload[10] = highHighLong;
    payload[11] = packetsTransmitted;
    payload[12] = times;
    Tx16Request tx = Tx16Request(MASTER_ADDRESS, payload, sizeof(payload)); //Address of master node is 0x1234
    xbee.send(tx); //Send the data
}

//void feedGPS() {
//    boolean readSentenceSuccessful;
//    while (true) {
//        while (Serial.available() > 0) 
//            readSentenceSuccessful = gps.encode(Serial.read());      
//        if (readSentenceSuccessful == true)
//            break;
//    }
//}

void runMotorForward(int speed, int time) {
    analogWrite(PWM1, speed);
    analogWrite(PWM2, 0);
    delay(time);
    analogWrite(PWM1, 0);
}

void runMotorBackward(int speed, int time) {
    analogWrite(PWM2, speed);
    analogWrite(PWM1, 0);
    delay(time);
    analogWrite(PWM2, 0);
}

/* Count the number of packets that have been transmitted */
void increaseNumOfPacketsTransmitted() {
    packetsTransmitted++;
    if (packetsTransmitted >= 255) {
        packetsTransmitted = 1;
        times++;
    }
}

/* Checking what type of command sent from coordinator */
void checkCommandSentFromCoordinator() {

    uint8_t choice; // The command from coordinator

   xbee.readPacket();
   if (xbee.getResponse().isAvailable()) {
        if (xbee.getResponse().getApiId() == RX_16_RESPONSE) {

            xbee.getResponse().getRx16Response(rx16);
            choice = rx16.getData(0);
            if (choice == ACTIVE_MOTOR) {
                uint8_t speed = rx16.getData(1);
                uint8_t time = rx16.getData(2);
                runMotorForward(speed, time * 1000);
            }
            else if (choice == ACTIVE_NODE) {
                startingToGetData = true;
            }
            else if (choice == DEACTIVE_NODE) {
                startingToGetData = false;
            }

        }       
   } 
}
