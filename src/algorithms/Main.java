package algorithms;

import java.awt.Point;

public class Main {

	public static void main(String[] args) {
		int [][] arr = {{1,2,0},{3,4,5},{6,7,8}};
		Astar a= new Astar();
		State s = new State(arr,new Point(0,2),null, null);
//		a.algorithm(s);

	}

}
