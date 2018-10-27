package src.algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Astar implements SearchInterface {
	private HashMap <Integer,Point> goalState = new HashMap<>();	
	private long elapsedTime;
	private long startTime;
	private long stopTime;
	private AstarState finalState;
	private ArrayList<AstarState> explored;
	private boolean euc;
	
	public Astar(boolean euc){
		this.euc = euc;
		// Saving Goal state in order to use it to compute the H(n)
		goalState.put(0, new Point(0,0));
		goalState.put(1, new Point(0,1));
		goalState.put(2, new Point(0,2));
		goalState.put(3, new Point(1,0));
		goalState.put(4, new Point(1,1));
		goalState.put(5, new Point(1,2));
		goalState.put(6, new Point(2,0));
		goalState.put(7, new Point(2,1));
		goalState.put(8, new Point(2,2));

		
	}
	/***
	 * Check if any of the State neighbours were explored before or not
	 * @param arr
	 * @return
	 */
	private boolean checkNotInExplored(int[][] arr){
		for(AstarState s : explored){
			if (Arrays.toString(s.getState().getGame()[0]).equals(Arrays.toString(arr[0])) 
					&& Arrays.toString(s.getState().getGame()[1]).equals(Arrays.toString(arr[1])) 
					&& Arrays.toString(s.getState().getGame()[2]).equals(Arrays.toString(arr[2])) 
					){
				return false;
			}
		}
		return true;
	}
	/***
	 * Computing H(n) using Manhattan heuristic or Euclidian heuristic using euc boolean
	 * @param arr
	 * @return
	 */
	private int computeNewH(int [][] arr){
		int h = 0 ;
		
		for (int i = 0 ; i < 3 ; i++){
			for(int j = 0 ; j < 3 ; j++){
				if(arr[i][j] == 0)
					continue;
				Point rightPos = goalState.get(arr[i][j]);
				if(!euc)
				h+= Math.abs(i-rightPos.x) + Math.abs(j-rightPos.y);
				else{
				h+= Math.sqrt(Math.pow((i-rightPos.x),2) + Math.pow(j-rightPos.y,2));
				}
			}
		}
		return h;
	}
	/***
	 *  It searchs for the current neighbour in the frontier List.
	 *  if it was present in frontier and it's cost > new cost then decrease it's key and change it's parent.
	 * @param arr
	 * @param cost
	 * @param frontier
	 * @return
	 */
	private boolean DecreaseKeyIfInFrontierAndNessesary(int[][] arr, int cost, PriorityQueue<AstarState> frontier){
		for(AstarState s : frontier){

			if (Arrays.toString(s.getState().getGame()[0]).equals(Arrays.toString(arr[0])) 
					&& Arrays.toString(s.getState().getGame()[1]).equals(Arrays.toString(arr[1])) 
					&& Arrays.toString(s.getState().getGame()[2]).equals(Arrays.toString(arr[2])) 
					){
				if(cost < s.getCost())
					s.setCost(cost);
					s.getState().setParent(explored.get(explored.size()-1).getState());
				return false;
			}	
		}
		return true;
	}
	
	public boolean astarAlgo(State state) {
		
		AstarState initialState = new AstarState(state, 0, 0);
		PriorityQueue<AstarState> frontier = new PriorityQueue<>(new Comparator <AstarState>() {

			@Override
			public int compare(AstarState arg0, AstarState arg1) {
				if(arg0.getCost() < arg1.getCost())
				return -1;
				return 1;
			}
		});
		initialState.setH(computeNewH(initialState.getState().getGame()));
		frontier.add(initialState);
				
		while(!frontier.isEmpty()){
			
			AstarState s = frontier.poll();
			explored.add(s);
			
			if (s.getH() == 0){
				stopTime = System.currentTimeMillis();
				elapsedTime = stopTime - startTime;
				finalState = s;
				return true;
			}
			ArrayList<State> posN = s.getState().allPossibleNeighbours();

			for(State st : posN){
				boolean notExplored = checkNotInExplored(st.getGame());

				int h;
				if (notExplored){
					h= computeNewH(st.getGame());
						boolean notInFrontier = DecreaseKeyIfInFrontierAndNessesary(st.getGame(),s.getG()+1+h,frontier);
						if(notInFrontier){
							
							s.getState().getNeighbours().add(st);
							AstarState newAstarState = new AstarState(st, s.getG()+1, h);
							frontier.add(newAstarState);
					}
				}
				
			}
		}	
		
		return false;
	}
	@Override
	public boolean search(int[] initialState) {
		// TODO Auto-generated method stub
		startTime = System.currentTimeMillis();
		explored = new ArrayList<AstarState>();
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
		return astarAlgo(inital);
	}
	@Override
	public ArrayList<String> pathToGoal() {
		ArrayList<State> parents = new ArrayList<State>();
		ArrayList<String> path = new ArrayList<String>();
		State s = finalState.getState();
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
		return finalState.getCost();
	}
	@Override
	public int nodesExpanded() {
		return explored.size();
	}
	@Override
	public int searchDepth() {
		int depth = -1;
		State s = finalState.getState();
		while (s != null) {
			depth++;
			s = s.getParent();
		}

		return depth;
	}
	public AstarState getFinalState(){
		return finalState;
	}
	@Override
	public long runningTime() {
		// TODO Auto-generated method stub
		return elapsedTime;
	}
}
