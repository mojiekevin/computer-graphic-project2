package cs4620.mesh.gen;

import cs4620.mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Sphere Mesh
 * @author Cristian
 * @author Minmin Gong (Revised 9/6/2015)
 * @author Wen Fan (Revised 9/6/2015)
 */
public class MeshGenSphere extends MeshGenerator {
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// TODO#A1 SOLUTION START 

		// Calculate Vertex And Index Count
		outData.vertexCount = (opt.divisionsLongitude+1) * (opt.divisionsLatitude+1);
		outData.indexCount = opt.divisionsLatitude * opt.divisionsLongitude * 2 * 3;
		
		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);
		
		// Add UV Coordinates
		float u = (float)(-1.0 / opt.divisionsLongitude);
		float v = (float)(-1.0 / opt.divisionsLatitude);
		
		// Create The Vertices and Add Normals
		for (int i = 0; i <= opt.divisionsLongitude; i++ ){
			float Omega = (float)(i * (2*Math.PI / opt.divisionsLongitude));
			u = (float)(u + 1.0 / opt.divisionsLongitude);
			for (int j = 0; j <= opt.divisionsLatitude; j++){
				float Theta = (float)(Math.PI-j * (Math.PI / opt.divisionsLatitude));
				v = (float)(v + 1.0 / opt.divisionsLatitude);
				float z = (float)(Math.sin(Theta) * Math.cos(Omega+Math.PI));
				float x = (float)(Math.sin(Theta) * Math.sin(Omega+Math.PI));
				float y = (float)(Math.cos(Theta));
				float x_1 = (float)(x/Math.sqrt(x*x+y*y+z*z));
				float y_1 = (float)(y/Math.sqrt(x*x+y*y+z*z));
				float z_1 = (float)(z/Math.sqrt(x*x+y*y+z*z));
				if (i == opt.divisionsLongitude && j == opt.divisionsLatitude){
					outData.positions.put(new float[]{x,y,z});
					outData.uvs.put(new float[]{u,v});
					outData.normals.put(new float[]{x_1,y_1,z_1});
				}	
				else{
					outData.positions.put(new float[]{x,y,z,});
					outData.uvs.put(new float[]{u,v,});
					outData.normals.put(new float[]{x_1,y_1,z_1,});
				}

			}
			v = (float)(-1.0 / opt.divisionsLatitude);
		}
		
		// Add Indices
		for(int m=0;m<=(opt.divisionsLatitude+1)*(opt.divisionsLongitude-1);m+=(opt.divisionsLatitude+1) ){
			for(int f = m;f <= m+opt.divisionsLatitude-1;f++) {
				outData.indices.put(f);
				outData.indices.put(f + opt.divisionsLatitude+1);
				outData.indices.put(f + 1);
				outData.indices.put(f + opt.divisionsLatitude+2);
				outData.indices.put(f + 1);
				outData.indices.put(f + opt.divisionsLatitude+1);
			}
		}


		// #SOLUTION END
	}
}
