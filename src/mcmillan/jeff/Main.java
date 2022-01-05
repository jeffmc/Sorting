package mcmillan.jeff;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static Scanner console = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			long start,end;
			
			// Loading
			
			File chosenFile = chooseFile();
			
			start = System.nanoTime();
			int[] inArr = arrayFromCSVFile(chosenFile);
			
			end = System.nanoTime();
			System.out.println("Loading took " + (end-start) / 1000000 + " ms.");
			System.out.println("Loaded " + inArr.length + " integers.");

			SortMethod chosenMethod = chooseSortMethod();
			
			console.close();
			console = null;
			
			SortResults results = runMonitoredSort(chosenMethod, inArr);
			int[] outArr = results.arr;
			
			String header = chosenMethod.getName() + ", took " + results.elapsedMs + "ms, sorted " + outArr.length + " integers."; 
			
			System.out.println("\n" + header + "\n");
			
			File out = new File("sortedOutput.txt");
			if (writeArrayToCSV(outArr, out, header)) {
				System.out.println("Wrote to '" + out.getName() +"'!");
			} else {
				System.err.println("Didn't write to file!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Choose an input file from user console input.
	public static File chooseFile() {
		File f = null;
		while (f == null) {
			try {
				System.out.println("Pick an input file, the higher the number, the more integers to sort: 1, 2, 3, 4");
				String in = console.nextLine();
				int pick = Integer.parseInt(in);
				if (pick > 0 && pick < 5) {
					f = new File("input" + pick + ".txt");
				}
			} catch (Exception e) {
				System.err.println("Error!");
			}
		}
		return f;
	}
	
	// Choose a sort method from user console input.
	public static SortMethod chooseSortMethod() {
		SortMethod[] methods = new SortMethod[] {
				new BubbleSort(),
				new SelectionSort(),
				new CountingSort(),
				new QuickSort()
		};
		SortMethod m = null;
		while (m == null) {
			try {
				System.out.println("Pick a sort method:");
				for (int i=0;i<methods.length;i++)
					System.out.println("  (" + (i+1) + ") " + methods[i].getName());
				String in = console.nextLine();
				int pick = Integer.parseInt(in);
				if (pick > 0 && pick <= methods.length) {
					m = methods[pick-1];
				}
			} catch (Exception e) {
				System.err.println("Error!");
			}
		}
		return m;
	}
	
	
	// Copies array passed in, performs sort, monitors and outputs progress to console, and returns a new sorted array w/ sort metrics.
	public static SortResults runMonitoredSort(SortMethod sm, int[] arr) throws Exception {
		int[] cloneArr = arr.clone();
		SortThread thr = new SortThread(sm, cloneArr);
		
		thr.start();
		System.out.println(sm.getClass().getSimpleName() + " started!");
		long lastTime = System.currentTimeMillis();
		while(!thr.finished()) {
			long nowTime = System.currentTimeMillis();
			if (nowTime - lastTime > 500) {
				lastTime = System.currentTimeMillis();
				System.out.println(Math.floor(thr.progress()*1000.0f)/10.0f + "%, " + thr.elapsedMs() + "ms elapsed");
			}
		}
		System.out.println(sm.getClass().getSimpleName() + " took " + thr.elapsedMs() + "ms");
		
		if (validateSort(cloneArr)) {
			System.out.println("Successfully sorted!");
		} else {
			throw new Exception("Not sorted!");
		}
		
		return new SortResults(thr.elapsedMs(), cloneArr);
	}
	
	public static class SortResults {
		public int elapsedMs;
		public int[] arr;
		SortResults(int elapsedMs, int[] arr) {
			this.elapsedMs = elapsedMs;
			this.arr = arr;
		}
	}
	
	// Print contents of an integer array.
	public static void print(int[] arr) {
		System.out.println(arr.length + " elements");
		for (int i : arr)
			System.out.print(String.valueOf(i) + ",");
		System.out.print("\n");
	}
	
	// Validate that the integer array is in ascending order.
	public static boolean validateSort(int[] arr) {
		for (int i=1;i<arr.length;i++) 
			if (arr[i-1] > arr[i]) return false;
		return true;
	}
	
	// Run SortMethod the specified number of iterations and return average milliseconds.
//	public static float benchmark(SortMethod sm, int iterations) throws IOException {
//		long start = System.nanoTime(), end;
//		int[] arr = arrayFromCSVFile(new File(filePath));
//		end = System.nanoTime();
//		System.out.println("Loading took " + (end-start)/1000000.0f + " ms.");
//		
//		for (int i : arr)
//			System.out.print(arr[i] + ",");
//		
//		long sum = 0;
//		for (int i=0;i<iterations;i++) {
//			start = System.nanoTime();
//			sm.sort(arr.clone());
//			end = System.nanoTime();
//			sum += (end-start);
//		}
//		return (sum/iterations)/1000000.0f;
//	}
	
	// Read CSV file into an integer array. (Probably over-complicated utilizing a char buffer.)
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

	// Write the integer array to a CSV file, with a header at the top.
	public static boolean writeArrayToCSV(int[] arr, File out, String header) {
		System.out.println("Writing to '" + out.getName() + "'...");
		try {
			out.delete();
			out.createNewFile();
			FileWriter fw = new FileWriter(out);
			fw.write(header + "\n");
			for (int i=0;i<arr.length;i++) {
				fw.write(String.valueOf(arr[i]) + ",");
			}
			fw.flush();
			fw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
