package mcmillan.jeff;

// All sort algorithms derive from this class, can be called using the 
public abstract class SortMethod {

	public abstract String getName(); // Name used in command line, single word
	public abstract void sort(int[] arr); // Sort array in-place
	
	public abstract float progress(); // Return progress as a %, should be 0.0-1.0
	
	// Swap values at indexes within the provided array, used in most sorting methods.
	public static final void swap(int[] arr, int x, int y) {
		int t = arr[x];
		arr[x] = arr[y];
		arr[y] = t;
	}
}
