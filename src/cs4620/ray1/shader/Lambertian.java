package cs4620.ray1.shader;

import cs4620.ray1.IntersectionRecord;
import cs4620.ray1.Light;
import cs4620.ray1.Ray;
import cs4620.ray1.Scene;
import egl.math.Color;
import egl.math.Colord;
import egl.math.Vector3d;

/**
 * A Lambertian material scatters light equally in all directions. BRDF value is
 * a constant
 *
 * @author ags
 */
public class Lambertian extends Shader {

	/** The color of the surface. */
	protected final Colord diffuseColor = new Colord(Color.White);
	public void setDiffuseColor(Colord inDiffuseColor) { diffuseColor.set(inDiffuseColor); }

	public Lambertian() { }
	
	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return "lambertian: " + diffuseColor;
	}

	/**
	 * Evaluate the intensity for a given intersection using the Lambert shading model.
	 * 
	 * @param outIntensity The color returned towards the source of the incoming ray.
	 * @param scene The scene in which the surface exists.
	 * @param ray The ray which intersected the surface.
	 * @param record The intersection record of where the ray intersected the surface.
	 * @param depth The recursion depth.
	 */
	@Override
	public void shade(Colord outIntensity, Scene scene, Ray ray, IntersectionRecord record) {
		// TODO#A2: Fill in this function.
		// 1) Loop through each light in the scene.
		// 2) If the intersection point is shadowed, skip the calculation for the light.
		//	  See Shader.java for a useful shadowing function.
		// 3) Compute the incoming direction by subtracting
		//    the intersection point from the light's position.
		// 4) Compute the color of the point using the Lambert shading model. Add this value
		//    to the output.
		outIntensity.set(0,0,0);
		for(int i = 0; i < scene.getLights().size(); i++){
			Ray raycopy = new Ray();
			if(!isShadowed(scene, scene.getLights().get(i), record, raycopy)){
				Vector3d omega_i = new Vector3d();
				Colord k_d =  new Colord();
				Colord k_L =  new Colord();
				Vector3d n = new Vector3d();
				double r2 = scene.getLights().get(i).position.distSq(record.location);
				omega_i.set(scene.getLights().get(i).position).sub(record.location).normalize();
				k_d.set(diffuseColor);
				k_L.set(scene.getLights().get(i).intensity);
				n.set(record.normal);
				outIntensity.add(k_d.mul(Math.max(n.dot(omega_i), 0)).mul(k_L.div(r2)));	
			}
		}
	}

}