package mcmillan.jeff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class QuickSort extends SortMethod {

	public static final String name = "Quicksort";
	private float progress = 0.0f;
	
	@Override
	public String getName() { return name; }

	@Override
	public float progress() { return progress; }

	@Override
	public void sort(int[] arr) {
//		https://en.wikipedia.org/wiki/Quicksort#Lomuto_partition_scheme
		
//		Sorting the entire array is accomplished by quicksort(A, 0, length(A) - 1).

		ArrayList<Region> regions = new ArrayList<Region>();
		
		regions.add(new Region(0, arr.length-1)); // TODO: FIX THIS!

		while (regions.size() > 0) {
			Region r = regions.remove(0);
			int lo = r.lo;
			int hi = r.hi;

			if (lo >= hi || lo < 0) continue;
			
			int p = partition(arr, lo, hi);
			
			System.out.println("Pivot: " + p);
			
			regions.add(new Region(lo, p - 1));
			regions.add(new Region(p + 1, hi));
		}
		
		
		
	}
	
//		// Sorts a (portion of an) array, divides it into partitions, then sorts those
//		algorithm quicksort(A, lo, hi) is 
//		  // If indices are in correct order
//		  if lo >= hi || lo < 0 then 
//		    return
//		    
//		  // Partition array and get the pivot index
//		  p := partition(A, lo, hi) 
//		      
//		  // Sort the two partitions
//		  quicksort(A, lo, p - 1) // Left side of pivot
//		  quicksort(A, p + 1, hi) // Right side of pivot

	private int partition(int[] arr, int lo, int hi) {
//		// Divides array into two partitions
//		algorithm partition(A, lo, hi) is 
//		  pivot := A[hi] // Choose the last element as the pivot
//
//		  // Temporary pivot index
//		  i := lo - 1
//
//		  for j := lo to hi do 
//		    // If the current element is less than or equal to the pivot
//		    if A[j] <= pivot then 
//		      // Move the temporary pivot index forward
//		      i := i + 1
//
//		      // Swap the current element with the element at the temporary pivot index
//		      swap A[i] with A[j] 
//		  // Move the pivot element to the correct pivot position (between the smaller and larger elements)
//		  i := i + 1
//		  swap A[i] with A[hi]
//		  return i // the pivot index
		
		int pivot = arr[hi]; // Choose last element as pivot
		
		int i = lo - 1; // Temp pivot idx
		
		for (int j = lo; j<=hi; j++) {
			if (arr[j]<=pivot) {
				i++;
				swap(arr, i, j);
			}
		}
		i++;
		if (i>=arr.length) i = arr.length - 1; // Index cap
		swap(arr,i,hi);
		return i;
	}
	
	class Region {
		int lo, hi;
		
		Region(int l, int h) {
			lo = l;
			hi = h;
		}
	}
	
}
