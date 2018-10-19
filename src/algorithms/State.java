package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class State {
	private int game [][] = new int [3][3];
	private Point zeroPosition;
	private ArrayList <State> neighbours = new ArrayList <>();
	private State parent;
	public State (int [][] game , Point zeroPosition, State parent){
		this.game = game;
		this.zeroPosition = zeroPosition;
		this.parent = parent;
		
	}

	public State getParent() {
		return parent;
	}
	public void setParent(State parent) {
		this.parent = parent;
	}
	public int[][] getGame() {
		return game;
	}
	public void setGame(int[][] game) {
		this.game = game;
	}
	public Point getZeroPosition() {
		return zeroPosition;
	}
	public void setZeroPosition(Point zeroPosition) {
		this.zeroPosition = zeroPosition;
	}
	public ArrayList<State> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(ArrayList<State> neighbours) {
		this.neighbours = neighbours;
	}
	private void swap (int[][]arr, int x1, int y1, int x2, int y2){
		int temp = arr[x1][y1];
		arr[x1][y1] = arr[x2][y2];
		arr[x2][y2] = temp;
	}
	public ArrayList<State> allPossibleNeighbours(){
		
		ArrayList<State> neigh = new ArrayList<>();
		if(zeroPosition.x+1 < 3){
		System.out.println("The right X move: ");
		int[][] n = new int[game.length][];
		for (int i = 0; i < n.length; ++i) {
	         n[i] = new int[game[i].length];
	         for (int j = 0; j < n[i].length; ++j) {
	        	 
	            n[i][j] = game[i][j];
	         }
	      }
		
		swap (n,zeroPosition.x,zeroPosition.y,zeroPosition.x+1,zeroPosition.y);
		
		System.out.println("swapping : "+n[zeroPosition.x][zeroPosition.y]+" "+ game[zeroPosition.x][zeroPosition.y]);
		neigh.add(new State(n, new Point(zeroPosition.x+1,zeroPosition.y),this));
	}
	
	if(zeroPosition.x-1 >= 0){
		
		int[][] n = new int[game.length][];
		for (int i = 0; i < n.length; ++i) {
	         n[i] = new int[game[i].length];
	         for (int j = 0; j < n[i].length; ++j) {
	        	 
	            n[i][j] = game[i][j];
	         }
	      }
		swap (n,zeroPosition.x,zeroPosition.y,zeroPosition.x-1,zeroPosition.y);
		System.out.println("swapping : "+n[zeroPosition.x][zeroPosition.y]+" "+ game[zeroPosition.x][zeroPosition.y]);
		neigh.add(new State(n, new Point(zeroPosition.x-1,zeroPosition.y),this));
	
	}
	
	if(zeroPosition.y+1 < 3){
		int[][] n = new int[game.length][];
		for (int i = 0; i < n.length; ++i) {
	         n[i] = new int[game[i].length];
	         for (int j = 0; j < n[i].length; ++j) {
	        	 
	            n[i][j] = game[i][j];
	         }
	      }
		swap (n,zeroPosition.x,zeroPosition.y,zeroPosition.x,zeroPosition.y+1);
		System.out.println("swapping : "+n[zeroPosition.x][zeroPosition.y]+" "+ game[zeroPosition.x][zeroPosition.y]);
		neigh.add(new State(n, new Point(zeroPosition.x,zeroPosition.y+1),this));

	}
	
	if(zeroPosition.y-1 >= 0){
		int[][] n = new int[game.length][];
		for (int i = 0; i < n.length; ++i) {
	         n[i] = new int[game[i].length];
	         for (int j = 0; j < n[i].length; ++j) {
	        	 
	            n[i][j] = game[i][j];
	         }
	      }
		swap (n,zeroPosition.x,zeroPosition.y,zeroPosition.x,zeroPosition.y-1);
		System.out.println("swapping : "+n[zeroPosition.x][zeroPosition.y]+" "+ game[zeroPosition.x][zeroPosition.y]);
		neigh.add(new State(n, new Point(zeroPosition.x,zeroPosition.y-1),this));


	}
		return neigh;
	}
}
