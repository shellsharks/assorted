#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BNO055.h>
#include <utility/imumaths.h>

/* Set the delay between fresh samples */
#define BNO055_SAMPLERATE_DELAY_MS (100)

#define SLAVE_ADDRESS 0x04 //added
#define N 4

int16_t values[N];

int x1 = 0;
int x2 = 0;
int x3 = 0;
int xneg = 0;
int y1 = 0;
int y2 = 0;
int y3 = 0;
int yneg = 0;
int z1 = 0;
int z2 = 0;
int z3 = 0;
int zneg = 0;

Adafruit_BNO055 bno = Adafruit_BNO055();

void setup(void)
{
  Serial.begin(9600);
  Serial.println("Orientation Sensor Raw Data Test"); Serial.println("");

  Wire.begin(SLAVE_ADDRESS); //added
  Wire.onReceive(receiveEvent);
  Wire.onRequest(requestEvent);

  /* Initialise the sensor */
  if(!bno.begin())
  {
    /* There was a problem detecting the BNO055 ... check your connections */
    Serial.print("Ooops, no BNO055 detected ... Check your wiring or I2C ADDR!");
    while(1);
  }

  delay(1000);

  /* Display the current temperature */
  int8_t temp = bno.getTemp();
  Serial.print("Current Temperature: ");
  Serial.print(temp);
  Serial.println(" C");
  Serial.println("");

  bno.setExtCrystalUse(true);

  Serial.println("Calibration status values: 0=uncalibrated, 3=fully calibrated");
}

int isNeg(float val) {
  if (val < 0) {
    return 1;
  }
  else {
    return 0;
  }
}

void format(float v1, float v2, float v3) {
  xneg = isNeg(v1);
  yneg = isNeg(v2);
  zneg = isNeg(v3);
  x3 = (v1 - (int)v1)*100;
  y3 = (v2 - (int)v2)*100;
  z3 = (v3 - (int)v3)*100;
  
  int val1 = abs(v1);
  if (val1 > 255) {
    x1 = 255;
    x2 = val1-255;
  }
  else {
    x1 = val1;
    x2 = 0;
  }
  int val2 = abs(v2);
  if (val2 > 255) {
    y1 = 255;
    y2 = val2-255;
  }
  else {
    y1 = val2;
    y2 = 0;
  }
  int val3 = abs(v3);
  if (val3 > 255) {
    z1 = 255;
    z2 = val3-255;
  }
  else {
    z1 = val3;
    z2 = 0;
  }
}

void loop(void)
{
  delay(1000);

  // Possible vector values can be:
  // - VECTOR_ACCELEROMETER - m/s^2
  // - VECTOR_MAGNETOMETER  - uT
  // - VECTOR_GYROSCOPE     - rad/s
  // - VECTOR_EULER         - degrees
  // - VECTOR_LINEARACCEL   - m/s^2
  // - VECTOR_GRAVITY       - m/s^2
  imu::Vector<3> euler = bno.getVector(Adafruit_BNO055::VECTOR_EULER);

  /* Display the floating point data */
  Serial.print("Yaw: ");
  Serial.print(euler.x());
  Serial.print(" Roll: ");
  Serial.print(euler.y());
  Serial.print(" Pitch: ");
  Serial.print(euler.z());
  Serial.print("\t\t");

  format(euler.x(), euler.y(), euler.z());

  /* Display calibration status for each sensor. */
  uint8_t system, gyro, accel, mag = 0;
  bno.getCalibration(&system, &gyro, &accel, &mag);
  Serial.print("CALIBRATION: Sys=");
  Serial.print(system, DEC);
  Serial.print(" Gyro=");
  Serial.print(gyro, DEC);
  Serial.print(" Accel=");
  Serial.print(accel, DEC);
  Serial.print(" Mag=");
  Serial.println(mag, DEC);

  delay(BNO055_SAMPLERATE_DELAY_MS);
}

void receiveEvent(int nBytes) {
  Serial.print("Received: ");
  Serial.println(nBytes);
  Serial.println(Wire.read());
}

void requestEvent() {
  Serial.println("Data requested");
  byte buffer[13] = {x1,x2,x3,xneg,y1,y2,y3,yneg,z1,z2,z3,zneg,4};
  Wire.write(buffer,N*4);
}
