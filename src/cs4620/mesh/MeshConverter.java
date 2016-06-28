package cs4620.mesh;

import java.util.ArrayList;

import egl.NativeMem;
import egl.math.Vector3;
import egl.math.Vector3i;

/**
 * Performs Normals Reconstruction Upon A Mesh Of Positions
 * @author Cristian
 * @author Minmin Gong (Revised 9/8/2015)
 * @author Wen Fan (Revised 9/8/2015)
 */
public class MeshConverter {
	/**
	 * Reconstruct A Mesh's Normals So That It Appears To Have Sharp Creases
	 * @param positions List Of Positions
	 * @param tris List Of Triangles (A Group Of 3 Values That Index Into The Positions List)
	 * @return A Mesh With Normals That Lie Normal To Faces
	 */
	public static MeshData convertToFaceNormals(ArrayList<Vector3> positions, ArrayList<Vector3i> tris) {
		MeshData data = new MeshData();

		// Notice
		System.out.println("This Feature Has Been Removed For The Sake Of Assignment Consistency");
		System.out.println("This Feature Will Be Added In A Later Assignment");
		
		// Please Do Not Fill In This Function With Code
		
		// After You Turn In Your Assignment, Chuck Norris Will
		// Substitute This Function With His Fiery Will Of Steel
		
		// TODO#A1 SOLUTION START
				
		// #SOLUTION END

		return data;
	}
	/**
	 * Reconstruct A Mesh's Normals So That It Appears To Be Smooth
	 * @param positions List Of Positions
	 * @param tris List Of Triangles (A Group Of 3 Values That Index Into The Positions List)
	 * @return A Mesh With Normals That Extrude From Vertices
	 */
	public static MeshData convertToVertexNormals(ArrayList<Vector3> positions, ArrayList<Vector3i> tris) {
		MeshData data = new MeshData();
		// TODO#A1 SOLUTION START
		// Calculate Vertex And Index Count
		data.vertexCount = positions.size();
		data.indexCount = tris.size()*3;
		
		// Create Storage Spaces
		data.positions = NativeMem.createFloatBuffer(data.vertexCount * 3);
		data.normals = NativeMem.createFloatBuffer(data.vertexCount * 3);
		data.uvs = NativeMem.createFloatBuffer(data.vertexCount * 2);
		data.indices = NativeMem.createIntBuffer(data.indexCount);
		
		// Add Indices and Positions
		Vector3 Vec1 = new Vector3(0.0f,0.0f,0.0f); 
		Vector3 Vec2 = new Vector3(0.0f,0.0f,0.0f);  
		
		for (int i = 0; i < positions.size(); i++){
			data.positions.put(positions.get(i).x);
			data.positions.put(positions.get(i).y);
			data.positions.put(positions.get(i).z);
		}
		for (int i = 0; i < tris.size(); i++){
			data.indices.put(tris.get(i).x);
			data.indices.put(tris.get(i).y);
			data.indices.put(tris.get(i).z);
		}
		
		// Add The Face Normal
		ArrayList<Vector3> facevec = new ArrayList<Vector3>();
		ArrayList<Vector3> normalOfvertex = new ArrayList<Vector3>();
		for (int i = 0; i < tris.size(); i++){
			Vector3 Vec3 = new Vector3(0.0f,0.0f,0.0f);
			Vec1.x = positions.get((tris.get(i).x)).x-positions.get((tris.get(i).y)).x;
			Vec1.y = positions.get((tris.get(i).x)).y-positions.get((tris.get(i).y)).y;
			Vec1.z = positions.get((tris.get(i).x)).z-positions.get((tris.get(i).y)).z;
			Vec2.x = positions.get((tris.get(i).x)).x-positions.get((tris.get(i).z)).x;
			Vec2.y = positions.get((tris.get(i).x)).y-positions.get((tris.get(i).z)).y;
			Vec2.z = positions.get((tris.get(i).x)).z-positions.get((tris.get(i).z)).z;
			// Compute the Cross Product of Vec1 and Vec2 
			Vec3.x = Vec1.y*Vec2.z-Vec1.z*Vec2.y;
			Vec3.y = Vec1.z*Vec2.x-Vec1.x*Vec2.z;
			Vec3.z = Vec1.x*Vec2.y-Vec1.y*Vec2.x;
			facevec.add(Vec3);
		}
		
		// Initialize The Vertex Normal 
		for (int i = 0; i < positions.size(); i++){
			Vector3 Vec4 = new Vector3(0.0f,0.0f,0.0f);
			normalOfvertex.add(Vec4);
		}
		
		// Add The Face's Normal To Its All Vertices
		for (int i = 0; i < facevec.size(); i++){
			normalOfvertex.get((tris.get(i).x)).x += facevec.get(i).x;
			normalOfvertex.get((tris.get(i).x)).y += facevec.get(i).y;
			normalOfvertex.get((tris.get(i).x)).z += facevec.get(i).z;
			normalOfvertex.get((tris.get(i).y)).x += facevec.get(i).x;
			normalOfvertex.get((tris.get(i).y)).y += facevec.get(i).y;
			normalOfvertex.get((tris.get(i).y)).z += facevec.get(i).z;
			normalOfvertex.get((tris.get(i).z)).x += facevec.get(i).x;
			normalOfvertex.get((tris.get(i).z)).y += facevec.get(i).y;
			normalOfvertex.get((tris.get(i).z)).z += facevec.get(i).z;	
		}
		
		// Add The Vertex Normal
		Vector3 Vec5 = new Vector3(0.0f,0.0f,0.0f);
		for (int i = 0; i < normalOfvertex.size(); i++){
			Vec5.x = normalOfvertex.get(i).x;
			Vec5.y = normalOfvertex.get(i).y;
			Vec5.z = normalOfvertex.get(i).z;
			float k = (float)(Math.sqrt(Vec5.x*Vec5.x+Vec5.y*Vec5.y+Vec5.z*Vec5.z));
			Vec5.x =Vec5.x / k;
			Vec5.y =Vec5.y / k;
			Vec5.z =Vec5.z / k;
			data.normals.put(Vec5.x);
			data.normals.put(Vec5.y);
			data.normals.put(Vec5.z);
		}
		// #SOLUTION END
		
		return data;
	}
}
