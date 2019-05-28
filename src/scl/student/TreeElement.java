package scl.student;

public class TreeElement {

	private TreeElement left;
	private TreeElement right;
	private Student content;
	
	/**
	 * Constructor for TreeElement
	 * @param content
	 * @param left
	 * @param right
	 */
	public TreeElement(Student content, TreeElement left, TreeElement right) {
		this.setContent(content);
		this.setLeft(left);
		this.setRight(right);
	}
	
	/**
	 * Check if the Element have a left son
	 * @return true if left son exists
	 */
	public boolean hasLeft() {
		if(left == null) return false;
		return true;
	}
	
	/**
	 * Check if the Element have a right son
	 * @return true if right son exists
	 */
	public boolean hasRight() {
		if(right == null) return false;
		return true;
	}

	/**
	 * Get the right sin
	 * @return Right son
	 */
	public TreeElement getRight() {
		return right;
	}

	/**
	 * Set the right son
	 * @param right
	 */
	public void setRight(TreeElement right) {
		this.right = right;
	}

	/**
	 * Get the Left son
	 * @return left son
	 */
	public TreeElement getLeft() {
		return left;
	}
	
	 /**
	  * left son
	  * @param left
	  */
	public void setLeft(TreeElement left) {
		this.left = left;
	}

	/**
	 * Get the the Student object of the Element
	 * @return student object
	 */
	public Student getContent() {
		return content;
	}

	/**
	 * Set the Student object
	 * @param content student object
	 */
	public void setContent(Student content) {
		this.content = content;
	}
	
}
