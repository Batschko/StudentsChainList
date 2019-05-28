package scl.student;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

	private TreeElement root;
	private boolean sort;
	
	public BinaryTree(Student[] data, boolean sort) {
		this.sort = sort;
		create(data);
	}
	
	/**
	 * Creates the Tree by the given data array
	 * @param data
	 */
	public void create(Student[] data) {
		for(Student s : data) {
			this.add(s);
		}
	}
	
	/**
	 * Empties the tree
	 */
	public void empty() {
		root = null;
	}
	
	/**
	 * Get root of Tree
	 * @return root
	 */
	public TreeElement getRoot() {
		return this.root;
	}
	
	/**
	 * Gets a specific Element by name - recursive
	 * @param name
	 * @param element Next Element in tree
	 * @return The element that holds the object with the name
	 */
	public TreeElement get(String name, TreeElement element) {
		Student student = element.getContent();
		String studName = student.getName();
		if(name.equals(studName)) {
			return element;
		} else if(name.compareTo(studName) > 0) {
			return get(name, element.getRight());
		} else if(name.compareTo(studName) < 0) {
			return get(name, element.getLeft());
		} else {
			return null;
		}
	}
	
	/**
	 * Gets a specific Element by matriculation number - recursive
	 * @param name
	 * @param element Next Element in tree
	 * @return The element that holds the object with the matriculation number
	 */
	private TreeElement get(int mn, TreeElement element) {
		Student student = element.getContent();
		int studMn = student.getMatriculationNumber();
		if(mn == studMn) {
			return element;
		} else if(mn > studMn) {
			return get(mn, element.getRight());
		} else if(mn < studMn) {
			return get(mn, element.getLeft());
		} else {
			return null;
		}
	}
	
	/**
	 * Swaps Element and SwapElement - the before Elements are needed to maintain the tree structure
	 * @param before Before Element
	 * @param element Element to be swapped
	 * @param swapElement Element that will be swapped with element
	 * @param beforeSwap element before swapElement
	 * @param dir 1 if we came from right, -1 if we came from left in the tree
	 * @param right true if we are in the right subtree
	 * @return returns the student of element
	 */
	private Student swapElements(TreeElement before, TreeElement element, TreeElement swapElement, TreeElement beforeSwap, int dir, boolean right) {
		
		TreeElement eR = element.getRight();
		TreeElement eL = element.getLeft();
		
		TreeElement sR = swapElement.getRight();
		TreeElement sL = swapElement.getLeft();
		
		if(dir == 1) {
			before.setRight(swapElement);
		} else if(dir == -1) {
			before.setLeft(swapElement);
		}
		
		swapElement.setRight(eR);
		swapElement.setLeft(eL);
		
		if(right) {
			beforeSwap.setLeft(element);
		} else {
			beforeSwap.setRight(element);
		}
		
		element.setLeft(sL);
		element.setRight(sR);
		
		return element.getContent();

	}
	
	/**
	 * Get the Essentials - means the Element that need to be swapped, the element before it and the direction - if element is left or right to before
	 * @param element Element to start searching
	 * @param mn number to search by
	 * @return List with before on 0, element on 1 and dir on 2
	 */
	private List getEssentialElements(TreeElement element, int mn) {
		
			boolean loop = true;
			TreeElement before = element;
			int dir = 0;
			while(loop) {
				if(element == null) {
					return null;
				}
				if(mn > element.getContent().getMatriculationNumber()) {
					dir = 1;
					element = element.getRight();
				} else if(mn < element.getContent().getMatriculationNumber()) {
					dir = -1;
					element = element.getLeft();
				}
				
				if(mn == element.getContent().getMatriculationNumber()) {
					loop = false;
				}
				
				if(loop) {
					before = element;
				}
			}
			List essentials = new ArrayList();
			essentials.add(before);
			essentials.add(element);
			essentials.add(dir);
			return essentials;
	}
	
	/**
	 * Get the Essentials - means the Element that need to be swapped, the element before it and the direction - if element is left or right to before
	 * @param element Element to start searching
	 * @param mn name to search by
	 * @return List with before on 0, element on 1 and dir on 2
	 */
	private List getEssentialElements(TreeElement element, String name, int mn) {
		
		boolean loop = true;
		TreeElement before = element;
		int dir = 0;
		while(loop) {
			if(element == null) {
				return null;
			}
			if(name.compareTo(element.getContent().getName()) > 0) {
				dir = 1;
				element = element.getRight();
			} else if(name.compareTo(element.getContent().getName()) < 0) {
				dir = -1;
				element = element.getLeft();
			}
			
			if(name.equals(element.getContent().getName()) && mn == element.getContent().getMatriculationNumber()) {
				loop = false;
			}
			
			if(loop) {
				before = element;
			}
		}
		List essentials = new ArrayList();
		essentials.add(before);
		essentials.add(element);
		essentials.add(dir);
		return essentials;
	}
	
	/**
	 * Remove the the given element
	 * @param before TreeElement before Element
	 * @param element element to remove
	 * @param dir 1 if element is right to before, -1 if left
	 * @return the Student of eleme
	 */
	private Student remove(TreeElement before, TreeElement element, int dir) {
		if(element.hasRight()) {
			boolean onlyRight = true;
			TreeElement swapElement = element.getRight();
			TreeElement beforeSwap = element;
			while(swapElement.hasLeft()) {
				if(swapElement.hasLeft()) {
					beforeSwap = swapElement;
				}
				swapElement = swapElement.getLeft();
				onlyRight = false;
			}
			
			if(onlyRight) {
				if(element.hasLeft()) {
					swapElement.setLeft(element.getLeft());
				}
				if(dir==1) {
					before.setRight(swapElement);
				} else if(dir == -1) {
					before.setLeft(swapElement);
				}
				return element.getContent();
			//Is leaf - no more sons
			} else if(!swapElement.hasLeft() && !swapElement.hasRight()) {
				Student removed = this.swapElements(before, element, swapElement, beforeSwap, dir, true);
				beforeSwap.setLeft(null);
				return removed;
			//Swap Elements then pull right sons on previous spot of swapElement
			} else if(swapElement.hasRight()) {
				Student removed = this.swapElements(before, element, swapElement, beforeSwap, dir, true);
				beforeSwap.setLeft(element.getRight());
				return removed;
			}
		} else if(element.hasLeft()) {
			boolean onlyLeft = true;
			TreeElement swapElement = element.getLeft();
			TreeElement beforeSwap = element;
			while(swapElement.hasRight()) {
				if(swapElement.hasRight()) {
					beforeSwap = swapElement;
				}
				swapElement = swapElement.getRight();
				onlyLeft = false;
			}
			if(onlyLeft) {
				if(element.hasRight()) {
					swapElement.setLeft(element.getRight());
				}
				if(dir==1) {
					before.setRight(swapElement);
				} else if(dir == -1) {
					before.setLeft(swapElement);
				}
				
				return element.getContent();
			//Is leaf - no more sons
			} else if(!swapElement.hasLeft() && !swapElement.hasRight()) {
				Student removed = this.swapElements(before, element, swapElement, beforeSwap, dir, false);
				beforeSwap.setRight(null);
				return removed;
			//Swap Elements then pull right sons on previous spot of swapElement
			} else if(swapElement.hasRight()) {
				Student removed = this.swapElements(before, element, swapElement, beforeSwap, dir, false);
				beforeSwap.setRight(element.getLeft());
				return removed;
			} else {
				return null;
			}
		} else {
			if(dir == 1) {
				before.setRight(null);
				return element.getContent();
			} else if(dir == -1) {
				before.setLeft(null);
				return element.getContent();
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Remove a Student by Matriculation number
	 * @param mn
	 * @return Removed Student
	 */
	public Student remove(int mn) {
		TreeElement element = this.getRoot();
		if(!element.hasLeft() && !element.hasRight()) {
			this.root = null;
			return element.getContent();
		} else {
			List essentials = this.getEssentialElements(element, mn);
			TreeElement before = (TreeElement)essentials.get(0);
			element = (TreeElement)essentials.get(1);
			int dir = (int)essentials.get(2);
			
			return this.remove(before, element, dir);
		}
		
	}
	
	/**
	 * Remove a Student by Matriculation number and Name
	 * @param mn
	 * @return Removed Student
	 */
	public Student remove(String name, int mn) {
		TreeElement element = this.getRoot();
		if(!element.hasLeft() && !element.hasRight()) {
			this.root = null;
			return element.getContent();
		} else {
			List essentials = this.getEssentialElements(element, name, mn);
			TreeElement before = (TreeElement)essentials.get(0);
			element = (TreeElement)essentials.get(1);
			int dir = (int)essentials.get(2);
			
			return this.remove(before, element, dir);
		}
		
	}
	
	/**
	 * Add given student to the tree
	 * @param student
	 */
	public void add(Student student) {
		
		TreeElement element = new TreeElement(student, null, null);
		
		if(root == null) {
			root = element;
			return;
		}
		
		TreeElement current = root;
		int result = 0;
		boolean loop = true;
		while(loop) {
			Student comp = current.getContent();
			if(sort) {
				if(student.getMatriculationNumber() <= comp.getMatriculationNumber()) {
					result = -1;
				} else {
					result = 1;
				}
			} else {
				if(student.getName().compareTo(comp.getName()) <= 0) {
					result = -1;
				} else {
					result = 1;
				}
			}
			
			TreeElement next = null;
			if(result == -1) {
				next = current.getLeft();
			} else if(result == 1) {
				next = current.getRight();
			} else {
				throw new RuntimeException("shit happens");
			}
			
			if(next == null) {
				loop = false;
			} else {
				current = next;
			}
		}
		
		if(result == -1) {
			current.setLeft(element);
		} else if(result == 1) {
			current.setRight(element);
		} 
		
	}
	
}
