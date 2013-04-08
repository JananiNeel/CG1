import java.util.ArrayList;
import java.util.Collections;

//
//  Clipper.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * Object for performing clipping
 *
 */

public class clipper {
    
	public ArrayList<float[]> buildEdgeList(int n, float[] inx, float[] iny){
		ArrayList<float[]> edgeList = new ArrayList<float[]>();
		
		for(int i=0; i<n; i++){
			float x1;
			float y1;
			float x2;
			float y2;
			if(i == n-1){
				x2 = inx[0];
				y2 = iny[0];
			}else{
				x2 = inx[i+1];
				y2 = iny[i+1];
			}
			x1 = inx[i];
			y1 = iny[i];
			
			float[] verticies = {x1,y1,x2,y2};
			edgeList.add(verticies);
		}
		
		return edgeList;
			
	}
	
	public ArrayList<float[]> buildVertexList(float[] inx, float[] iny){
		ArrayList<float[]> vertexList = new ArrayList<float[]>();
		
		for(int i=0; i<inx.length; i++){
			float[] verticies = {inx[i],iny[i]};
			vertexList.add(verticies);
		}
		
		return vertexList;
			
	}
	
	public boolean inside(float[] vertex, float[] edge, int count){
		float x = vertex[0];
		float y = vertex[1];
		boolean value = false;
		if(count == 0){
			float cy = edge[1];
			if(y >= cy){
				value = true;
			}
		}
		if(count == 1){
			float cx = edge[0];
			if(x <= cx){
				value = true;
			}
		}
		if(count == 2){
			float cy = edge[1];
			if(y <= cy){
				value = true;
			}
		}
		if(count == 3){
			float cx = edge[0];
			if(x >= cx){
				value = true;
			}
		}
		return value;
	}
	
	 public float[] intersection(int count, float[] vertex1, float[] vertex2, float[] edge) {
	        float x1,y1, x2,y2, x3,y3, x4,y4;
	        x1 = vertex1[0];
	        y1 = vertex1[1];
	        x2 = vertex2[0];
	        y2 = vertex2[1];
	        x3 = edge[0];
	        y3 = edge[1];
	        x4 = edge[2];
	        y4 = edge[3];
	        if(count==0){
	        	x3-=x1;
	        	x4+=x2;
	        }if(count==1){
	        	y3-=y1;
	        	y4+=y2;
	        }if(count==2){
	        	x3+=x1;
	        	x4-=x2;
	        }if(count==3){
	        	y3+=y1;
	        	y4-=y2;
	        }
	        float x = (
	                (x2 - x1)*(x3*y4 - x4*y3) - (x4 - x3)*(x1*y2 - x2*y1)
	                ) /
	                (
	                (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
	                );
	        float y = (
	                (y3 - y4)*(x1*y2 - x2*y1) - (y1 - y2)*(x3*y4 - x4*y3)
	                ) /
	                (
	                (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
	                );
	        
	        float[] intersect = {x,y};
	        return intersect;
	 }
    /**
     * clipPolygon
     * 
     * Clip the polygon with vertex count in and vertices inx/iny
     * against the rectangular clipping region specified by lower-left corner
     * (x0,y0) and upper-right corner (x1,y1). The resulting vertices are
     * placed in outx/outy.  
     * 
     * The routine should return the with the vertex count of polygon
     * resulting from the clipping.
     *
     * @param in the number of vertices in the polygon to be clipped
     * @param inx - x coords of vertices of polygon to be clipped.
     * @param int - y coords of vertices of polygon to be clipped.
     * @param outx - x coords of vertices of polygon resulting after clipping.
     * @param outy - y coords of vertices of polygon resulting after clipping.
     * @param x0 - x coord of lower left of clipping rectangle.
     * @param y0 - y coord of lower left of clipping rectangle.
     * @param x1 - x coord of upper right of clipping rectangle.
     * @param y1 - y coord of upper right of clipping rectangle.
     *
     * @return number of vertices in the polygon resulting after clipping
     * 
     */
    public int clipPolygon(int in, float inx[], float iny[], float outx[], 
                    float outy[], float x0, float y0, float x1, float y1)
    {
    	float[] cx = {x0,x1,x1,x0};
    	float[] cy = {y0,y0,y1,y1};
    	
    	ArrayList<float[]> outputList = buildVertexList(inx,iny);
    	ArrayList<float[]> clipEdgeList = buildEdgeList(4,cx,cy);
    	
    	int count = 0;
    	for(float[] edge : clipEdgeList){
    		ArrayList<float[]> inputList = new ArrayList<float[]>(outputList);
    		outputList.clear();
    		if(inputList.size() == 0){
    			return 0;
    		}
    		float[] s = inputList.get(inputList.size()-1);
    		for(float[] e : inputList){
    			if(inside(e,edge,count)){
    				if(!inside(s,edge,count)){
    					outputList.add(intersection(count,s,e,edge));
    				}
    				outputList.add(e);
    			}else if(inside(s,edge,count)){
    				outputList.add(intersection(count,s,e,edge));
    			}
    			s = e;
    		}
    		count++;
    	}
    	for(int i=0; i<outputList.size(); i++){
    		outx[i]=outputList.get(i)[0];
    		outy[i]=outputList.get(i)[1];
    	}
    	return outputList.size(); // should return number of vertices in clipped poly.
    }
}
