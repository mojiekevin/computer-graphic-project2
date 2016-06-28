package cs4620.mesh.gen;

import cs4620.mesh.MeshData;
import egl.NativeMem;
import egl.math.Matrix4;
import egl.math.Vector3;

/**
 * Generates A Torus Mesh
 * @author Cristian
 * @author Minmin Gong (Revised 9/7/2015)
 * @author Wen Fan (Revised 9/7/2015)
 */
public class MeshGenTorus extends MeshGenerator {
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// TODO#A1 SOLUTION START
		
		// Calculate Vertex And Index Count
		outData.vertexCount = (opt.divisionsLongitude+1) * (opt.divisionsLatitude+1);
		outData.indexCount = opt.divisionsLongitude * opt.divisionsLatitude * 2 * 3;
		
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
				float Theta = (float)(j * (2*Math.PI / opt.divisionsLatitude));
				v = (float)(v + 1.0 / opt.divisionsLatitude);
				float z = (float)((1 + opt.innerRadius * Math.cos(Theta+Math.PI)) * Math.cos(Omega+Math.PI));
				float x = (float)((1 + opt.innerRadius * Math.cos(Theta+Math.PI)) * Math.sin(Omega+Math.PI));
				float y = (float)(opt.innerRadius * Math.sin(Theta + Math.PI));
				float x_1 = (float)(Math.cos(Omega+Math.PI));
				float y_1 = 0;
				float z_1 = (float)(Math.sin(Omega+Math.PI));
				float x_2 = (float)((x-x_1)/(Math.sqrt((x-x_1)*(x-x_1)+(y-y_1)*(y-y_1)+(z-z_1)*(z-z_1))));
				float y_2 = (float)((y-y_1)/(Math.sqrt((x-x_1)*(x-x_1)+(y-y_1)*(y-y_1)+(z-z_1)*(z-z_1))));
				float z_2 = (float)((z-z_1)/(Math.sqrt((x-x_1)*(x-x_1)+(y-y_1)*(y-y_1)+(z-z_1)*(z-z_1))));
				if (i == opt.divisionsLongitude && j == opt.divisionsLatitude){
					outData.positions.put(new float[]{x,y,z});
					outData.uvs.put(new float[]{u,v});
					outData.normals.put(new float[]{x_2,y_2,z_2});
				}	
				else{
					outData.positions.put(new float[]{x,y,z,});
					outData.uvs.put(new float[]{u,v,});
					outData.normals.put(new float[]{x_2,y_2,z_2,});
				}

			}
			v = (float)(-1.0 / opt.divisionsLatitude);
		}
		// Add Indices
		for(int m=0;m<=(opt.divisionsLatitude+1)*(opt.divisionsLongitude-1);m+=(opt.divisionsLatitude+1) ){
			for(int f = m;f <= m+(opt.divisionsLatitude-1);f++) {
				outData.indices.put(f);
				outData.indices.put(f + (opt.divisionsLatitude+1));
				outData.indices.put(f + 1);
				outData.indices.put(f + (opt.divisionsLatitude+2));
				outData.indices.put(f + 1);
				outData.indices.put(f + (opt.divisionsLatitude+1));
			}
		}
		
		// #SOLUTION END
	}
}
