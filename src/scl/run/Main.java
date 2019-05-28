package scl.run;

import scl.student.*;
import scl.writer.*;
import scl.reader.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Program handling the BinaryTree
 */
public class Main {
	
	private String menu =""
			+ "Students Chain List\n"
			+ "L  - Empty the list\n"
			+ "ZN - Sort by name and print list\n"
			+ "ZM - Sort by matriculation number and print list\n"
			+ "RN - Remove by name\n"
			+ "RM - Remove by matriculation number\n"
			+ "CM - Change Matriculation\n"
			+ "CN - Change Name\n"
			+ "SN - Search by name\n"
            + "SM - Search by matriculation number \n"
            + "N  - Insert student\n"
            + "S  - Save data\n"
            + "E  - End programm\n";
	
	private BufferedReader br;
	private Student[] data;
	private BinaryTree nameList;
	private BinaryTree numberList;
	private StudentReader sr;
	private StudentWriter sw;
	private boolean loop;
	
	/**
	 * Initialize the programm - Read the students.txt file
	 */
	public Main() {
		loop = true;
		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			sr = new StudentReader();
			sw = new StudentWriter();
		} catch (FileNotFoundException e1) {
			System.out.println("Error creating reader - "+e1.getMessage());
		} catch (IOException e) {
			System.out.println("Error creating writer - "+e.getMessage());
		}
		try {
			data = sr.readFile();
		} catch (IOException e) {
			System.out.println("Error reading data from file - "+e.getMessage());
			data = new Student[0];
		} catch(IllegalArgumentException iae) {
			System.out.println("Error importing data from file - "+iae.getMessage()+"\nQuitting Program");
			System.exit(0);
		}
		
		nameList = new BinaryTree(data, false);
		numberList = new BinaryTree(data, true);
	}
	
	/**
	 * Runs the progamm
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		new Main().run();
	}
	
	/**
	 * Run the Main application
	 */
	public void run() {
		while(loop) {
			System.out.println(menu);
			System.out.print(">>> ");
			String input = null;
			try {
				input = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			execute(input);
		}
	}
	
	/**
	 * Switch to choose the menupoint to execute
	 * @param command Argument from the menu
	 */
	private void execute(String command) {
		switch(command.toUpperCase()) {
		case "L": empty(); break;
		case "ZN": sortName(); break;
		case "ZM": sortMN(); break;
		case "RN": removeName(); break;
		case "RM": removeMN(); break;
		case "CM": changeMN(); break;
		case "CN": changeName(); break;
		case "SN": searchName(true);break;
		case "SM": searchNumber(true);break;
		case "N": insert(); break;
		case "S": save(); break;
		case "E": exit(); break;
		case "Z": printArray();break;
		}
	}
	
	/**
	 * Change the name of a specific student object by removing, changing the object and adding it again.
	 * If its wanted to identify by name an extra selection will be shown in case there are multiple students with identical names.
	 */
	private void changeName() {
		System.out.print(""
				+ "Choose number of selection (0 to exit): \n"
				+ "1: Name\n"
				+ "2: Matriculation number\n");
		boolean loop = true;
		int selection = -1;
		while(loop) {
			System.out.print(">>> ");
			selection = InputReader.readInt();
			if(selection < 0 || selection > 2) {
				System.out.println("Must be in range!");
			} else if(selection == 0) {
				System.out.println();
				return;
			} else {
				loop = false;
			}
		}
		Student student = null;
		if(selection==1) {
			List<TreeElement> studentElements=this.searchName(false);
			System.out.println("Choose number of student:");
			for(int i=0;i<studentElements.size();i++) {
				Student s = studentElements.get(i).getContent();
				System.out.println((i+1)+": "+s.getName()+" - "+s.getMatriculationNumber());
			}
			int number = -1;
			while(loop) {
				System.out.print(">>> ");
				number = InputReader.readInt();
				if(number <= 0 || number > studentElements.size()) {
					System.out.println("Input must be a valid number!");
				} else {
					loop = false;
				}
			}
			student =  studentElements.get(number-1).getContent();
		}if(selection==2) {
			student = this.searchNumber(false);
		}
		if(student == null) {
			System.out.println("Something went wrong...");
		} else {
			System.out.print("New Name for "+student.getName()+": ");
			String name = InputReader.readString();
			nameList.remove(student.getName(), student.getMatriculationNumber());
			numberList.remove(student.getMatriculationNumber());
			student.setName(name);
			nameList.add(student);
			numberList.add(student);
		}
	}
	
	/**
	 * Change the matriculation number of a specific student object by removing, changing the object and adding it again.
	 * If its wanted to identify by name an extra selection will be shown in case there are multiple students with identical names.
	 */
	private void changeMN() {
		System.out.print(""
				+ "Choose number of selection (0 to exit): \n"
				+ "1: Name\n"
				+ "2: Matriculation number\n");
		boolean loop = true;
		int selection = -1;
		while(loop) {
			System.out.print(">>> ");
			selection = InputReader.readInt();
			if(selection < 0 || selection > 2) {
				System.out.println("Must be in range!");
			} else if(selection == 0) {
				System.out.println();
				return;
			} else {
				loop = false;
			}
		}
		Student student = null;
		
		if(selection==1) {
			List<TreeElement> studentElements=this.searchName(false);
			System.out.println("Choose number of student:");
			for(int i=0;i<studentElements.size();i++) {
				Student s = studentElements.get(i).getContent();
				System.out.println((i+1)+": "+s.getName()+" - "+s.getMatriculationNumber());
			}
			int number = -1;
			loop=true;
			while(loop) {
				System.out.print(">>> ");
				number = InputReader.readInt();
				if(number <= 0 || number > studentElements.size()) {
					System.out.println("Input must be a valid number!");
				} else {
					loop = false;
				}
			}
			student =  studentElements.get(number-1).getContent();
			
		}if(selection==2) {
			student = this.searchNumber(false);
		}
		
		if(student == null) {
			System.out.println("Something went wrong...");
		} else {
			loop = true;
			int number = -1;
			while(loop) {
				System.out.print("New Number for "+student.getName()+": ");
				number = InputReader.readInt();
				if(number <= 0) {
					System.out.println("New Matriculation Number must be bigger than 0!");
				} else {
					loop = false;
				}
			}
			nameList.remove(student.getName(), student.getMatriculationNumber());
			numberList.remove(student.getMatriculationNumber());
			student.setMatriculationNumber(number);
			nameList.add(student);
			numberList.add(student);
		}
	}
	
	/**
	 * Removes a student by name.
	 * For each name there is an extra selection in case there are multiple students with the same name.
	 * @return Removed student
	 */
	private Student removeName() {
		String name = null;
		boolean loop = true;

		List<TreeElement> studentElements = searchName(false); 
		
		if(studentElements.size() == 0) {
			return null;
		}
		
		System.out.println("Choose number of student:");
		for(int i=0;i<studentElements.size();i++) {
			Student s = studentElements.get(i).getContent();
			System.out.println((i+1)+": "+s.getName()+" - "+s.getMatriculationNumber());
		}
		int number = -1;
		while(loop) {
			System.out.print(">>> ");
			number = InputReader.readInt();
			if(number <= 0 || number > studentElements.size()) {
				System.out.println("Input must be a valid number!");
			} else {
				loop = false;
			}
		}
		Student student =  studentElements.get(number-1).getContent();
		int mn = student.getMatriculationNumber();
		name = student.getName();
		Student removedA = nameList.remove(name, mn);
		Student removedB = numberList.remove(mn);
		if(removedA == null || removedB == null) {
			System.out.println("An error occured while deleting!");
			return null;
		}
		if(!removedA.equals(removedB)) {
			System.out.println("Something went wrong!"); 
			return null;
		}
		this.removeFromArray(removedA);
		System.out.println("Removed "+removedA.getName()+" - "+removedA.getMatriculationNumber()+"!");
		return removedA;
	}
	
	/**
	 * Remove student by Matriculation Number
	 * @return Removed student
	 */
	private Student removeMN() {
		int mn = -1;
		boolean loop = true;
		
		Student searched = this.searchNumber(false);
		
		if(searched == null) {
			return null;
		}
		
		String name = searched.getName();
		mn = searched.getMatriculationNumber();
		Student removedA = nameList.remove(name, mn);
		Student removedB = numberList.remove(mn);
		if(removedA == null || removedB == null) {
			System.out.println("An error occured while deleting!");
			return null;
		}
		if(!removedA.equals(removedB)) {
			System.out.println("Something went wrong!"); 
			return null;
		}
		this.removeFromArray(removedA);
		System.out.println("Removed "+removedA.getName()+" - "+removedA.getMatriculationNumber()+"!");
		return removedA;
	}
	
	/**
	 * Search for Students by name
	 * @param print If true the method will print all found students
	 * @return All found students
	 */
	private List<TreeElement> searchName(boolean print) {
		String name=null;
		boolean loop = true;
		while(loop) {
			System.out.print("Name: ");
			try {
				name = br.readLine();
				loop = false;
			} catch(IOException ioe) {
				System.out.println("Error reading input - "+ioe.getMessage());
			}
		}
		ArrayList<TreeElement> studenten = new ArrayList<TreeElement>();
		TreeElement s=binaryNameSearch(nameList.getRoot(), name);
		loop = true;
		if(s!=null) {
			studenten.add(s);
		}
		while(s!=null && loop) {
				if(s.hasLeft()) {
					s=binaryNameSearch(s.getLeft(), name);
				}else if(s.hasRight()) {
					s=binaryNameSearch(s.getRight(), name);
				}else loop=false;
				
				if(loop && s!=null) {
					studenten.add(s);
				}
		}
		
		if(studenten.size()==0) {
			System.out.println("No student with name: "+name+" found");
		}
		
		if(print) {
			System.out.println("\n--- Students ---");
			for(TreeElement te : studenten) {
				printStudent(te.getContent());
			}
			System.out.println("----------------\n");
		}
		return studenten;
	}
	

	/**
	 * Print a student by matriculation number
	 * @param print If true the method will print the found student
	 * @return Found student
	 */
	private Student searchNumber(boolean print) {
		int mNr=0;
		boolean loop = true;
		while(loop) {
			System.out.print("Matriculation Number: ");
			try {
				mNr = Integer.parseInt(br.readLine());
				loop = false;
			} catch(IOException ioe) {
				System.out.println("Error reading input - "+ioe.getMessage());
			} catch(NumberFormatException nfe) {
				System.out.println("Number must be an integer!");
			}
		}
		
		TreeElement s=binaryNumberSearch(numberList.getRoot(), mNr);
		if(s == null) {
			System.out.println("No student with mNR: "+mNr+" found");
			return null;
		}else {
			if(print) {
				printStudent(s.getContent());
			}
			return s.getContent();
		}
	}

	/**
	 * Search recursive trough the tree by matriculation number
	 * @param next Next element in tree - first must be the root
	 * @param mNr matriculation number to search for
	 * @return The TreeElement with the found matriculation number
	 */
	private TreeElement binaryNumberSearch(TreeElement next,int mNr) {
		if(next==null) {
			return null;
		}else if(mNr==next.getContent().getMatriculationNumber()) {
			return next;
		}else if(mNr<=next.getContent().getMatriculationNumber()) {
			return binaryNumberSearch(next.getLeft(),mNr);
		}else {
			return binaryNumberSearch(next.getRight(),mNr);
		}
	}
	
	/**
	 * Search recursive trough the tree by name
	 * @param next Next element in tree - first must be the root
	 * @param mNr Name to search for
	 * @return The TreeElement with the found name
	 */
	private TreeElement binaryNameSearch(TreeElement next,String name) {
		if(next==null) {
			return null;
		}else if(next.getContent().getName().equals(name)) {
			return next;
		}else if(name.compareTo(next.getContent().getName())<0) {
			return binaryNameSearch(next.getLeft(),name);
		}else if(name.compareTo(next.getContent().getName())>0) {
			return binaryNameSearch(next.getRight(),name);
		}
		return null;
	}
	
	/**
	 * Print the data Array
	 */
	private void printArray() {
		System.out.println("\n--- Secret Array! ---");
		for(Student s :data) {
			printStudent(s);
		}
		System.out.println("---------------------\n");
	}

	/**
	 * Empty both lists
	 */
	private void empty() {
		numberList.empty();
		nameList.empty();
		data=new Student[0];
	}
	
	/**
	 * Print the whole tree recursive
	 * @param next Next Element
	 * @return returns true when the method returns - used as indicator in earlier recusrive layer
	 */
	private boolean printTree(TreeElement next) {
		if(next==null) {
			return true;
		}
		TreeElement left = next.getLeft();
		TreeElement right = next.getRight();
		boolean returned = false;
		boolean printed = false;
		
		if(next.hasLeft()) {
			 returned = printTree(left);
		} else {
			printStudent(next.getContent());
			printed = true;
		}
		
		if(returned & !printed) {
			printStudent(next.getContent());
		} 
		
		if(next.hasRight()) {
			printTree(right);
		}
		
		return true;
	}
	
	/**
	 * Print the Name List
	 */
	private void sortName() {
		System.out.println("\n--- Students ---");
		printTree(nameList.getRoot());
		System.out.println("----------------\n");
	}
	
	/**
	 * Print the number list
	 */
	private void sortMN() {
		System.out.println("\n--- Students ---");
		printTree(numberList.getRoot());
		System.out.println("----------------\n");
	}
	
	/**
	 * Print a single student
	 * @param student Student to print
	 */
	private void printStudent(Student student) {
		if(student==null) {
			System.out.println("No student found");
		}else {
			System.out.println(student.getName()+" - "+student.getMatriculationNumber());
		}
	}
	
	/**
	 * Insert a student to the tree
	 */
	private void insert() {
		String name = "";
		String mn = "";
		int number = 0;
		boolean loop = true;
		while(loop) {
			System.out.print("\nName: ");
			try {
				name = br.readLine();
				loop = false;
			} catch(IOException ioe) {
				System.out.println("Error reading input - "+ioe.getMessage());
			}
		}
		loop = true;
		while(loop) {
			System.out.print("Matriculation Number: ");
			try {
				mn = br.readLine();
				number = Integer.parseInt(mn);
				if(!(checkMnumber(number))) {
					System.out.print("matriculation number already exists\n");	
				}else {
				
				loop = false;}
			
			} catch(IOException ioe) {
				System.out.println("Error reading input - "+ioe.getMessage());
			} catch(NumberFormatException nfe) {
				System.out.println("Number must be an integer!");
			}
		}
		System.out.println();
		Student student = new Student(name, number);
		addToArray(student);
		nameList.add(student);
		numberList.add(student);
	}
	
	/**
	 * Add student to array and extend it
	 * @param student
	 */
	private void addToArray(Student student) {
		Student[] ndata = new Student[data.length+1];
		for(int i=0;i<data.length;i++) {
			ndata[i] = data[i];
		}
		ndata[ndata.length-1] = student;
		data = ndata;
	}
	
	/**
	 * Remove from Array and decrease it
	 * @param student
	 */
	private void removeFromArray(Student student) {
		for(int i=0;i<data.length;i++) {
			if(data[i].equals(student)) {
				data[i] = data[data.length-1];
			}
		}
		Student[] ndata = new Student[data.length-1];
		for(int i=0;i<ndata.length;i++) {
			ndata[i] = data[i];
		}
		data = ndata;
	}
	
	/**
	 * Check if number is in data array
	 * @param mn
	 * @return true if it is
	 */
	private boolean checkMnumber(int mn) {
		for(int i=0;i<data.length;i++) {
			if(data[i].getMatriculationNumber()==mn) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Save the list to file
	 */
	private void save() {
		try {
			sw.writeData(data);
		} catch (IOException e) {
			System.out.println("Error writing to file - "+e.getMessage());
		}
	}
	
	/**
	 * End application
	 */
	private void exit() {
		loop = false;
	}
}
