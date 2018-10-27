package src.algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements SearchInterface {

	private long elapsedTime;
	private long startTime;
	private long stopTime;
	private int pathCost;
	private int nodesExpanded;
	private int searchDepth;
	private ArrayList<String> path;
	private State finalState;
	private ArrayList<State> explored;

	@Override
	public boolean search(int[] initialState) {
		// TODO Auto-generated method stub
		startTime = System.currentTimeMillis();

		if (initialState == null || initialState.length != 9) {
			stopTime = System.currentTimeMillis();
			elapsedTime = stopTime - startTime;
			return false;
		}

		Point zeroIndex = new Point(-1, -1);

		int[][] state = new int[3][3];
		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 3; j++) {

				if (initialState[i * 3 + j] == 0) {
					// check if there are multiple zeros
					if (zeroIndex.x == -1)
						zeroIndex = new Point(i, j);
					else {
						stopTime = System.currentTimeMillis();
						elapsedTime = stopTime - startTime;
						return false;
					}
				}
				state[i][j] = initialState[i * 3 + j];
			}
		}
		// check if no zeros found
		if (zeroIndex.x == -1) {
			stopTime = System.currentTimeMillis();
			elapsedTime = stopTime - startTime;
			return false;
		}

		State inital = new State(state, zeroIndex, null ,null);

		Queue<State> frontier = new LinkedList<State>();
		explored = new ArrayList<State>();
		frontier.add(inital);
		return BFS(frontier, explored);
	}

	// BFS Algorithm
	// returns true if goal reached, false otherwise
	private boolean BFS(Queue<State> frontier, ArrayList<State> explored) {
		while (!frontier.isEmpty()) {
			State s = frontier.remove();
			explored.add(s);
			if (s.testGoal()) {
				stopTime = System.currentTimeMillis();
				elapsedTime = stopTime - startTime;
				finalState = s;
				return true;
			}
			for (State neighbour : s.getNeighbours()) {
				if (!isExplored(neighbour, explored) && !inFrontier(neighbour, frontier)) {
					frontier.add(neighbour);
				}
			}
		}
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		return false;
	}

	// check if this state has already been explored
	private boolean isExplored(State current, ArrayList<State> explored) {
		for (State s : explored) {
			if (current.areEqual(s.getGame()))
				return true;
		}
		return false;
	}

	// check if state exist in frontier
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
		ArrayList<State> parents = new ArrayList<State>();
		ArrayList<String> path = new ArrayList<String>();
		State s = finalState;
		while (s != null) {
			parents.add(s);
			s = s.getParent();
		}
		for(int i = parents.size()-2 ; i >= 0 ; i-- ) {
			path.add(parents.get(i).getPath());
		}
		return path;
	}

	@Override
	public int pathCost() {
		// TODO Auto-generated method stub
		int cost = -1;
		State s = finalState;
		while (s != null) {
			cost++;
			s = s.getParent();
		}
		
		return cost;
	}

	@Override
	public int nodesExpanded() {
		// TODO Auto-generated method stub
		return explored.size();
		
	}

	@Override
	public int searchDepth() {
		// TODO Auto-generated method stub
		int depth = -1;
		State s = finalState;
		while (s != null) {
			depth++;
			s = s.getParent();
		}
		
		return depth;
	}
	public State getFinalState(){
		return finalState;
	}
	@Override
	public long runningTime() {
		// TODO Auto-generated method stub
		return elapsedTime;
	}

}
