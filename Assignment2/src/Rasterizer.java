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

class EdgeComparator implements Comparator<Integer[]>{

	@Override
	public int compare(Integer[] arg0, Integer[] arg1) {
		Integer s0 = arg0[2]/arg0[3];
		Integer s1 = arg1[2]/arg1[3];
		if(arg0[1]>arg1[1]){
			return 1;
		}
		if(arg0[1]<arg1[1]){
			return -1;
		}
		if(arg0[1]==arg1[1]){
			if((s0)>(s1)){
				return 1;
			}
			if((s0)<(s1)){
				return -1;
			}
		}
		return 0;
	}
}

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
			int x2;
			int y2;
			if(i == n-1){
				x2 = x[0];
				y2 = y[0];
			}else{
				x2 = x[i+1];
				y2 = y[i+1];
			}

			int x1 = x[i];
			int y1 = y[i];
			int dx = x2-x1;
			int dy = y2-y1;
			if(dy==0){
				continue;
			}
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
			System.out.print(key + " ");
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
		LinkedList<Integer[]> ActiveEdgeList = new LinkedList<Integer[]>();
		HashMap<Integer, LinkedList<Integer[]>> edgeTable = buildEdgeTable(n,x,y);
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>();
		for(Integer key : edgeTable.keySet()){
			sortedKeys.add(key);
		}
		Collections.sort(sortedKeys);
		Integer scanLine = sortedKeys.get(0);
		int maxScanLine = 0;
		for(Integer key : edgeTable.keySet()){
			for(Integer[] edge : edgeTable.get(key)){
				if(edge[0] > maxScanLine){
					maxScanLine = edge[0];
				}
			}
		}

		LinkedList<Integer[]> toAdd = edgeTable.get(scanLine);
		for(Integer[] edge : toAdd){
			ActiveEdgeList.add(edge);
		}
		Collections.sort(ActiveEdgeList, new EdgeComparator());

		while(!ActiveEdgeList.isEmpty()){


			ArrayList<int[]> toDraw = new ArrayList<int[]>();
			int parity = 0;
			for(int i=0; i<ActiveEdgeList.size()-1; i++){    			
				int currentX = ActiveEdgeList.get(i)[1];
				int nextX = ActiveEdgeList.get(i+1)[1];
				int[] xs = {currentX,nextX};
				if(parity % 2 == 0){
					toDraw.add(xs);
				}
				parity++;
			}
			System.out.println(scanLine);
			for(int i=0; i<toDraw.size(); i++){
				for(int xi=toDraw.get(i)[0]; xi<toDraw.get(i)[1]; xi++){
					C.setPixel(xi,scanLine);
				}
			}
			scanLine++;

			ArrayList<Integer[]> toRemove = new ArrayList<Integer[]>();
			for(Integer[] activeEdge : ActiveEdgeList){
				if(activeEdge[0].equals(scanLine)){
					toRemove.add(activeEdge);
				}
			}
			for(Integer[] edge : toRemove){
				ActiveEdgeList.remove(edge);
			}

			for(Integer[] edge : ActiveEdgeList){
				if(edge[2] !=0){
					edge[4] += Math.abs(edge[2]);
					while(edge[4] >= Math.abs(edge[3])){
						if((edge[2] > 0 && edge[3] > 0) || (edge[2] < 0 && edge[3] < 0)){
							edge[1] += 1;
						}
						else{
							edge[1] -= 1;
						}
						edge[4] -= Math.abs(edge[3]);
					}
				}
			}

			if(edgeTable.containsKey(scanLine)){
				LinkedList<Integer[]> add = edgeTable.get(scanLine);
				for(Integer[] edge : add){
					ActiveEdgeList.add(edge);
				}
				edgeTable.remove(scanLine);
			}

			Collections.sort(ActiveEdgeList, new EdgeComparator());

		}

	}

}
