import processing.core.*;
public abstract class GeoLoc extends PApplet{
  PApplet p;
  int r=360;
  float lat = 0.0f;
  float lon = 0.0f;
  
  public GeoLoc() {
  }
  
  public GeoLoc(PApplet p, float lat, float lon) {
    this.p = p;
    this.lat = lat;
    this.lon = lon;
    
  }
  
  public void plot() {
    float theta = p.radians(lat);
    float phi = p.radians(lon) + p.PI;

    float x = r * p.cos(theta) * p.cos (phi);
    float y = - r * p.sin(theta);
    float z = - r * p.cos(theta) * p.sin (phi);

    p.pushMatrix();
      p.translate(x, y, z);
    p.popMatrix();
  }
}
