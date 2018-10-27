package algorithms;

public class AstarState implements Comparable<AstarState> {
	private State state;
	private int g, h, cost;

	public AstarState(State state, int g, int h) {

		this.state = state;
		this.g = g;
		this.h = h;
		cost = g + h;
	}

	/***
	 * Returns the current state
	 * 
	 * @return
	 */
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	/***
	 * Compares the cost of current state with other costs in order to place it in
	 * the right place in the priority queue
	 */
	@Override
	public int compareTo(AstarState o) {
		if (o.cost < cost) {
			return 1;
		}
		return -1;
	}
}