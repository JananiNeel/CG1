/**
 * cg1Shape.java
 *
 * Class that includes routines for tessellating a number of basic shapes
 *
 * Students are to supply their implementations for the
 * functions in this file using the function "addTriangle()" to do the 
 * tessellation.
 *
 */

import java.awt.*;
import java.nio.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.io.*;


public class cg1Shape extends simpleShape
{
	/**
	 * constructor
	 */
	public cg1Shape()
	{
	}

	private void drawFace(float step, int side){
		float xstart,ystart,zstart;
		if(side==0){
			xstart=-0.5f;
			ystart=-0.5f;
			zstart=0.5f;
			for(float i=ystart; i<0.5; i+=step){
				for(float j=xstart; j<0.5f; j+=step){
					float x0 = j;
					float y0 = i;
					float x1 = j+step;
					float y1 = i;
					float x2 = j+step;
					float y2 = i+step;

					float x02 = j;
					float y02 = i;
					float x12 = j+step;
					float y12 = i+step;
					float x22 = j;
					float y22 = i+step;

					addTriangle(x0,y0,zstart,x1,y1,zstart,x2,y2,zstart);
					addTriangle(x02,y02,zstart,x12,y12,zstart,x22,y22,zstart);
				}
			}
		}
		if(side==1){
			xstart=-0.5f;
			ystart=0.5f;
			zstart=0.5f;
			for(float i=zstart; i>-0.5f; i-=step){
				for(float j=xstart; j<0.5f; j+=step){
					float x0 = j;
					float y0 = ystart;
					float z0 = i;

					float x1 = j+step;
					float y1 = ystart;
					float z1 = i;

					float x2 = j+step;
					float y2 = ystart;
					float z2 = i-step;

					float x02 = j;
					float y02 = ystart;
					float z02 = i;

					float x12 = j+step;
					float y12 = ystart;
					float z12 = i-step;

					float x22 = j;
					float y22 = ystart;
					float z22 = i-step;

					addTriangle(x0,y0,z0,x1,y1,z1,x2,y2,z2);
					addTriangle(x02,y02,z02,x12,y12,z12,x22,y22,z22);
				}
			}
		}
		if(side==2){
			xstart=-0.5f;
			ystart=0.5f;
			zstart=-0.5f;
			for(float i=ystart; i>-0.5f; i-=step){
				for(float j=xstart; j<0.5f; j+=step){
					float x0 = j;
					float y0 = i;
					float x1 = j+step;
					float y1 = i;
					float x2 = j+step;
					float y2 = i-step;

					float x02 = j;
					float y02 = i;
					float x12 = j+step;
					float y12 = i-step;
					float x22 = j;
					float y22 = i-step;

					addTriangle(x0,y0,zstart,x1,y1,zstart,x2,y2,zstart);
					addTriangle(x02,y02,zstart,x12,y12,zstart,x22,y22,zstart);
				}
			}
		}
		if(side==3){
			xstart=-0.5f;
			ystart=-0.5f;
			zstart=-0.5f;
			for(float i=zstart; i<0.5f; i+=step){
				for(float j=xstart; j<0.5f; j+=step){
					float x0 = j;
					float y0 = ystart;
					float z0 = i;

					float x1 = j+step;
					float y1 = ystart;
					float z1 = i;

					float x2 = j+step;
					float y2 = ystart;
					float z2 = i+step;

					float x02 = j;
					float y02 = ystart;
					float z02 = i;

					float x12 = j+step;
					float y12 = ystart;
					float z12 = i+step;

					float x22 = j;
					float y22 = ystart;
					float z22 = i+step;

					addTriangle(x0,y0,z0,x1,y1,z1,x2,y2,z2);
					addTriangle(x02,y02,z02,x12,y12,z12,x22,y22,z22);
				}
			}
		}
		if(side==4){
			xstart=-0.5f;
			ystart=-0.5f;
			zstart=-0.5f;
			for(float i=zstart; i<0.5f; i+=step){
				for(float j=ystart; j<0.5f; j+=step){
					float x0 = xstart;
					float y0 = j;
					float z0 = i;

					float x1 = xstart;
					float y1 = j;
					float z1 = i+step;

					float x2 = xstart;
					float y2 = j+step;
					float z2 = i+step;

					float x02 = xstart;
					float y02 = j;
					float z02 = i;

					float x12 = xstart;
					float y12 = j+step;
					float z12 = i+step;

					float x22 = xstart;
					float y22 = j+step;
					float z22 = i;

					addTriangle(x0,y0,z0,x1,y1,z1,x2,y2,z2);
					addTriangle(x02,y02,z02,x12,y12,z12,x22,y22,z22);
				}
			}
		}
		if(side==5){
			xstart=0.5f;
			ystart=-0.5f;
			zstart=0.5f;
			for(float i=zstart; i>-0.5f; i-=step){
				for(float j=ystart; j<0.5f; j+=step){
					float x0 = xstart;
					float y0 = j;
					float z0 = i;

					float x1 = xstart;
					float y1 = j;
					float z1 = i-step;

					float x2 = xstart;
					float y2 = j+step;
					float z2 = i-step;

					float x02 = xstart;
					float y02 = j;
					float z02 = i;

					float x12 = xstart;
					float y12 = j+step;
					float z12 = i-step;

					float x22 = xstart;
					float y22 = j+step;
					float z22 = i;

					addTriangle(x0,y0,z0,x1,y1,z1,x2,y2,z2);
					addTriangle(x02,y02,z02,x12,y12,z12,x22,y22,z22);
				}
			}
		}


	}

	/**
	 * makeCube - Create a unit cube, centered at the origin, with a given number
	 * of subdivisions in each direction on each face.
	 *
	 * @param subdivision - number of equal subdivisons to be made in each 
	 *        direction along each face
	 *
	 * Can only use calls to addTriangle()
	 */
	public void makeCube (int subdivisions)
	{
		float step = (float)1/subdivisions;
		drawFace(step,0);
		drawFace(step,1);
		drawFace(step,2);
		drawFace(step,3);
		drawFace(step,4);
		drawFace(step,5);
	}

	/**
	 * makeCylinder - Create polygons for a cylinder with unit height, centered at
	 * the origin, with separate number of radial subdivisions and height 
	 * subdivisions.
	 *
	 * @param radius - Radius of the base of the cylinder
	 * @param radialDivision - number of subdivisions on the radial base
	 * @param heightDivisions - number of subdivisions along the height
	 *
	 * Can only use calls to addTriangle()
	 */
	public void makeCylinder (float radius, int radialDivisions, int heightDivisions)
	{
		float angle = 360/radialDivisions;
		ArrayList<Float> circlePoints = new ArrayList<Float>();
		for(float i=0; i<360; i+=angle){
			circlePoints.add(i);
		}

		float step = (float)1/heightDivisions;
		for(int i=0; i<=circlePoints.size()-1; i++){
			float xi = radius * (float)Math.cos(Math.toRadians(circlePoints.get(i)));
			float zi = radius * (float)Math.sin(Math.toRadians(circlePoints.get(i)));

			if(i == circlePoints.size()-1){
				float x0 = radius * (float)Math.cos(Math.toRadians(circlePoints.get(0)));
				float z0 = radius * (float)Math.sin(Math.toRadians(circlePoints.get(0)));

				addTriangle(0,0.5f,0,
						xi,0.5f,zi,
						x0,0.5f,z0);

				addTriangle(0,-0.5f,0,
						x0,-0.5f,z0,
						xi,-0.5f,zi);
				for(float h = 0; h<1; h+=step){
					addTriangle(xi,(float)0.5-h,zi,
							xi,(float)0.5-h-step,zi,
							x0,(float)0.5-h,z0);

					addTriangle(xi,(float)0.5-h-step,zi,
							x0,(float)0.5-h-step,z0,
							x0,(float)0.5-h,z0);
				}
			}else{
				float nextX = radius * (float)Math.cos(Math.toRadians(circlePoints.get(i+1)));
				float nextZ = radius * (float)Math.sin(Math.toRadians(circlePoints.get(i+1)));

				addTriangle(0,0.5f,0,
						xi,0.5f,zi,
						nextX,0.5f,nextZ);

				addTriangle(0,-0.5f,0,
						nextX,-0.5f,nextZ,
						xi,-0.5f,zi);
				for(float h = 0; h<1; h+=step){
					addTriangle(xi,(float)0.5-h,zi,
							xi,(float)0.5-h-step,zi,
							nextX,(float)0.5-h,nextZ);

					addTriangle(xi,(float)0.5-h-step,zi,
							nextX,(float)0.5-h-step,nextZ,
							nextX,(float)0.5-h,nextZ);
				}
			}
		}
	}

	/**
	 * makeCone - Create polygons for a cone with unit height, centered at the
	 * origin, with separate number of radial subdivisions and height 
	 * subdivisions.
	 *
	 * @param radius - Radius of the base of the cone
	 * @param radialDivision - number of subdivisions on the radial base
	 * @param heightDivisions - number of subdivisions along the height
	 *
	 * Can only use calls to addTriangle()
	 */
	public void makeCone (float radius, int radialDivisions, int heightDivisions)
	{
		float angle = (float)360/radialDivisions;

		ArrayList<Float> circlePoints = new ArrayList<Float>();
		for(float i=0; i<360; i+=angle){
			circlePoints.add(i);
		}

		float step = (float)1/heightDivisions;
		float radiusStep = (float)radius/heightDivisions;

		for(int i=0; i<=circlePoints.size()-1; i++){
			float rx = 0;
			float xi = (float)Math.cos(Math.toRadians(circlePoints.get(i)));
			float zi = (float)Math.sin(Math.toRadians(circlePoints.get(i)));

			if(i == circlePoints.size()-1){
				float x0 = (float)Math.cos(Math.toRadians(circlePoints.get(0)));
				float z0 = (float)Math.sin(Math.toRadians(circlePoints.get(0)));

				addTriangle(0,-0.5f,0,
						radius*x0,-0.5f,radius*z0,
						radius*xi,-0.5f,radius*zi);

				for(float h = 0; h<1; h+=step){
					if(rx == 0){
						addTriangle(0,0.5f,0,
								(rx+radiusStep)*xi,0.5f-step,(rx+radiusStep)*zi,
								(rx+radiusStep)*x0,0.5f-step,(rx+radiusStep)*z0);
					}else{
						addTriangle(rx*xi,(float)0.5-h,rx*zi,
								(rx+radiusStep)*xi,(float)0.5-h-step,(rx+radiusStep)*zi,
								rx*x0,(float)0.5-h,rx*z0);

						addTriangle((rx+radiusStep)*xi,(float)0.5-h-step,(rx+radiusStep)*zi,
								(rx+radiusStep)*x0,(float)0.5-h-step,(rx+radiusStep)*z0,
								rx*x0,(float)0.5-h,rx*z0);
					}
					rx += radiusStep;
				}

			}else{
				float nextX = (float)Math.cos(Math.toRadians(circlePoints.get(i+1)));
				float nextZ = (float)Math.sin(Math.toRadians(circlePoints.get(i+1)));

				addTriangle(0,-0.5f,0,
						radius*nextX,-0.5f,radius*nextZ,
						radius*xi,-0.5f,radius*zi);

				for(float h = 0; h<1; h+=step){
					if(rx == 0){
						addTriangle(0,0.5f,0,
								(rx+radiusStep)*xi,0.5f-step,(rx+radiusStep)*zi,
								(rx+radiusStep)*nextX,0.5f-step,(rx+radiusStep)*nextZ);
					}else{
						addTriangle(rx*xi,(float)0.5-h,rx*zi,
								(rx+radiusStep)*xi,(float)0.5-h-step,(rx+radiusStep)*zi,
								rx*nextX,(float)0.5-h,rx*nextZ);

						addTriangle((rx+radiusStep)*xi,(float)0.5-h-step,(rx+radiusStep)*zi,
								(rx+radiusStep)*nextX,(float)0.5-h-step,(rx+radiusStep)*nextZ,
								rx*nextX,(float)0.5-h,rx*nextZ);
					}
					rx += radiusStep;
				}

			}
			
		}
	}


	/**
	 * makeSphere - Create sphere of a given radius, centered at the origin, 
	 * using spherical coordinates with separate number of thetha and 
	 * phi subdivisions.
	 *
	 * @param radius - Radius of the sphere
	 * @param slides - number of subdivisions in the theta direction
	 * @param stacks - Number of subdivisions in the phi direction.
	 *
	 * Can only use calls to addTriangle
	 */
	public void makeSphere (float radius, int slices, int stacks)
	{
	}
}
