package mcmillan.jeff;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static final String filePath = "input1.txt";
	
	public static void main(String[] args) {
		try {
			for (int i : arrayFromCSVFile(new File(filePath))) {
				System.out.print(i + ",");
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int[] arrayFromCSVFile(File in) throws IOException {
		ArrayList<Integer> ints = new ArrayList<>();
		
		char[] cbuf = new char[16];
		FileReader fr = new FileReader(in);
		int charsRead = fr.read(cbuf);
		String currString = "";
		
		while (cbuf != null) {
			for (int c=0;c<charsRead;c++) {
				currString += cbuf[c];
				System.out.print(cbuf[c]);
			}
			System.out.println();
			System.err.println(currString);
			int commaIdx = currString.indexOf(',');
			if (commaIdx < currString.length()-1) {
				while (commaIdx > -1) {
					String number = currString.substring(0, commaIdx);
					ints.add(Integer.parseInt(number));
					currString = currString.substring(commaIdx+1);
					commaIdx = currString.indexOf(',');
				}
			}
			charsRead = fr.read(cbuf);
		}
		if (currString.length() > 0) {
			String number = currString.substring(0, currString.length()-2);
			ints.add(Integer.parseInt(number));
		}
		
		fr.close();
		
		int[] iArray = new int[ints.size()];
		for (int i=0;i<ints.size();i++)
			iArray[i] = ints.get(i);
		System.out.println("FINISHED ARRAY!\n\n");
		return iArray;
	}

}
