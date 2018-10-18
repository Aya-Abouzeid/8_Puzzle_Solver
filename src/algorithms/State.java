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
	protected int[][] getGame() {
		return game;
	}
	protected void setGame(int[][] game) {
		this.game = game;
	}
	protected Point getZeroPosition() {
		return zeroPosition;
	}
	protected void setZeroPosition(Point zeroPosition) {
		this.zeroPosition = zeroPosition;
	}
	protected ArrayList<State> getNeighbours() {
		return neighbours;
	}
	protected void setNeighbours(ArrayList<State> neighbours) {
		this.neighbours = neighbours;
	}
	
	protected boolean areEqual( int[][] arr2) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.game[i][j] != arr2[i][j])
					return false;
			}
		}
		return true;
	}

	protected boolean testGoal() {
		int index = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.game[i][j] != index)
					return false;
				index++;
			}
		}
		return true;
	}
}