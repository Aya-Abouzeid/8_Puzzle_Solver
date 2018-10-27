package src.algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFS implements SearchInterface {

	private long elapsedTime;
	private long startTime;
	private long stopTime;
	private State finalState;
	private ArrayList<State> explored;

	public DFS() {
		explored = new ArrayList<>();
	}

	/**
	 * DFS algorithm
	 * */
	public boolean dfs(State startState) {
		Stack<State> frontier = new Stack<>();

		frontier.push(startState);
		
		// while there exist non explored nodes
		while (!frontier.isEmpty()) {
			State s = frontier.pop();
			explored.add(s);

			if (s.testGoal()) {
				stopTime = System.currentTimeMillis();
				elapsedTime = stopTime - startTime;
				finalState = s;
				return true;
			}

			ArrayList<State> neighbours = s.getNeighbours();
			for (State neighbour : neighbours)
				if (!inFrontier(neighbour, frontier) && !inExplored(neighbour, explored))
					frontier.push(neighbour);

		}
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		return false;
	}

	/**
	 * check if a state is exist in the frontier or not
	 * */
	private boolean inFrontier(State neighbour, Stack<State> frontier) {
		for (State s : frontier)
			if (neighbour.areEqual(s.getGame()))
				return true;

		return false;
	}

	/**
	 * check if a state is exist in the explored set or not
	 * */
	private boolean inExplored(State neighbour, ArrayList<State> explored2) {
		for (State s : explored2)
			if (neighbour.areEqual(s.getGame()))
				return true;

		return false;
	}
	
	/**
	 * search for a given initial state to the goal
	 * return false if the goal isn't reachable
	 * */
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

		State inital = new State(state, zeroIndex, null, null);
		return dfs(inital);
	}

	/**
	 * returns array list contains the path taken to reach the goal
	 * */
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
		for (int i = parents.size() - 2; i >= 0; i--) {
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
