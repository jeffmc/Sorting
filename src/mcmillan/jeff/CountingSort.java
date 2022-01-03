package mcmillan.jeff;

import java.util.Arrays;

public class CountingSort extends SortMethod {

	public static final String name = "Counting";
	private float progress = 0.0f;
	
	@Override
	public String getName() { return name; }

	@Override
	public float progress() { return progress; }

	@Override
	public void sort(int[] arr) {
//	    https://en.wikipedia.org/wiki/Counting_sort:
//		function CountingSort(input, k)
//		    
//		    count ← array of k + 1 zeros
//		    output ← array of same length as input
//		    
//		    for i = 0 to length(input) - 1 do
//		        j = key(input[i])
//		        count[j] += 1
//	
//		    for i = 1 to k do
//		        count[i] += count[i - 1]
//	
//		    for i = length(input) - 1 downto 0 do
//		        j = key(input[i])
//		        count[j] -= 1
//		        output[count[j]] = input[i]
//	
//		    return output
		
		int[] in = Arrays.copyOf(arr, arr.length);
		
		int k = -1; // Value of max integer in array
		for (int i : in)
			if (i > k) k = i;
		if (k < 0) throw new IllegalArgumentException("No positive values in array");
		
		int[] count = new int[k+1]; // Initialized to zero
//		Arrays.fill(count, 0); // Unnecessary code
		for (int i=0;i<in.length;i++)
			count[in[i]]++;
		
		int i = 0; // count idx
		int j = 0; // output arr idx
		
		while (i<count.length) {
			int x = count[i];
			Arrays.fill(arr, j, j+x, i);
			
			i++;
			j += x;
		}
			
	}


}
