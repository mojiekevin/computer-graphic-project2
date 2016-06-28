package cs4620.mesh.gen;

import cs4620.mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Cylinder Mesh
 * @author Cristian (Original)
 * @author Jimmy (Revised 8/25/2015)
 * @author Minmin Gong (Revised 9/9/2015)
 * @author Wen Fan (Revised 9/9/2015)
 * 
 * Thinking of work: Generate the upper and lower caps' points first,
 * 					 then generate the cylinder's points.
 * 					 Details are in each method.
 */
public class MeshGenCylinder extends MeshGenerator {

	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// TODO#A1 SOLUTION START
		int divideParam=opt.divisionsLongitude;
		
		// Calculate Vertex And Index Count
		outData.vertexCount = (divideParam+1) * 4 + 2;
		outData.indexCount = divideParam * 3 * 4 ;
		
		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);
		
	//--------------------------------------------------------------------//
		// Add Positions For Faces
		
		
		// Face YP: n+1 points, a duplicate point at x-axis (in clockwise)
		for(int n=0; n< divideParam+1; n++) {
			outData.positions.put(new float[] {
					(float) Math.cos(2*Math.PI * n / divideParam), 1, (float) Math.sin(2*Math.PI * n / divideParam)
			});
		}
		// Center of upper circle
		outData.positions.put(new float[] {0,1,0});
		
		// Face YN: n+1 points, a duplicate point at x-axis (in clockwise)
		for(int n=0; n< divideParam+1; n++) {	
			outData.positions.put(new float[] {
				(float) Math.cos(2*Math.PI * n / divideParam), -1, (float) Math.sin(2*Math.PI * n / divideParam)
			});
		}
		// Center of lower circle
		outData.positions.put(new float[] {0,-1,0});
		
		// Outer surface: (n+1) points * 2
		for(int n=0; n< divideParam+1; n++) {
			outData.positions.put(new float[] {
				(float) Math.cos(2*Math.PI * n / divideParam), 1, (float) Math.sin(2*Math.PI * n / divideParam),
			});
		}
		for(int n=0; n< divideParam+1; n++) {
			outData.positions.put(new float[] {
				(float) Math.cos(2*Math.PI * n / divideParam), -1, (float) Math.sin(2*Math.PI * n / divideParam)
			});
		}
		
	//--------------------------------------------------------------------//
		// Add Normals For Faces
		
		// Face YP
		for(int i = 0; i < divideParam+2; i++) { outData.normals.put(0); outData.normals.put(1); outData.normals.put(0); }
		
		// Face YN
		for(int i = 0; i < divideParam+2; i++) { outData.normals.put(0); outData.normals.put(-1); outData.normals.put(0); }
		
		// Outer surface
		for(int n = 0; n < 2*divideParam+1; n++) {
				outData.normals.put(new float[] {
						(float) Math.cos(2*Math.PI * n / divideParam), 0, (float) Math.sin(2*Math.PI * n / divideParam) 
				});
		}

	//--------------------------------------------------------------------//
		// Add UV Coordinates
		
		// Face YP
		for(int n = 0; n < divideParam+1; n++) { 
			outData.uvs.put(new float[] {
				(float)(0.75+0.25*Math.cos(2*Math.PI*n/divideParam)), (float)(0.75-0.25*Math.sin(2*Math.PI*n/divideParam)),
			});
		}
		outData.uvs.put(new float[] {(float)0.75, (float)0.75});
		
		// Face YN
		for(int i = 0; i < divideParam+1; i++) { 
			outData.uvs.put(new float[] {
				(float)(0.25+0.25*Math.cos(2*Math.PI*i/divideParam)), (float)(0.75+0.25*Math.sin(2*Math.PI*i/divideParam))
				});
		}
		outData.uvs.put(new float[] {(float)0.25, (float)0.75});
		
		// Outer surface
		for(int i=0; i<divideParam+1; i++) {
			outData.uvs.put(new float[] { 
				1-(float) i/ (float)divideParam, (float) 0.5,

			}); 
		}
		for(int i=0; i<divideParam+1; i++) {
			outData.uvs.put(new float[] { 
				1-(float)i/(float)divideParam, (float) 0,
			}); 
		}

	//--------------------------------------------------------------------//
		// Add Indices
		
		// Upper cap
		for(int f = 0;f < divideParam;f++) {
			outData.indices.put(f);
			outData.indices.put(divideParam+1);
			outData.indices.put(f+1);
		}

		
		// Lower cap
		for(int f = divideParam+2;f < 2*divideParam+2;f++) {
			outData.indices.put(f+1);
			outData.indices.put(2*divideParam+3);	
			outData.indices.put(f);
		}
		
		// Outer surface
		for(int f = (2*divideParam+4);f < (3*divideParam+4);f++) {
			outData.indices.put(f);
			outData.indices.put(f+1);
			outData.indices.put(f+divideParam+2);
			outData.indices.put(f);
			outData.indices.put(f+divideParam+2);
			outData.indices.put(f+divideParam+1);
		}
		
		// #SOLUTION END
	}
}
