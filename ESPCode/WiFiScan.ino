#include "WiFi.h"
#include <HTTPClient.h>
#include <ESP32Time.h>

int tempPin = 34;
int irPin1 = 33;
int irPin2 = 32;
ESP32Time rtc;

void setup(){
  Serial.begin(115200);
  rtc.setTime(56, 40, 19, 9, 3, 2022);
  pinMode(tempPin, INPUT);
  pinMode(irPin1, INPUT);
  pinMode(irPin2, INPUT);
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  char wifi_name[] = "TUES";
  char wifi_pass[] = "elsys-bg.org";
  WiFi.begin(wifi_name, wifi_pass);
  while(WiFi.status() != WL_CONNECTED)Serial.println("waiting");
  Serial.println("conected");
}

float readTemperatureSensor(int pin){
  int val = analogRead(pin);
  float c = val * (3300.0 / 4096.0);
  c /= 1000;
  return c;
}

int ir(int irPin){
  long now = rtc.getEpoch();
  now++;
  bool k = true;
  int i = 0;
  long a = rtc.getMicros();
  long c = rtc.getSecond();
  while(i < 2){
    if(1 - digitalRead(irPin)){
      if(k){
        i++;
        k = false;
      }
    }
    else{
      k = true;
    }
  }
  long b = rtc.getMicros();
  long d = rtc.getSecond();
  if (d < c)d+=60;
  long r = b - a + (1000000*(d-c));
  return int(60/(float(r)/1000000));
}

int sendRequest(HTTPClient &http, int a, int b, int c, float d){
  String i = String(a);
  String o = String(b);
  String g = String(c);
  String t = String(d);
  String sending = "{\"inRpm\":\""+i+"\",\"outRpm\":\""+o+"\",\"gear\":\""+g+"\",\"temperature\":\""+t+"\"}";
  Serial.println(sending);
  return http.POST(sending);
}

void loop(){
  if(WiFi.status() == WL_CONNECTED){
    Serial.println("in query");
    WiFiClient client;
    HTTPClient http;
    String ip = "http://192.168.2.39:8080/gear/esp";
    http.begin(client, ip);
    http.addHeader("Content-Type", "application/json");
    int a = ir(irPin1);
    int b = ir(irPin2);
    int c = 1;
    float d = readTemperatureSensor(tempPin);
    sendRequest(http, a, b, c, d);
    http.end();
  }
}
    
