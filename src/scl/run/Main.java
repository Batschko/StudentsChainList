package scl.run;

import scl.student.*;
import scl.writer.*;
import scl.reader.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
	
	public static void main(String[] args) {
		new Main().run();
	}
	
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
	
	private Student getStudent() {
		System.out.print(""
				+ "Choose number of selection (0 to exit): \n"
				+ "1: Matriculation number\n"
				+ "2: Name\n");
		boolean loop = true;
		int selection = -1;
		while(loop) {
			System.out.print(">>> ");
			selection = InputReader.readInt();
			if(selection < 0 || selection > 2) {
				System.out.println("Must be in range!");
			} else if(selection == 0) {
				System.out.println();
				return null;
			} else {
				loop = false;
			}
		}
		
		Student student = null;
		if(selection==1) {
			Student searched = this.searchNumber(false);
			String name = searched.getName();
			int mn = searched.getMatriculationNumber();
			Student removedA = nameList.remove(name, mn);
			Student removedB = numberList.remove(mn);
			if(!removedA.equals(removedB)) {
				System.out.println("Something went wrong!"); 
				return null;
			}
			student = removedA;
		} else if(selection == 2) {
			List<TreeElement> studentElements = searchName(false); 
			
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
			Student searched =  studentElements.get(number-1).getContent();
			int mn = searched.getMatriculationNumber();
			String name = searched.getName();
			Student removedA = nameList.remove(name, mn);
			Student removedB = numberList.remove(mn);
			if(!removedA.equals(removedB)) {
				System.out.println("Something went wrong!"); 
				return null;
			}
			student = removedA;
		}
		
		return student;
	}
	
	private void changeName() {
		System.out.print(""
				+ "Choose number of selection (0 to exit): \n"
				+ "1: Matriculation number\n"
				+ "2: Name\n");
		boolean loop = true;
		int selection = -1;
		while(loop) {
			System.out.print(">>> ");
			selection = InputReader.readInt();
			if(selection < 0 || selection > 2) {
				System.out.println("Must be in range!");
			} else if(selection == 0) {
				System.out.println();
			} else {
				loop = false;
			}
		}
		Student student = null;
		if(selection==1) {
			student = this.searchNumber(true);
		}if(selection==2) {
			student = this.searchNumber(true);
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
	
	private void changeMN() {
		System.out.print(""
				+ "Choose number of selection (0 to exit): \n"
				+ "1: Matriculation number\n"
				+ "2: Name\n");
		boolean loop = true;
		int selection = -1;
		while(loop) {
			System.out.print(">>> ");
			selection = InputReader.readInt();
			if(selection < 0 || selection > 2) {
				System.out.println("Must be in range!");
			} else if(selection == 0) {
				System.out.println();
			} else {
				loop = false;
			}
		}
		Student student = null;
		if(selection==1) {
			student = this.searchNumber(true);
		}if(selection==2) {
			student = this.searchNumber(true);
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
	
	private Student removeName() {
		String name = null;
		boolean loop = true;

		List<TreeElement> studentElements = searchName(false); 
		
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
		if(!removedA.equals(removedB)) {
			System.out.println("Something went wrong!"); 
			return null;
		}
		this.removeFromArray(removedA);
		System.out.println("Removed "+removedA.getName()+" - "+removedA.getMatriculationNumber()+"!");
		return removedA;
	}
	
	private Student removeMN() {
		int mn = -1;
		boolean loop = true;
		
		Student searched = this.searchNumber(false);
		String name = searched.getName();
		mn = searched.getMatriculationNumber();
		Student removedA = nameList.remove(name, mn);
		Student removedB = numberList.remove(mn);
		if(!removedA.equals(removedB)) {
			System.out.println("Something went wrong!"); 
			return null;
		}
		this.removeFromArray(removedA);
		System.out.println("Removed "+removedA.getName()+" - "+removedA.getMatriculationNumber()+"!");
		return removedA;
	}
	
	//WARUM BALLERT DER NULL IN DIE ARRAYLIST?????
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
	
	
	private void printArray() {
		for(Student s :data) {
			printStudent(s);
		}
	}

	private void empty() {
		numberList.empty();
		nameList.empty();
		data=new Student[0];
	}
	
	private boolean printTree(TreeElement next) {
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
	
	private void sortName() {
		System.out.println("\n--- Students ---");
		printTree(nameList.getRoot());
		System.out.println("----------------\n");
	}
	
	private void sortMN() {
		System.out.println("\n--- Students ---");
		printTree(numberList.getRoot());
		System.out.println("----------------\n");
	}
	
	
	private void printStudent(Student student) {
		if(student==null) {
			System.out.println("No student found");
		}else {
			System.out.println(student.getName()+" - "+student.getMatriculationNumber());
		}
	}
	
	
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
	
	private void addToArray(Student student) {
		Student[] ndata = new Student[data.length+1];
		for(int i=0;i<data.length;i++) {
			ndata[i] = data[i];
		}
		ndata[ndata.length-1] = student;
		data = ndata;
	}
	
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
	
	private boolean checkMnumber(int mn) {
		for(int i=0;i<data.length;i++) {
			if(data[i].getMatriculationNumber()==mn) {
				return false;
			}
		}
		return true;
	}
	
	private void save() {
		try {
			sw.writeData(data);
		} catch (IOException e) {
			System.out.println("Error writing to file - "+e.getMessage());
		}
	}
	
	private void exit() {
		loop = false;
	}
}
