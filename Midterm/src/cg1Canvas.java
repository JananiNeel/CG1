//
//  cg1Canvas.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * This is a simple canvas class for adding functionality for the
 * 2D portion of Computer Graphics I.
 *
 */

import java.util.*;
import Jama.*;

public class cg1Canvas extends simpleCanvas {
    
	private int m_numPolys = 0;
	private HashMap<Integer,polygon> polyMap = new HashMap<Integer,polygon>();
	private Matrix curNormalizeMatrix = null;
	private Matrix curViewMatrix = null;
	private polygon clipWindow = null;
	private float cx0 = 0;
	private float cx1 = 0;
	private float cy0 = 0;
	private float cy1 = 0;
	Rasterizer R;
	clipper C;
	
    /**
     * Constructor
     *
     * @param w width of canvas
     * @param h height of canvas
     */
    cg1Canvas (int w, int h)
    {
        super (w, h);
        R = new Rasterizer(h);
        C = new clipper();
        
    }
    
    /**
     *
     * addPoly - Adds and stores a polygon to the canvas.  Note that this method does not
     *           draw the polygon, but merely stores it for later draw.  Drawing is 
     *           initiated by the draw() method.
     *
     *           Returns a unique integer id for the polygon.
     *
     * @param x - Array of x coords of the vertices of the polygon to be added.
     * @param y - Array of y coords of the vertices of the polygin to be added.
     * @param n - Number of verticies in polygon
     *
     * @return a unique integer identifier for the polygon
     */
    public int addPoly (float x[], float y[], int n)
    {
    	float[] xs = x.clone();
    	float[] ys = y.clone();
		Matrix identityMatrix = Matrix.identity(3, 3);
        polyMap.put(m_numPolys, new polygon(xs,ys,n,identityMatrix));
        int polyID = m_numPolys;
        m_numPolys++;
        return polyID;
    }
    
    /**
     *
     * clearTransform - sets the current transformation to be the identity 
     *
     */
    public void clearTransform()
    {
    	for(int key : polyMap.keySet()){
    		polygon poly = polyMap.get(key);
    		Matrix identityMatrix = Matrix.identity(3, 3);
    		poly.setTransforms(identityMatrix);
    	}
    }
    
    /**
     *
     * draw - Draw the polygon with the given id.  Draw should draw the polygon after applying the 
     *        current transformation on the vertices of the polygon.
     *
     * @param polyID - the ID of the polygon to be drawn.
     */
    public void draw (int polyID)
    {
    	polygon poly = polyMap.get(polyID);
    	float[] xs = poly.getTransformXs();
    	float[] ys = poly.getTransformYs();
    	
    	polygon normalizedPoly = new polygon(xs, ys, poly.getNs(), curViewMatrix.times(curNormalizeMatrix));
    	float[] nxs = normalizedPoly.getTransformXs();
    	float[] nys = normalizedPoly.getTransformYs();
    	float[] outnxs = new float[50];
    	float[] outnys = new float[50];
    	
    	clipWindow.setTransforms(curViewMatrix.times(curNormalizeMatrix));
    	float[] cxs = clipWindow.getTransformXs();
    	float[] cys = clipWindow.getTransformYs();
    	
    	int numverticies = C.clipPolygon(normalizedPoly.getNs(),nxs,nys,outnxs,outnys,cxs[0],cys[0],cxs[2],cys[2]);
    	
    	int[] x = new int[numverticies];
    	for(int i=0; i<numverticies; i++){
    		x[i] = (int)outnxs[i];
    	}
    	int[] y = new int[numverticies];
    	for(int i=0; i<numverticies; i++){
    		y[i] = (int)outnys[i];
    	}
    	
    	
    	R.drawPolygon(numverticies, x, y, this);
    }
    
    /**
     *
     * rotate - Add a rotation to the current transformation by pre-multiplying the appropriate
     *          rotation matrix to the current transformation matrix.
     *
     * @param degrees - Amount of rotation in degrees.
     *
     */
    public void rotate (float degrees)
    {
    	double radians = degrees * (Math.PI/180);
    	for(int key : polyMap.keySet()){
    		polygon poly = polyMap.get(key);
    		Matrix currentTransform = poly.getTransforms();
    		double[][] translate = new double[3][];
    		translate[0] = new double[]{Math.cos(radians),Math.sin(-radians),0};
    		translate[1] = new double[]{Math.sin(radians),Math.cos(radians),0};
    		translate[2] = new double[]{0,0,1};
    		Matrix scaleMatrix = new Matrix(translate);
    		Matrix transform = scaleMatrix.times(currentTransform);
    		poly.setTransforms(transform);
    	}
    }
    
    /**
     *
     * scale - Add a scale to the current transformation by pre-multiplying the appropriate
     *          scaling matrix to the current transformation matrix.
     *
     * @param x - Amount of scaling in x.
     * @param y - Amount of scaling in y.
     *
     */
    public void scale (float x, float y)
    {
    	for(int key : polyMap.keySet()){
    		polygon poly = polyMap.get(key);
    		Matrix currentTransform = poly.getTransforms();
    		double[][] translate = new double[3][];
    		translate[0] = new double[]{(double)x,0,0};
    		translate[1] = new double[]{0,(double)y,0};
    		translate[2] = new double[]{0,0,1};
    		Matrix scaleMatrix = new Matrix(translate);
    		Matrix transform = scaleMatrix.times(currentTransform);
    		poly.setTransforms(transform);
    	}
    }
    
    /**
     *
     * setClipWindow - defines the clip window
     *
     * @param bottom - y coord of bottom edge of clip window (in world coords)
     * @param top - y coord of top edge of clip window (in world coords)
     * @param left - x coord of left edge of clip window (in world coords)
     * @param right - x coord of right edge of clip window (in world coords)
     *
     */
    public void setClipWindow (float bottom, float top, float left, float right)
    {
    		double[][] normalize = new double[3][];
    		normalize[0] = new double[]{2/(right-left),0,((-2*left)/(right-left))-1};
    		normalize[1] = new double[]{0,2/(top-bottom),((-2*bottom)/(top-bottom))-1};
    		normalize[2] = new double[]{0,0,1};
    		Matrix normalizeMatrix = new Matrix(normalize);
    		curNormalizeMatrix = normalizeMatrix;
    		
    		clipWindow = new polygon(new float[]{left,right,right,left}, new float[]{bottom,bottom,top,top}, 4, Matrix.identity(3, 3));
    		
    }
    
    
    /**
     *
     * setViewport - defines the viewport
     *
     * @param xmin - x coord of lower left of view window (in screen coords)
     * @param ymin - y coord of lower left of view window (in screen coords)
     * @param width - width of view window (in world coords)
     * @param height - width of view window (in world coords)
     *
     */
    public void setViewport (int x, int y, int width, int height)
    {
    	double[][] view = new double[3][];
    	view[0] = new double[]{(((width+x)-(x))/2),0,(((width+x)+(x))/2)};
    	view[1] = new double[]{0,(((height+y)-(y))/2),(((height+y)+(y))/2)};
    	view[2] = new double[]{0,0,1};
		Matrix viewMatrix = new Matrix(view);
		curViewMatrix = viewMatrix;
    }

    
    /**
     *
     * translate - Add a translation to the current transformation by pre-multiplying the appropriate
     *          translation matrix to the current transformation matrix.
     *
     * @param x - Amount of translation in x.
     * @param y - Amount of translation in y.
     *
     */
    public void translate (float x, float y)
    {
    	for(int key : polyMap.keySet()){
    		polygon poly = polyMap.get(key);
    		Matrix currentTransform = poly.getTransforms();
    		double[][] translate = new double[3][];
    		translate[0] = new double[]{0,0,(double)x};
    		translate[1] = new double[]{0,0,(double)y};
    		translate[2] = new double[]{0,0,0};
    		Matrix translateMatrix = new Matrix(translate);
    		Matrix transform = translateMatrix.plus(currentTransform);
    		poly.setTransforms(transform);
    	}
    }
}
