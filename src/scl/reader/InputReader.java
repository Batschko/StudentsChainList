package scl.reader;

import java.io.*;

public class InputReader {
	/**
	 * Reads a positive integer number from user
	 * @return -1 if not a proper integer
	 */
	public static int readInt() {
		String in = readString();
		int num = -1;
		try {
			num = Integer.parseInt(in);
		} catch (Exception e) {
			
		}
		return num;
	}
	
	/**
	 * Reads a String from user input
	 * @return Read string from user
	 */
	public static String readString() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String in="";
		try {
			in = br.readLine();
		} catch(Exception e) {
			
		}
		
		return in;
	}
}
