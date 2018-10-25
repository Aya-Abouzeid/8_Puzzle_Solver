package Algorithms;

import java.util.ArrayList;

public interface SearchInterface {
	
	/**
	 * Runs the Algorithm.
	 */
	public boolean search(int[] initialState);

	/**
	 * @return ArrayList of strings representing the path walked to reach the goal
	 * where the string could only be one of the following values:
	 * UP , DOWN , LEFT , RIGHT.
	 */
	public ArrayList<String> pathToGoal();
	
	/**
	 * 
	 * @return cost of path walked to reach goal.
	 */
	public int pathCost();
	
	/**
	 * @return number of expanded nodes.
	 */
	public int nodesExpanded();
	
	/**
	 * @return maximum depth of tree traversal needed to reach goal.
	 */
	public int searchDepth();
	
	/**
	 * @return running time of Algorithm in milliseconds.
	 */
	public long runningTime();

}