package cs4620.ray1.surface;

import cs4620.ray1.IntersectionRecord;
import cs4620.ray1.Ray;
import egl.math.Vector3d;

/**
 * Represents a sphere as a center and a radius.
 *
 * @author ags
 */
public class Sphere extends Surface {
  
  /** The center of the sphere. */
  protected final Vector3d center = new Vector3d();
  public void setCenter(Vector3d center) { this.center.set(center); }
  
  /** The radius of the sphere. */
  protected double radius = 1.0;
  public void setRadius(double radius) { this.radius = radius; }
  
  protected final double M_2PI = 2*Math.PI;
  
  public Sphere() { }
  
  /**
   * Tests this surface for intersection with ray. If an intersection is found
   * record is filled out with the information about the intersection and the
   * method returns true. It returns false otherwise and the information in
   * outRecord is not modified.
   *
   * @param outRecord the output IntersectionRecord
   * @param ray the ray to intersect
   * @return true if the surface intersects the ray
   */
  public boolean intersect(IntersectionRecord outRecord, Ray rayIn) {
    // TODO#A2: fill in this function.

	  Vector3d e = new Vector3d();
	  Vector3d d = new Vector3d();
      e.set(rayIn.origin);
	  d.set(rayIn.direction);
	  Vector3d e_c = new Vector3d();
	  e_c.set(e.clone().sub(center));
	  
	  double B = 2*(d.x*e_c.x + d.y*e_c.y + d.z*e_c.z);
	  double A = d.x*d.x + d.y*d.y + d.z*d.z;
	  double C = e_c.x*e_c.x + e_c.y*e_c.y +e_c.z*e_c.z - radius*radius;
	  double discriminant = B*B - 4*A*C;
	  
	  if (discriminant <= 0) {
		  return false;
	  } else if (discriminant == 0) {
		  double t = -B / (2*A);
		  if(t < rayIn.start || t > rayIn.end){
			return false; 
		  }
		  Vector3d p = new Vector3d();
		  p.set(e.addMultiple(t, d));
		  outRecord.location.set(p);
		  outRecord.normal.set(p.clone().sub(center));
		  outRecord.normal.normalize();
		  outRecord.surface = this;
		  outRecord.t = t;
		  return true;
	  } else {
		  double t1 = (-B + Math.sqrt(discriminant)) / (2*A);
		  double t2 = (-B - Math.sqrt(discriminant)) / (2*A);
		  double t = Math.min(t1, t2);
		  if(t < rayIn.start || t > rayIn.end){
			return false; 
		  }
		  Vector3d p = new Vector3d();
		  p.set(e.addMultiple(t, d));
		  outRecord.location.set(p);
		  outRecord.normal.set(p.clone().sub(center));
		  outRecord.normal.normalize();
		  outRecord.surface = this;
		  outRecord.t = t;
		  return true;
	  }
  }
  
  /**
   * @see Object#toString()
   */
  public String toString() {
    return "sphere " + center + " " + radius + " " + shader + " end";
  }

}