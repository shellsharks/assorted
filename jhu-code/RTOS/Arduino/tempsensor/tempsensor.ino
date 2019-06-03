/* Michael Sass
 * EN.605.715.83.SP19 (Software Dev for Real-Time Systems)
 * Project 2 - Serial Transmit of Temperature
 * 
 * Inspiration / code gathered heavily from https://www.robotshop.com/community/forum/t/arduino-101-timers-and-interrupts/13072
 */

//Include library for DGT 11 temperature/humidity sensor
#include <dht.h> 

//Instatiate DHT class
dht DHT; 

//Define pin 7
#define DHT11_PIN 7

//Counter which increments when timer ISR is invoked.
volatile int isrCtr = 0;

//Temperature check flag which is polled by main loop
volatile bool checkTemp = false;

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);
  Serial.println("Michael Sass | EN.605.715.83.SP19 | Project 2");
  delay(1000);
  Serial.println("Initiating System...\n");

  noInterrupts(); //Disable interrupts
  TCCR1A = 0; //Set timer/counter control register to 0
  TCCR1B = 0; //Set timer/counter control register to 0
  OCR1A = 31250; //Set compare register
  TCCR1B |= (1 << WGM12); 
  TCCR1B |= (1 << CS12); 
  TIMSK1 |= (1 << OCIE1A); //Enable compare
  interrupts(); //Re-enable interrupts
}

void loop() {
  //If the interrupt code has been triggered 20 times, checkTemp flag is set and this code runs.
  if (checkTemp) {
    checkTemp = false; //Toggle back to false
    int chk = DHT.read11(DHT11_PIN); //read temperature from sensor
    int temp = (int)((9.0/5.0) * DHT.temperature + 32.0); //convert raw reading from celsius to fahrenheit

    //Print time elapsed in Xmin Ysec
    int time = millis()/1000;
    Serial.print("Time Elapsed: ");
    Serial.print(time/60);
    Serial.print("min ");
    if (time < 60) {
      Serial.print(time);
    }
    else {
      Serial.print(time % 60);
    }
    Serial.print("sec | ");

    //Print temperature to serial output
    Serial.print("Temperature: ");
    Serial.print(temp);
    Serial.print(char(194)); 
    Serial.print(char(176));
    Serial.println(" Fahrenheit");
  }
}

ISR(TIMER1_COMPA_vect) {
  isrCtr++; //Increment interrupt counter by 1

  //If ISR has been called 20 times, blink built-in LED and set checkTemp flag to true.
  if (isrCtr == 20) {
    digitalWrite(LED_BUILTIN, digitalRead(LED_BUILTIN) ^ 1);
    isrCtr = 0;
    checkTemp = true;
  }
}
