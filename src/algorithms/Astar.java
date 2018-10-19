package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Astar {
	private HashMap <Integer,Point> goalState = new HashMap<>();	
	
	public Astar(){
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
	private void swap (int[][]arr, int x1, int y1, int x2, int y2){
		int temp = arr[x1][y1];
		arr[x1][y1] = arr[x2][y2];
		arr[x2][y2] = temp;
	}
	private boolean checkNotInExplored(int[][] arr, ArrayList<AstarState> explored){
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
	private int computeNewH(int [][] arr){
		int h = 0 ;
		boolean finalState = false;
		for (int i = 0 ; i < 3 ; i++){
			for(int j = 0 ; j < 3 ; j++){
				if(arr[i][j] == 0)
					continue;
				Point rightPos = goalState.get(arr[i][j]);
				//System.out.println("value " + arr[i][j] + " i&j "+  i+" "+j +"  "+ "rights " + rightPos.x + " " +rightPos.y);
				h+= Math.abs(i-rightPos.x) + Math.abs(j-rightPos.y);
				if(i==0 && j==0 && h==0)
					finalState = true;
			}
		}
		if(finalState && h !=0)
			return -1;
		
		return h;
	}
	private boolean DecreaseKeyIfInFrontierAndNessesary(int[][] arr, int cost, PriorityQueue<AstarState> frontier){
		for(AstarState s : frontier){

			if (Arrays.toString(s.getState().getGame()[0]).equals(Arrays.toString(arr[0])) 
					&& Arrays.toString(s.getState().getGame()[1]).equals(Arrays.toString(arr[1])) 
					&& Arrays.toString(s.getState().getGame()[2]).equals(Arrays.toString(arr[2])) 
					){
				if(cost < s.getCost())
					s.setCost(cost);
				return false;
			}	
		}
		return true;
	}
	public void algorithm (State state) {
		
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
		
		ArrayList<AstarState> explored = new ArrayList<>();
		
		while(!frontier.isEmpty()){
			
			AstarState s = frontier.poll();
			System.out.println("frontier: "+Arrays.toString(s.getState().getGame()[0]));
			explored.add(s);
			if (s.getH() == 0)
				break;
			ArrayList<State> posN = s.getState().allPossibleNeighbours();

			for(State st : posN){
				boolean notExplored = checkNotInExplored(st.getGame(),explored);
				System.out.println("Boolean not Explored4  : "+notExplored);

				int h;
				if (notExplored){
					h= computeNewH(st.getGame());
					System.out.println("value of h  "+h);

					
					if(h != -1){
					boolean notInFrontier = DecreaseKeyIfInFrontierAndNessesary(st.getGame(),s.getG()+1+h,frontier);
					if(notInFrontier){
						
						s.getState().getNeighbours().add(st);
						AstarState newAstarState = new AstarState(st, s.getG()+1, h);
						frontier.add(newAstarState);

					}
					}
				}
				
			}
		}		
	}
}
