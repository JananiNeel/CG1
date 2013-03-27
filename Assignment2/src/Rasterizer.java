//
//  Rasterizer.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * 
 * This is a class that performas rasterization algorithms
 *
 */

import java.util.*;

public class Rasterizer {
    
    /**
     * number of scanlines
     */
    int n_scanlines;
    
    /**
     * Constructor
     *
     * @param n - number of scanlines
     *
     */
    Rasterizer (int n)
    {
        n_scanlines = n;
    }
    
    private HashMap<Integer, LinkedList<Integer[]>> buildEdgeTable(int n, int[] x, int[] y){
    	
    	HashMap<Integer, LinkedList<Integer[]>> edgeTable = new HashMap<Integer, LinkedList<Integer[]>>();
    	
    	for(int i=0; i<n; i++){

    		int x1 = x[i];
    		int y1 = y[i];
    		int x2 = x[i+1];
    		int y2 = y[i+1];
    		int dx = x2-x1;
    		int dy = y2-y1;
    		int ymax;
    		int ymin;
    		int xAtYmin;
    		int sum = 0;
  
    		if(y1<y2){
    			ymin = y1;
    			xAtYmin = x1;
    		}else{
    			ymin = y2;
    			xAtYmin = x2;
    		}

    		if(y1>y2){
    			ymax = y1;
    		}else{
    			ymax = y2;
    		}
    		
    		Integer[] entry = {ymax,xAtYmin,dx,dy,sum};
    		LinkedList<Integer[]> newLinkedList = edgeTable.get(ymin);
    		if(newLinkedList != null){
    			newLinkedList.add(entry);
    		}else{
    			newLinkedList = new LinkedList<Integer[]>();
    			newLinkedList.add(entry);
    		}
    		
    		edgeTable.put(ymin, newLinkedList);
    	}

    	for(Integer key : edgeTable.keySet()){
    		for(Integer[] list : edgeTable.get(key)){
    			System.out.print("[");
    			for(Integer number : list){
    				System.out.print(number+" ");
    			}
    			System.out.print("] ");
    		}
    		System.out.println();
    	}
    	return edgeTable;
    }
    
    
    /**
     * Draw a filled polygon in the simpleCanvas C.
     *
     * The polygon has n distinct vertices. The 
     * coordinates of the vertices making up the polygon are stored in the 
     * x and y arrays.  The ith vertex will have coordinate  (x[i], y[i])
     *
     * You are to add the implementation here using only calls
	 * to C.setPixel()
     */
    public void drawPolygon(int n, int x[], int y[], simpleCanvas C)
    {
    	HashMap<Integer, LinkedList<Integer[]>> edgeTable = buildEdgeTable(n,x,y);	
    }
    
}
