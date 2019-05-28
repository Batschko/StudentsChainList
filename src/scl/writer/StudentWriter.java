package scl.writer;

import java.io.*;

import scl.student.Student;

public class StudentWriter {

	private File file;
	private PrintWriter pw;
	
	/**
	 * Constructor for StudentWriter
	 * @throws IOException
	 */
	public StudentWriter() throws IOException {
		file = new File("students/students.txt");
		if(!file.exists()) file.createNewFile();
	}
	
	/**
	 * Write given Student array to students.txt
	 * @param data
	 * @throws IOException
	 */
	public void writeData(Student[] data) throws IOException {
		pw = new PrintWriter(new FileWriter(file), true);
		for(int i=0;i<data.length;i++) {
			pw.println(data[i].getName()+","+data[i].getMatriculationNumber());
		}
		pw.close();
	}
	
}
