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
}
