package mcmillan.jeff;

// Runs the provided SortMethod on the provided array on its own thread.
public class SortThread extends Thread {

	private static final float nsInMs = 1000000.0f; // nanoseconds in a millisecond
	
	private SortMethod sortMethod; // Sorting method to be utilized.
	private int[] arr; // array to sort
	
	private long startTime = -1, endTime = -1; // System.nanoTime();
	private boolean finished = false; // false if in progress or hasn't began, true only if completed.
	
	public SortThread(SortMethod sm, int[] arr) {
		this.sortMethod = sm;
		this.arr = arr;
		startTime = -1;
		this.setName(sm.getName() + " on " + arr.length + " elements"); // Set thread name
		this.setPriority(Thread.MAX_PRIORITY); // Not sure if this does anything.
	}
	
	// Runs the sort method and records metrics
	@Override
	public void run() {
		finished = false;
		startTime = System.nanoTime();
		sortMethod.sort(arr);
		endTime = System.nanoTime();
		finished = true;
	}
	
	// Returns if thread is finished with the sort.
	public boolean finished() {
		return finished;
	}
	
	// Returns the elapsed milliseconds from start till now/finish time if finished.
	public int elapsedMs() {
		if (endTime < 0) {
			return Math.round((System.nanoTime()-startTime)/nsInMs);
		} else {
			return Math.round((endTime-startTime)/nsInMs);
		}
	}
	
	// Return progress of sort, should be 0.0-1.0
	public float progress() {
		return sortMethod.progress();
	}
	
}
