package mcmillan.jeff;

public class SortThread extends Thread {

	private static final float msInNs = 1000000.0f;
	
	private SortMethod sortMethod;
	private int[] arr;
	
	private long startTime = -1, endTime = -1; // System.nanoTime();
	private boolean finished = false;
	
	public SortThread(SortMethod sm, int[] arr) {
		this.sortMethod = sm;
		this.arr = arr;
		startTime = -1;
		this.setName(sm.getName() + " Thread");
		this.setPriority(Thread.MAX_PRIORITY);
	}
	
	@Override
	public void run() {
		finished = false;
		startTime = System.nanoTime();
		sortMethod.sort(arr);
		endTime = System.nanoTime();
		finished = true;
	}
	
	public boolean finished() {
		return finished;
	}
	
	public int elapsedMs() {
		if (endTime < 0) {
			return Math.round((System.nanoTime()-startTime)/msInNs);
		} else {
			return Math.round((endTime-startTime)/msInNs);
		}
	}
	
	public float progress() {
		return sortMethod.progress();
	}
	
}
