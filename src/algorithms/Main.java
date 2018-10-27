package src.algorithms;

public class Main {

	public static void main(String[] args) {
		int [] arr = {3,1,2,6,4,5,7,8,0};
		DFS a= new DFS();
		System.out.println(a.search(arr));
		System.out.println(a.pathCost());
		System.out.println(a.runningTime());
		
	}

}
