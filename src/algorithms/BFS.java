package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements SearchInterface {

	@Override
	public boolean search(int[] initialState) {
		// TODO Auto-generated method stub
		if (initialState == null || initialState.length != 9)
			return false;

		Point zeroIndex = new Point(-1, -1);

		int[][] state = new int[3][3];
		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 3; j++) {

				if (initialState[i * 3 + j] == 0)
					zeroIndex = new Point(i, j);

				state[i][j] = initialState[i * 3 + j];
			}
		}

		if (zeroIndex.x == -1)
			return false;

		State inital = new State(state, zeroIndex, null);

		Queue<State> frontier = new LinkedList<State>();
		ArrayList<State> explored = new ArrayList<State>();
		frontier.add(inital);

		return BFS(frontier, explored);
	}

	private boolean BFS(Queue<State> frontier, ArrayList<State> explored) {
		while (!frontier.isEmpty()) {
			State s = frontier.remove();
			explored.add(s);

			if (s.testGoal()) {
				return true;
			}

			for (State neighbour : s.getNeighbours()) {
				if (!isExplored(neighbour, explored) && !inFrontier(neighbour, frontier)) {
					frontier.add(neighbour);
				}
			}
		}
		return false;
	}

	private boolean isExplored(State current, ArrayList<State> explored) {
		for (State s : explored) {
			if (current.areEqual(s.getGame()))
				return true;
		}
		return false;
	}

	private boolean inFrontier(State current, Queue<State> frontier) {
		for (State s : frontier) {
			if (current.areEqual(s.getGame()))
				return true;
		}
		return false;
	}

	@Override
	public ArrayList<String> pathToGoal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int pathCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int nodesExpanded() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int searchDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long runningTime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
