package mcmillan.jeff;

//Implementation of Selection sort, as seen here https://en.wikipedia.org/wiki/Selection_sort
public class SelectionSort extends SortMethod {

	public static final String name = "Selection";

	private float progress = 0.0f;
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override // https://en.wikipedia.org/wiki/Selection_sort#Implementations
	public void sort(int[] arr) {
		progress = 0.0f;
	
		int i,j;
		
		// advance the position through the entire array 
		// (could do i < aLength-1 because single element is also min element) 
		for (i = 0; i < arr.length-1; i++)
		{
			progress = (float)i/(float)arr.length;
			// find the min element in the unsorted a[i .. aLength-1]

		    // assume the min is the first element 
		    int jMin = i;
		    // test against elements after i to find the smallest 
		    for (j = i+1; j < arr.length; j++)
		    {
		        // if this element is less, then it is the new minimum 
		        if (arr[j] < arr[jMin])
		        {
		            // found new minimum; remember its index 
		            jMin = j;
		        }
		    }

		    if (jMin != i) 
		    {
		        swap(arr, i, jMin);
		    }
		}
		
		progress = 1.0f;
	}

	@Override
	public float progress() {
		return progress;
	}

}
