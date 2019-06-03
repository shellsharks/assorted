bool sentinel = false; //Boolean for when sentinel character is present or no
int t = 200; //Time inbetween dot signals
int yellow = 12; //Yellow LED connected to pin 12
int blue = 11; //Blue LED connected to pin 11

//Set up Arduino and initialize serial connection
void setup() {
  pinMode(yellow, OUTPUT);
  pinMode(blue, OUTPUT);
  Serial.begin(9600);
}

//Wait for input over serial connection and terminate processing when sentinel character is present
void loop() {
  while (!sentinel && Serial.available()) {
    String serial = Serial.readString();
    for (int i = 0; !sentinel && i < serial.length(); i++) {
      sendChar(serial.charAt(i));
    }
  }
}

//Process respective character
void sendChar(char eng) {
  switch (toLowerCase(eng)) {
    case '$': Serial.println("Sentinel!"); Serial.println("Transmission Ended"); sp(); sp(); sentinel = true; break;
    case 'a': Serial.print("a: "); dotdash(B10,B10); break;
    case 'b': Serial.print("b: "); dotdash(B0001,B100); break;
    case 'c': Serial.print("c: "); dotdash(B0101,B100); break;
    case 'd': Serial.print("d: "); dotdash(B001,B11); break;
    case 'e': Serial.print("e: "); dotdash(B0,B1); break;
    case 'f': Serial.print("f: "); dotdash(B0100,B100); break;
    case 'g': Serial.print("g: "); dotdash(B011,B11); break;
    case 'h': Serial.print("h: "); dotdash(B0000,B100); break;
    case 'i': Serial.print("i: "); dotdash(B00,B10); break;
    case 'j': Serial.print("j: "); dotdash(B1110,B100); break;
    case 'k': Serial.print("k: "); dotdash(B101,B11); break;
    case 'l': Serial.print("l: "); dotdash(B0010,B100); break;
    case 'm': Serial.print("m: "); dotdash(B11,B10); break;
    case 'n': Serial.print("n: "); dotdash(B01,B10); break;
    case 'o': Serial.print("o: "); dotdash(B111,B11); break;
    case 'p': Serial.print("p: "); dotdash(B0110,B100); break;
    case 'q': Serial.print("q: "); dotdash(B1011,B100); break;
    case 'r': Serial.print("r: "); dotdash(B010,B11); break;
    case 's': Serial.print("s: "); dotdash(B000,B11); break;
    case 't': Serial.print("t: "); dotdash(B1,B1); break;
    case 'u': Serial.print("u: "); dotdash(B100,B11); break;
    case 'v': Serial.print("v: "); dotdash(B1000,B100); break;
    case 'w': Serial.print("w: "); dotdash(B110,B11); break;
    case 'x': Serial.print("x: "); dotdash(B1001,B100); break;
    case 'y': Serial.print("y: "); dotdash(B1101,B100); break;
    case 'z': Serial.print("z: "); dotdash(B0011,B100); break;
    case '\n': Serial.print("Ready For Input!\n"); sp(); sp(); break;
    case ' ': Serial.println("space"); sp(); break;
    default: Serial.print("not found");
  } 
}

void dotdash(byte character, byte len) {
  byte tmp = character;
  
  for (int i = 0; i < len; i++) {
    //Serial.print(tmp);
    if ((tmp & 1) > 0) {
      dash();
    } 
    else { 
      dot(); 
    }
    tmp = tmp >> 1;
    delay(t*2);
  }
  Serial.print("\n");
  sp();
}

//Flash the blue LED
void sp() {
  digitalWrite(blue, HIGH);
  delay(t*3);
  digitalWrite(blue, LOW);
  delay(t*3);
}

//Flash yellow LED if it is a dot
void dot() {
  digitalWrite(yellow, HIGH); Serial.print(" . ");
  delay(t);
  digitalWrite(yellow, LOW);
}

//Flash yellow LED if it is a dash
void dash() {
  digitalWrite(yellow, HIGH); Serial.print(" - ");
  delay(t*4);
  digitalWrite(yellow, LOW);
}
