/**
 *
 * viewParams.java
 *
 * Simple class for setting up the viewing and projection transforms for the Transformation
 * Assignment.
 *
 * Students are to complete this class.
 *
 */


import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*; 

import Jama.*;

public class viewParams
{
    
	/**
	 * constructor
	 */
	public viewParams()
	{
        
	}
    
    
    /**
     * This functions sets up the view and projection parameter for a frustrum
     * projection of the scene. See the assignment description for the values
     * for the projection parameters.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the vertex shader.
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
	public void setUpFrustrum (int program, GL2 gl2)
	{
		double scale = 4;
		double[][] scaleAll = new double[4][];
		scaleAll[0] = new double[]{1,0,0,0};
		scaleAll[1] = new double[]{0,scale,0,0};
		scaleAll[2] = new double[]{0,0,1,0};
		scaleAll[3] = new double[]{0,0,0,1};
		Matrix ScaleAll = new Matrix(scaleAll);
		
		double Zradians = 90 * (Math.PI/180);
		double[][] rotatez = new double[4][];
		rotatez[0] = new double[]{Math.cos(Zradians),Math.sin(-Zradians),0,0};
		rotatez[1] = new double[]{Math.sin(Zradians),Math.cos(Zradians),0,0};
		rotatez[2] = new double[]{0,0,1,0};
		rotatez[3] = new double[]{0,0,0,1};
		Matrix ZRot = new Matrix(rotatez);
		
		double Xradians = 50 * (Math.PI/180);
		double[][] rotateX = new double[4][];
		rotateX[0] = new double[]{Math.cos(Xradians),0,Math.sin(Xradians),0};
		rotateX[1] = new double[]{0,1,0,0};
		rotateX[2] = new double[]{Math.sin(-Xradians),0,Math.cos(Xradians),0};
		rotateX[3] = new double[]{0,0,0,1};
		Matrix XRot = new Matrix(rotateX);
		
		double[][] translate = new double[4][];
		translate[0] = new double[]{1,0,0,1};
		translate[1] = new double[]{0,1,0,0};
		translate[2] = new double[]{0,0,1,-1};
		translate[3] = new double[]{0,0,0,1};
		Matrix Translate = new Matrix(translate);
		
		Matrix Transforms = Translate.times(XRot.times(ZRot.times(ScaleAll)));
		
		double[][] modelMatrix = Transforms.getArray();
		float[] floatMatrix2 = new float[16];
		
		int count2 = 0;
		for(int i=0; i<modelMatrix.length; i++){
			for(int j=0; j<4; j++){
				floatMatrix2[count2] = (float) modelMatrix[i][j];
				//System.out.println(floatMatrix2[count2]);
				count2++;
			}
		}
		int matrixPos2 = gl2.glGetUniformLocation(program, "model");
		gl2.glUniformMatrix4fv(matrixPos2,1,true,floatMatrix2,0);
		
		double lookatx = 1.0;
		double lookaty = 0;
		double lookatz = 0;
		
		double epx = 0;
		double epy = 3.0;
		double epz = 3.0;
		
		double upx = 0;
		double upy = 1.0;
		double upz = 0;
		
		
		double nx = epx-lookatx;
		double ny = epy-lookaty;
		double nz = epz-lookatz;

		double nsize = Math.sqrt(nx*nx + ny*ny + nz*nz);
		
		nx = nx/nsize;
		ny = ny/nsize;
		nz = nz/nsize;
		
		double ux = (upy*nz) - (upz*ny);
		double uy = -(upx*nz) + (upz*nx);
		double uz = (upx*ny) - (upy*nx);
		
		double usize = Math.sqrt(ux*ux + uy*uy + uz*uz);
		
		ux = ux/usize;
		uy = uy/usize;
		uz = uz/usize;
		
		
		double vx = (ny*uz) - (nz*uy);
		double vy = -(nx*uz) + (nz*ux);
		double vz = (nx*uy) - (ny*ux);
		
		double r1 = -ux*epx + -uy*epy + -uz*epz;
		double r2 = -vx*epx + -vy*epy + -vz*epz;
		double r3 = -nx*epx + -ny*epy + -nz*epz;
		
		double[][] camera = new double[4][];
		camera[0] = new double[]{ux,uy,uz,r1};
		camera[1] = new double[]{vx,vy,vz,r2};
		camera[2] = new double[]{nx,ny,nz,r3};
		camera[3] = new double[]{0,0,0,1};
		Matrix Camera = new Matrix(camera);
		
		double[][] viewMatrix = Camera.getArray();
		float[] floatMatrix1 = new float[16];
		
		int count1 = 0;
		for(int i=0; i<viewMatrix.length; i++){
			for(int j=0; j<4; j++){
				floatMatrix1[count1] = (float) viewMatrix[i][j];
				//System.out.println(floatMatrix1[count1]);
				count1++;
			}
		}
		int matrixPos1 = gl2.glGetUniformLocation(program, "view");
		gl2.glUniformMatrix4fv(matrixPos1,1,true,floatMatrix1,0);

		double Left = -1.5;
		double Right = 1.0;
		double Top = 1.5;
		double Bottom = -1.5;
		double Near = 1.0;
		double Far = 8.5;
		
		double[][] frustum = new double[4][];
		frustum[0] = new double[]{2*Near/(Right-Left),0,(Right+Left)/(Right-Left),0};
		frustum[1] = new double[]{0,2*Near/(Top-Bottom),(Top+Bottom)/(Top-Bottom),0};
		frustum[2] = new double[]{0,0,-(Far+Near)/(Far-Near),-2*Far*Near/(Far-Near)};
		frustum[3] = new double[]{0,0,-1,0};
		Matrix Frustum = new Matrix(frustum);

		double[][] projectionMatrix = Frustum.getArray();
		float[] floatMatrix = new float[16];
		
		int count = 0;
		for(int i=0; i<projectionMatrix.length; i++){
			for(int j=0; j<4; j++){
				floatMatrix[count] = (float) projectionMatrix[i][j];
				//System.out.println(floatMatrix[count]);
				count++;
			}
		}
		int matrixPos = gl2.glGetUniformLocation(program, "projection");
		gl2.glUniformMatrix4fv(matrixPos,1,true,floatMatrix,0);
		
	}
    
    /**
     * This functions sets up the view and projection parameter for an orthographic
     * projection of the scene. See the assignment description for the values
     * for the projection parameters.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the vertex shader.
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpOrtho (int program, GL2 gl2)
    {
    	double scale = 4;
		double[][] scaleAll = new double[4][];
		scaleAll[0] = new double[]{1,0,0,0};
		scaleAll[1] = new double[]{0,scale,0,0};
		scaleAll[2] = new double[]{0,0,1,0};
		scaleAll[3] = new double[]{0,0,0,1};
		Matrix ScaleAll = new Matrix(scaleAll);
		
		double Zradians = 90 * (Math.PI/180);
		double[][] rotatez = new double[4][];
		rotatez[0] = new double[]{Math.cos(Zradians),Math.sin(-Zradians),0,0};
		rotatez[1] = new double[]{Math.sin(Zradians),Math.cos(Zradians),0,0};
		rotatez[2] = new double[]{0,0,1,0};
		rotatez[3] = new double[]{0,0,0,1};
		Matrix ZRot = new Matrix(rotatez);
		
		double Xradians = 50 * (Math.PI/180);
		double[][] rotateX = new double[4][];
		rotateX[0] = new double[]{Math.cos(Xradians),0,Math.sin(Xradians),0};
		rotateX[1] = new double[]{0,1,0,0};
		rotateX[2] = new double[]{Math.sin(-Xradians),0,Math.cos(Xradians),0};
		rotateX[3] = new double[]{0,0,0,1};
		Matrix XRot = new Matrix(rotateX);
		
		double[][] translate = new double[4][];
		translate[0] = new double[]{1,0,0,1};
		translate[1] = new double[]{0,1,0,0};
		translate[2] = new double[]{0,0,1,-1};
		translate[3] = new double[]{0,0,0,1};
		Matrix Translate = new Matrix(translate);
		
		Matrix Transforms = Translate.times(XRot.times(ZRot.times(ScaleAll)));
		
		double[][] modelMatrix = Transforms.getArray();
		float[] floatMatrix2 = new float[16];
		
		int count2 = 0;
		for(int i=0; i<modelMatrix.length; i++){
			for(int j=0; j<4; j++){
				floatMatrix2[count2] = (float) modelMatrix[i][j];
				//System.out.println(floatMatrix2[count2]);
				count2++;
			}
		}
		int matrixPos2 = gl2.glGetUniformLocation(program, "model");
		gl2.glUniformMatrix4fv(matrixPos2,1,true,floatMatrix2,0);
		
		double lookatx = 1.0;
		double lookaty = 0;
		double lookatz = 0;
		
		double epx = 0;
		double epy = 3.0;
		double epz = 3.0;
		
		double upx = 0;
		double upy = 1.0;
		double upz = 0;
		
		
		double nx = epx-lookatx;
		double ny = epy-lookaty;
		double nz = epz-lookatz;

		double nsize = Math.sqrt(nx*nx + ny*ny + nz*nz);
		
		nx = nx/nsize;
		ny = ny/nsize;
		nz = nz/nsize;
		
		double ux = (upy*nz) - (upz*ny);
		double uy = -(upx*nz) + (upz*nx);
		double uz = (upx*ny) - (upy*nx);
		
		double usize = Math.sqrt(ux*ux + uy*uy + uz*uz);
		
		ux = ux/usize;
		uy = uy/usize;
		uz = uz/usize;
		
		
		double vx = (ny*uz) - (nz*uy);
		double vy = -(nx*uz) + (nz*ux);
		double vz = (nx*uy) - (ny*ux);
		
		double r1 = -ux*epx + -uy*epy + -uz*epz;
		double r2 = -vx*epx + -vy*epy + -vz*epz;
		double r3 = -nx*epx + -ny*epy + -nz*epz;
		
		double[][] camera = new double[4][];
		camera[0] = new double[]{ux,uy,uz,r1};
		camera[1] = new double[]{vx,vy,vz,r2};
		camera[2] = new double[]{nx,ny,nz,r3};
		camera[3] = new double[]{0,0,0,1};
		Matrix Camera = new Matrix(camera);
		
		double[][] viewMatrix = Camera.getArray();
		float[] floatMatrix1 = new float[16];
		
		int count1 = 0;
		for(int i=0; i<viewMatrix.length; i++){
			for(int j=0; j<4; j++){
				floatMatrix1[count1] = (float) viewMatrix[i][j];
				//System.out.println(floatMatrix1[count1]);
				count1++;
			}
		}
		int matrixPos1 = gl2.glGetUniformLocation(program, "view");
		gl2.glUniformMatrix4fv(matrixPos1,1,true,floatMatrix1,0);

		double Left = -1.5;
		double Right = 1.0;
		double Top = 1.5;
		double Bottom = -1.5;
		double Near = 1.0;
		double Far = 8.5;
		
		double[][] ortho = new double[4][];
    	ortho[0] = new double[]{2/(Right-Left),0,0,-(Right+Left)/(Right-Left)};
    	ortho[1] = new double[]{0,2/(Top-Bottom),0,-(Top+Bottom)/(Top-Bottom)};
    	ortho[2] = new double[]{0,0,-2/(Far-Near),-(Far+Near)/(Far-Near)};
    	ortho[3] = new double[]{0,0,0,1};
		Matrix Ortho = new Matrix(ortho);

		double[][] projectionMatrix = Ortho.getArray();
		float[] floatMatrix = new float[16];
		
		int count = 0;
		for(int i=0; i<projectionMatrix.length; i++){
			for(int j=0; j<4; j++){
				floatMatrix[count] = (float) projectionMatrix[i][j];
				//System.out.println(floatMatrix[count]);
				count++;
			}
		}
		int matrixPos = gl2.glGetUniformLocation(program, "projection");
		gl2.glUniformMatrix4fv(matrixPos,1,true,floatMatrix,0);
		
    	
    }

	
}
