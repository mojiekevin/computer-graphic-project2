package cs4620.ray1.surface;

import cs4620.ray1.IntersectionRecord;
import cs4620.ray1.Ray;
import egl.math.Vector3d;

public class Cylinder extends Surface {

  /** The center of the bottom of the cylinder  x , y ,z components. */
  protected final Vector3d center = new Vector3d();
  public void setCenter(Vector3d center) { this.center.set(center); }

  /** The radius of the cylinder. */
  protected double radius = 1.0;
  public void setRadius(double radius) { this.radius = radius; }

  /** The height of the cylinder. */
  protected double height = 1.0;
  public void setHeight(double height) { this.height = height; }

  public Cylinder() { }

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
	// TODO#A2 (extra credit): Fill in this function, and write an xml file with a cylinder in it.
		Vector3d ve = new Vector3d();
		Vector3d vd = new Vector3d();
		ve.set(rayIn.origin);
		vd.set(rayIn.direction);
		double a = Math.pow(vd.x, 2)+Math.pow(vd.y, 2);
		double b = 2*(ve.x-center.x)*vd.x+2*(ve.y-center.y)*vd.y;
		double c = Math.pow(ve.x-center.x, 2)+Math.pow(ve.y-center.y, 2)-Math.pow(radius, 2);
		double d = b*b - 4*a*c;
		if (d < 0){
			return false;
		}
		else if (d == 0){
			double t = -0.5*b/a;
			if(t < rayIn.start || t > rayIn.end){
				return false; 
			}
			double z1 = ve.z+t*vd.z;
			if (z1<(center.z-height/2)||z1>(center.z+height/2)){
				return false;
			}
			else{
				  Vector3d p = new Vector3d();
				  p.set(ve.addMultiple(t, vd));
				  outRecord.location.set(p);
				  outRecord.normal.set(p.x,p.y,0);
				  outRecord.normal.normalize();
				  outRecord.surface = this;
				  outRecord.t = t;
				  return true;
			}
		}
		else{
			double t1 = (-b+Math.sqrt(d))/(2*a);
			double t2 = (-b-Math.sqrt(d))/(2*a);
			double t = Math.min(t1, t2);
			if(t < rayIn.start || t > rayIn.end){
				return false; 
			}
			double z2 = ve.z+t*vd.z;
			if (z2<(center.z-height/2)||z2>(center.z+height/2)){
				if(z2>(center.z+height/2)){
					double t3 = (height/2-ve.z)/vd.z;
					if(Math.pow(ve.x+t3*vd.x,2)+Math.pow(ve.y+t3*vd.y,2)<=Math.pow(radius, 2)){
						  Vector3d p = new Vector3d();
						  p.set(ve.addMultiple(t3, vd));
						  outRecord.location.set(p);
						  outRecord.normal.set(0,0,1);
						  outRecord.surface = this;
						  outRecord.t = t3;
						  return true;
					}
				}
				if(z2<(center.z-height/2)){
					double t4 = (-height/2-ve.z)/vd.z;
					if(Math.pow(ve.x+t4*vd.x,2)+Math.pow(ve.y+t4*vd.y,2)<=Math.pow(radius, 2)){
						  Vector3d p = new Vector3d();
						  p.set(ve.addMultiple(t4, vd));
						  outRecord.location.set(p);
						  outRecord.normal.set(0,0,-1);
						  outRecord.surface = this;
						  outRecord.t = t4;
						  return true;
					}
				}
				return false;
			}
			else{
				  Vector3d p = new Vector3d();
				  p.set(ve.addMultiple(t, vd));
				  outRecord.location.set(p);
				  outRecord.normal.set(p.x,p.y,0);
				  outRecord.normal.normalize();
				  outRecord.surface = this;
				  outRecord.t = t;
				  return true;
			}
		}
		
  }

  /**
   * @see Object#toString()
   */
  public String toString() {
    return "Cylinder " + center + " " + radius + " " + height + " "+ shader + " end";
  }
}
