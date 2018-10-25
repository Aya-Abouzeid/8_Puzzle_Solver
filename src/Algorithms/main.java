package Algorithms;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] init = {1,2,5,3,4,0,6,7,8};
		SearchInterface d = new DFS();
		boolean b = d.search(init);
		System.out.println(b);
		System.out.println(d.nodesExpanded());
		System.out.println(d.runningTime());
		System.out.println(d.pathCost());
		System.out.println(d.searchDepth());
		System.out.println(d.pathToGoal());
	}

}
