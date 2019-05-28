package scl.student;

public class Student implements Comparable<Student> {

	private int mn;
	private String name;
	
	/*
	 * Set the Name
	 * @param name Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Constructor for Student object
	 * @param name Name
	 * @param mn Matriculation Number
	 */
	public Student(String name, int mn) {
		this.mn = mn;
		this.name = name;
	}
	
	/**
	 * Get the Matriculation Number
	 * @return Matriculation Number
	 */
	public int getMatriculationNumber() {
		return this.mn;
	}
	
	/**
	 * Set the Matriculation Number
	 * @param mn Matriculation Number
	 */
	public void setMatriculationNumber(int mn) {
		this.mn = mn;
	}
	
	/**
	 * Get the Name
	 * @return Name
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	/**
	 * Checks if another student object is equal by comparing the Matriculation Numbers
	 * @param o Student object to check equality
	 * @return if Matriculation Numbers are equal
	 */
	public boolean equals(Object o) {
		Student that = (Student)o;
		if(this.mn == that.getMatriculationNumber()) return true;
		return false;
	}
	
	@Override
	/**
	 * Compares this Student object to another
	 * @param that Student object to compare
	 * @return <1 if lower, 0 if equal, >1
	 */
	public int compareTo(Student that) {
		return this.mn - that.getMatriculationNumber();
	}
	
}
