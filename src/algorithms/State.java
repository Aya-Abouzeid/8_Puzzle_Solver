package src.algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class State {
	private int game[][] = new int[3][3];
	private Point zeroPosition;
	private ArrayList<State> neighbours = new ArrayList<>();
	private State parent;
	private String path;

	public State(int[][] game, Point zeroPosition, State parent, String path) {
		this.game = game;
		this.zeroPosition = zeroPosition;
		this.parent = parent;
		this.path = path;
	}

	public State getParent() {
		return parent;
	}
	
	public String getPath() {
		return path;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public int[][] getGame() {
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
		return allPossibleNeighbours();
	}

	protected void setNeighbours(ArrayList<State> neighbours) {
		this.neighbours = neighbours;
	}

	private void swap(int[][] arr, int x1, int y1, int x2, int y2) {
		int temp = arr[x1][y1];
		arr[x1][y1] = arr[x2][y2];
		arr[x2][y2] = temp;
	}

	public ArrayList<State> allPossibleNeighbours() {

		ArrayList<State> neigh = new ArrayList<>();

		if (zeroPosition.y + 1 < 3 &&  !( this.parent != null && zeroPosition.y + 1 == this.parent.zeroPosition.y )) {
			int[][] n = new int[this.game.length][];
			for (int i = 0; i < n.length; ++i) {
				n[i] = new int[this.game[i].length];
				for (int j = 0; j < n[i].length; ++j) {

					n[i][j] = this.game[i][j];
				}
			}
			swap(n, zeroPosition.x, zeroPosition.y, zeroPosition.x, zeroPosition.y + 1);
			neigh.add(new State(n, new Point(zeroPosition.x, zeroPosition.y + 1), this ,"Right"));

		}
		
		if (zeroPosition.x - 1 >= 0 && !( this.parent != null && zeroPosition.x - 1 == this.parent.zeroPosition.x)) {

			int[][] n = new int[this.game.length][];
			for (int i = 0; i < n.length; ++i) {
				n[i] = new int[this.game[i].length];
				for (int j = 0; j < n[i].length; ++j) {

					n[i][j] = this.game[i][j];
				}
			}
			swap(n, zeroPosition.x, zeroPosition.y, zeroPosition.x - 1, zeroPosition.y);
			neigh.add(new State(n, new Point(zeroPosition.x - 1, zeroPosition.y), this , "Up"));

		}
		
		if (zeroPosition.x + 1 < 3 && !( this.parent != null && zeroPosition.x + 1 == this.parent.zeroPosition.x)) {
			int[][] n = new int[this.game.length][];
			for (int i = 0; i < n.length; ++i) {
				n[i] = new int[this.game[i].length];
				for (int j = 0; j < n[i].length; ++j) {
					
					n[i][j] = this.game[i][j];
				}
			}
			
			swap(n, zeroPosition.x, zeroPosition.y, zeroPosition.x + 1, zeroPosition.y);
			
			neigh.add(new State(n, new Point(zeroPosition.x + 1, zeroPosition.y), this , "Down"));
		}


		

		if (zeroPosition.y - 1 >= 0 &&  !( this.parent != null && zeroPosition.y - 1 == this.parent.zeroPosition.y )) {
			int[][] n = new int[this.game.length][];
			for (int i = 0; i < n.length; ++i) {
				n[i] = new int[this.game[i].length];
				for (int j = 0; j < n[i].length; ++j) {

					n[i][j] = this.game[i][j];
				}
			}
			swap(n, zeroPosition.x, zeroPosition.y, zeroPosition.x, zeroPosition.y - 1);
			neigh.add(new State(n, new Point(zeroPosition.x, zeroPosition.y - 1), this , "Left"));

		}

		this.neighbours = neigh;
		return neigh;
	}

	protected boolean areEqual(int[][] arr2) {
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