package mcmillan.jeff;

public class CountingSort extends SortMethod {

	public static final String name = "Selection";
	private float progress = 0.0f;
	
	@Override
	public String getName() { return name; }

	@Override
	public float progress() { return progress; }

	@Override
	public void sort(int[] in) {
	    // https://www.baeldung.com/java-counting-sort
		// https://en.wikipedia.org/wiki/Counting_sort
	}


}
