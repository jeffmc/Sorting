package mcmillan.jeff;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static final String filePath = "input2.txt";
	
	public static void main(String[] args) {
		try {
			long start,end;
			
			// Loading
			start = System.nanoTime();
			int[] arr = arrayFromCSVFile(new File(filePath));
			end = System.nanoTime();
			System.out.println("Loading took " + (end-start) / 1000000.0f + " ms.");
			System.out.println("Loaded: " + arr.length + " elements.");
			
			// Bubble
			int[] bubble = test(new BubbleSort(), arr);
			
			// Selection 
			int[] selection = test(new SelectionSort(), arr);
			System.err.println("Bubble and Selection " + (Arrays.equals(bubble, selection)?"are":"AREN'T") + " equal.");
			
			// Counting/Table
			int[] counting = test(new CountingSort(), arr);
			System.err.println("Selection and Counting " + (Arrays.equals(selection, counting)?"are":"AREN'T") + " equal.");
			
			int[] quick = test(new QuickSort(), arr);
			System.err.println("Counting and Quick" + (Arrays.equals(counting, quick)?"are":"AREN'T") + " equal.");
			
			System.in.read();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static int[] test(SortMethod sm, int[] arr) throws Exception {
		int[] newArr = arr.clone();
		SortThread thr = new SortThread(sm, newArr);
		
		thr.start();
		System.out.println(sm.getClass().getSimpleName() + " started!");
		long lastTime = 0;
		while(!thr.finished()) {
			long nowTime = System.currentTimeMillis();
			if (nowTime - lastTime > 500) {
				lastTime = System.currentTimeMillis();
				System.out.println(Math.floor(thr.progress()*1000.0f)/10.0f + "%, " + thr.elapsedMs() + "ms elapsed");
			}
		}
		System.out.println(sm.getClass().getSimpleName() + " took " + thr.elapsedMs() + "ms");
		
		if (!validateSort(newArr)) throw new Exception("Not sorted!");
		
		return newArr;
	}
	
	public static void print(int[] arr) {
		System.out.println(arr.length + " elements");
		for (int i : arr)
			System.out.print(String.valueOf(i) + ",");
		System.out.print("\n");
	}
	
	public static boolean validateSort(int[] arr) {
		for (int i=1;i<arr.length;i++) 
			if (arr[i-1] > arr[i]) return false;
		return true;
	}
	
	// Benchmark runnable and return average milliseconds.
	public static float benchmark(SortMethod sm, int iterations) throws IOException {
		long start = System.nanoTime(), end;
		int[] arr = arrayFromCSVFile(new File(filePath));
		end = System.nanoTime();
		System.out.println("Loading took " + (end-start)/1000000.0f + " ms.");
		
		for (int i : arr)
			System.out.print(arr[i] + ",");
		
		long sum = 0;
		for (int i=0;i<iterations;i++) {
			start = System.nanoTime();
			sm.sort(arr.clone());
			end = System.nanoTime();
			sum += (end-start);
		}
		return (sum/iterations)/1000000.0f;
	}
	
	// ~125ms for input4.txt on i7-6700
	public static int[] arrayFromCSVFile(File in) throws IOException {
		ArrayList<Integer> ints = new ArrayList<>();
		
		char[] cbuf = new char[512]; // BUFFER SIZE, can be changed.
		FileReader fr = new FileReader(in);
		fr.read(cbuf);
		String currString = String.valueOf(cbuf);
		
		boolean finished = false;
		while (!finished) {
			String[] segments = currString.split(",");
			boolean closes = currString.endsWith(",");
			if (closes) {
				for (int i=0;i<segments.length;i++) {
					ints.add(Integer.valueOf(segments[i]));
				}
				if (fr.read(cbuf)<0) finished = true;
				currString = String.valueOf(cbuf);
			} else {
				for (int i=0;i<segments.length-1;i++) {
					ints.add(Integer.valueOf(segments[i]));
				}
				if (fr.read(cbuf)<0) finished = true;
				String cbufStr = String.valueOf(cbuf);
				currString = segments[segments.length-1] + cbufStr;
			}
			if (currString.isBlank()) finished = true;
		}
		
		
		fr.close();
		
		int[] iArray = new int[ints.size()];
		for (int i=0;i<ints.size();i++)
			iArray[i] = ints.get(i);
		return iArray;
	}

}
