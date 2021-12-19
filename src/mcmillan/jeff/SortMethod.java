package mcmillan.jeff;

public abstract class SortMethod {

	public abstract String getName(); // Name used in command line, single word
	public abstract void sort(int[] arr); // Sort array in-place
	
	public abstract float progress();
	
	public static void swap(int[] arr, int x, int y) {
		int t = arr[x];
		arr[x] = arr[y];
		arr[y] = t;
	}
}
