package cs4620.ray1.surface;

import cs4620.ray1.IntersectionRecord;
import cs4620.ray1.Ray;
import egl.math.Matrix3d;
import egl.math.Vector3d;
import egl.math.Vector3i;
import cs4620.ray1.shader.Shader;

/**
 * Represents a single triangle, part of a triangle mesh
 *
 * @author ags
 */
public class Triangle extends Surface {
  /** The normal vector of this triangle, if vertex normals are not specified */
  Vector3d norm;
  
  /** The mesh that contains this triangle */
  Mesh owner;
  
  /** 3 indices to the vertices of this triangle. */
  Vector3i index;
  
  double a, b, c, a1, b1, c1, d, e, f;
  public Triangle(Mesh owner, Vector3i index, Shader shader) {
    this.owner = owner;
    this.index = new Vector3i(index);
    
    Vector3d v0 = owner.getPosition(index.x);
    Vector3d v1 = owner.getPosition(index.y);
    Vector3d v2 = owner.getPosition(index.z);
    
    if (!owner.hasNormals()) {
    	Vector3d e0 = new Vector3d(), e1 = new Vector3d();
    	e0.set(v1).sub(v0);
    	e1.set(v2).sub(v0);
    	norm = new Vector3d();
    	norm.set(e0).cross(e1);
    }
    a = v0.x-v1.x;
    b = v0.y-v1.y;
    c = v0.z-v1.z;
    
    a1 = v0.x;
    b1 = v0.y;
    c1 = v0.z;
    
    d = v0.x-v2.x;
    e = v0.y-v2.y;
    f = v0.z-v2.z;
    
    this.setShader(shader);
  }

  /**
   * Tests this surface for intersection with ray. If an intersection is found
   * record is filled out with the information about the intersection and the
   * method returns true. It returns false otherwise and the information in
   * outRecord is not modified.
   *
   * @param outRecord the output IntersectionRecord
   * @param rayIn the ray to intersect
   * @return true if the surface intersects the ray
   */
  public boolean intersect(IntersectionRecord outRecord, Ray rayIn) {
    // TODO#A2: fill in this function.
	Vector3d ve = new Vector3d();
	Vector3d vd = new Vector3d();
	ve.set(rayIn.origin);
	vd.set(rayIn.direction);
	Matrix3d AM = new Matrix3d(
			a, d, vd.x,
			b, e, vd.y,
			c, f, vd.z);
	Matrix3d BetaM = new Matrix3d(
			a1-ve.x, d, vd.x,
			b1-ve.y, e, vd.y,
			c1-ve.z, f, vd.z);
	Matrix3d GammaM = new Matrix3d(
			a, a1-ve.x, vd.x,
			b, b1-ve.y, vd.y,
			c, c1-ve.z, vd.z);
	Matrix3d tM = new Matrix3d(
			a, d, a1-ve.x,
			b, e, b1-ve.y,
			c, f, c1-ve.z);	
	double Beta = BetaM.determinant()/AM.determinant();
	double Gamma = GammaM.determinant()/AM.determinant();
	double Arfa = 1 - Beta - Gamma;
	double t = tM.determinant()/AM.determinant();
	if(t < rayIn.start || t > rayIn.end){
		return false; 
	}
	if(Gamma < 0 || Gamma > 1){
		return false;
	}
	if(Beta < 0 || Beta > 1 - Gamma){
		return false;
	}
	outRecord.location.set(ve.add(vd.mul(t)));
	Vector3d V1 = new Vector3d();
	Vector3d V2 = new Vector3d();
	Vector3d V3 = new Vector3d();
	if (owner.hasNormals()){
		V1.set(owner.getNormal(index.x));
		V2.set(owner.getNormal(index.y));
		V3.set(owner.getNormal(index.z));
		outRecord.normal.set((V1.mul(Arfa).add(V2.mul(Beta).add(V3.mul(Gamma)))).normalize());
	}
	else{
		outRecord.normal.set(norm.normalize());
	}
	outRecord.t = t;
	outRecord.surface = this;
	return true;
  }	

  /**
   * @see Object#toString()
   */
  public String toString() {
    return "Triangle ";
  }
}