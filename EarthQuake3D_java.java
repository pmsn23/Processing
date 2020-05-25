import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class EarthQuake3D_java extends PApplet {

// DS 6390 Muthu Palanisamy                
// HW10/Final Seismic Activities 3D visualizatioin in Java                  

// Data Source: 
// https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.csv

// Image Source:
// https://eoimages.gsfc.nasa.gov/images/imagerecords/73000/73909/world.topo.bathy.200412.3x5400x2700.jpg


PFont titleFont, placeFont;
PImage world;
PShape globe;
float angle;
int r = 360;
float x=width;
int index=0;

float [][] eqData;
String [][] eqPlace;
String [] place;
int count=0;

EQLoc eq;

public void setup(){

  
  eqData = new float[0][3];
  eqPlace = new String[0][1];
  titleFont = createFont("GillSans-Bold", 50);
  placeFont = createFont("GillSans-Bold", 16);
  eq = new EQLoc();
  eq.p = this;

}

public void draw() {
  
  background(0);
  loadData();
    
  fill(255, 175, 0);
  textFont(titleFont);
  text("Seismic Activity", 320,70);
  fill(255);
  scrollPlace();
  spin();
  eq.plot();
  
}

public void spin() {
  noFill();
  world = loadImage("worldmap.jpg");             
  
  noStroke();
  globe = createShape(SPHERE, r);
  globe.setTexture(world);                        
  
  translate(width/2, height/2);
  lights();
  angle += 0.1f;
  switch(key){
  case CODED:
    if (keyCode == UP){
      rotateX(angle);
    }else if (keyCode == DOWN){
      angle -= 0.2f;
      rotateX(angle);
    }else if (keyCode == LEFT){
      angle -= 0.2f;
      rotateY(angle);  
    }else{
      rotateY(angle);
    }
    break;
  case 's':
    rotateY(angle);
    break;
  case 'f':
    angle +=.5f;
    rotateY(angle);
    break;
  default:
    rotateY(angle);
    break;  
  }                            
  shape(globe); 
}

public void loadData(){
  Table EQ = loadTable("all_month.csv","header");
  eqData = new float[EQ.getRowCount()][3]; 
  eqPlace = new String[EQ.getRowCount()][1];
  
  for (int i = 0; i<EQ.getRowCount(); i++){
    
    TableRow row = EQ.getRow(i);
    eqData[i][0] = row.getFloat("latitude");
    eqData[i][1] = row.getFloat("longitude");
    eqData[i][2] = row.getFloat("mag");
    eqPlace[i][0] = row.getString("place");
    if (eqData[i][2] >= 6){  
      count +=  1;
    } 
  }
  eq.eqData = eqData;
  eq.eqPlace = eqPlace;
}

public void scrollPlace(){
  String[] place = new String[count];
  int j=0;
  for (int i=0; i<eqData.length; i++){
    if (eqData[i][2] >= 6){
      place[j] = eqPlace[i][0];
      j += 1;
    } 
  }
  textAlign (LEFT);
  textFont(placeFont);
  x = x - 100;
  text(place[index], x, height-20);
  float w = textWidth(place[index]);
  if (x<- -w){
    x = width;
    index = (index + 1) % place.length;
  }
}    
  public void settings() {  size(970, 970, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "EarthQuake3D_java" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
