import processing.core.*;
public class EQLoc extends GeoLoc{
  
  float [][] eqData;
  String [][] eqPlace;
  int r = 360;
  public EQLoc(){
  }
  public EQLoc(PApplet p, float lat, float lon, float[][] eqData, String[][] eqPlace){
    super(p, lat, lon);
    this.eqData = eqData;
    this.eqPlace = eqPlace;
  }
  
  public void plot() {
    
    for (int i=0; i<eqData.length; i++) {
      // Below method converts latitude and longitude to 3D coordinate system
      float theta = radians(eqData[i][0]);
      float phi = radians(eqData[i][1]) + PI;
      float mag = eqData[i][2];
      String place = eqPlace[i][0];
      float x = r * cos(theta) * cos (phi);
      float y = - r * sin(theta);
      float z = - r * cos(theta) * sin (phi);
      
      p.pushMatrix();
        p.translate(x, y, z);
        p.noStroke();
        // Magnitude 
        // < 2.0 Micro, 2-2.9 Very Minor, 3-3.9 Minor, 4-4.9 Light 5-5.9 Moderate
        // 6-6.9 Strong, 7-7.9 Major, 8-9.9 Great, 10+ - Meteoric.  
        if (mag >=6){
          p.fill(255, 0, 0);
          p.sphere(3);
        }else{
          p.fill(0, 255, 0);
          p.sphere(1);
        }      
      p.popMatrix();
    }
  }
}
