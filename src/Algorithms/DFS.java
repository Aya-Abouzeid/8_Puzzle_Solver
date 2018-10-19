package Algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFS {
	
	
	public boolean search(State startState) {
		Stack<State> frontier = new Stack<>();
		Set<State> explored = new HashSet<>();
		
		frontier.push(startState);
		
		while(!frontier.isEmpty()) {
			State s = frontier.pop();
			explored.add(s);
			
			if(s.testGoal())
				return true;
			
			ArrayList<State> neighbours = s.getNeighbours();
			for(State neighbour : neighbours) 
				if(!inFrontier(neighbour, frontier) && !inExplored(neighbour, explored))
					frontier.push(neighbour);
				
		}
		return false;
	}

	private boolean inFrontier(State neighbour, Stack<State> frontier) {
		for(State s: frontier) 
			if(neighbour.areEqual(s.getGame()))
				return true;
		
		return false;
	}

	private boolean inExplored(State neighbour, Set<State> explored) {
		for(State s: explored) 
			if(neighbour.areEqual(s.getGame()))
				return true;
		
		return false;
	}

	
	
}
