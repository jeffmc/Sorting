package mcmillan.jeff;

// Implementation of Bubble sort method, as seen here: https://en.wikipedia.org/wiki/Bubble_sort
public class BubbleSort extends SortMethod {

	public static final String name = "Bubble";
	
	private float progress = 0.0f;
	
	@Override
	public String getName() {
		return name;
	}

	@Override // Pseduocode from https://en.wikipedia.org/wiki/Bubble_sort used to implement this.
	public void sort(int[] arr) {
		progress = 0.0f;
		boolean changed = true;
		while (changed) {
			changed = false;
			int correct = 0;
			for (int i=1;i<arr.length;i++) { // Iterate over entire array, swapping values where lower idx's value is higher than higher idx's value.
				if (arr[i-1]>arr[i]) {
					swap(arr,i-1,i);
					changed = true; // Indicate that the array has changed this iteration
				} else {
					correct++;
				}
			}
			progress = (float)correct/(float)arr.length; // Update progress value, limiting division.
		}
		progress = 1.0f; // Finished!
	}

	@Override
	public float progress() {
		return progress;
	}
}
