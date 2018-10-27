package algorithms;

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

	// Return Parent of State
	public State getParent() {
		return parent;
	}

	// Returns UP/LEFT/DOWN/UP according to state's parent
	public String getPath() {
		return path;
	}

	// sets state's parent
	public void setParent(State parent) {
		this.parent = parent;
	}

	// returns grid of puzzle
	public int[][] getGame() {
		return game;
	}

	// sets puzzle grid
	protected void setGame(int[][] game) {
		this.game = game;
	}

	// returns zero postition of current state
	protected Point getZeroPosition() {
		return zeroPosition;
	}

	// sets zero position of current state
	protected void setZeroPosition(Point zeroPosition) {
		this.zeroPosition = zeroPosition;
	}

	// return state's neighbours
	protected ArrayList<State> getNeighbours() {
		return allPossibleNeighbours();
	}

	// set state's neighbours
	protected void setNeighbours(ArrayList<State> neighbours) {
		this.neighbours = neighbours;
	}

	// swap 2 elements in a grid
	private void swap(int[][] arr, int x1, int y1, int x2, int y2) {
		int temp = arr[x1][y1];
		arr[x1][y1] = arr[x2][y2];
		arr[x2][y2] = temp;
	}

	// calculate state's neighbours up/left/down/right , except for it's parent
	public ArrayList<State> allPossibleNeighbours() {

		ArrayList<State> neigh = new ArrayList<>();

		// get right neighbour
		if (zeroPosition.y + 1 < 3 && !(this.parent != null && zeroPosition.y + 1 == this.parent.zeroPosition.y)) {
			int[][] n = new int[this.game.length][];
			for (int i = 0; i < n.length; ++i) {
				n[i] = new int[this.game[i].length];
				for (int j = 0; j < n[i].length; ++j) {

					n[i][j] = this.game[i][j];
				}
			}
			swap(n, zeroPosition.x, zeroPosition.y, zeroPosition.x, zeroPosition.y + 1);
			neigh.add(new State(n, new Point(zeroPosition.x, zeroPosition.y + 1), this, "Right"));

		}

		// get Upper neighbour
		if (zeroPosition.x - 1 >= 0 && !(this.parent != null && zeroPosition.x - 1 == this.parent.zeroPosition.x)) {

			int[][] n = new int[this.game.length][];
			for (int i = 0; i < n.length; ++i) {
				n[i] = new int[this.game[i].length];
				for (int j = 0; j < n[i].length; ++j) {

					n[i][j] = this.game[i][j];
				}
			}
			swap(n, zeroPosition.x, zeroPosition.y, zeroPosition.x - 1, zeroPosition.y);
			neigh.add(new State(n, new Point(zeroPosition.x - 1, zeroPosition.y), this, "Up"));

		}

		// get lower neighbour
		if (zeroPosition.x + 1 < 3 && !(this.parent != null && zeroPosition.x + 1 == this.parent.zeroPosition.x)) {
			int[][] n = new int[this.game.length][];
			for (int i = 0; i < n.length; ++i) {
				n[i] = new int[this.game[i].length];
				for (int j = 0; j < n[i].length; ++j) {

					n[i][j] = this.game[i][j];
				}
			}

			swap(n, zeroPosition.x, zeroPosition.y, zeroPosition.x + 1, zeroPosition.y);

			neigh.add(new State(n, new Point(zeroPosition.x + 1, zeroPosition.y), this, "Down"));
		}

		// get left neighbour
		if (zeroPosition.y - 1 >= 0 && !(this.parent != null && zeroPosition.y - 1 == this.parent.zeroPosition.y)) {
			int[][] n = new int[this.game.length][];
			for (int i = 0; i < n.length; ++i) {
				n[i] = new int[this.game[i].length];
				for (int j = 0; j < n[i].length; ++j) {

					n[i][j] = this.game[i][j];
				}
			}
			swap(n, zeroPosition.x, zeroPosition.y, zeroPosition.x, zeroPosition.y - 1);
			neigh.add(new State(n, new Point(zeroPosition.x, zeroPosition.y - 1), this, "Left"));

		}

		this.neighbours = neigh;
		return neigh;
	}

	// check if two States are the same
	protected boolean areEqual(int[][] arr2) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.game[i][j] != arr2[i][j])
					return false;
			}
		}
		return true;
	}

	// checks if current grid matches goal grid
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