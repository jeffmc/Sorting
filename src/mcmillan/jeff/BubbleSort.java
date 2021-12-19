package mcmillan.jeff;

public class BubbleSort extends SortMethod {

	public static final String name = "Bubble";
	
	private float progress = 0.0f;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void sort(int[] arr) {
		progress = 0.0f;
		boolean changed = true;
		while (changed) {
			changed = false;
			int correct = 0;
			for (int i=1;i<arr.length;i++) {
				if (arr[i-1]>arr[i]) {
					swap(arr,i-1,i);
					changed = true;
				} else {
					correct++;
				}
			}
			progress = (float)correct/(float)arr.length;
		}
		progress = 1.0f;
	}

	@Override
	public float progress() {
		return progress;
	}
}
