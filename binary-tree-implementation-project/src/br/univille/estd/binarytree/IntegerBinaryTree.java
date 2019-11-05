package br.univille.estd.binarytree;


public class IntegerBinaryTree {

private LinkedBinaryTree<Integer> intTree;

	
	public IntegerBinaryTree() {
		intTree = new LinkedBinaryTree<Integer>();
	}

	
	public void add(Integer i) {
		if (isEmpty())
			intTree.addRoot(i);
		else
			insertElement(intTree.root(), i);
	}

	
	public boolean contains(Integer i) {
		return containsElement(intTree.root(), i);
	}

	
	private boolean containsElement(BTPosition<Integer> root, Integer i) {
		Integer n = root.getElement();
		if (n.equals(i)) {
			return true;
		} 
		if (root.getRight() != null) {
			containsElement(root.getRight(), i);
		}
		if (root.getLeft() != null) {
			containsElement(root.getLeft(), i);
		}
		return false;
	}


	public void remove(Integer i) {
		BTPosition<Integer> v = new BTPosition(i);
		intTree.remove(v);
	}

	
	public boolean isEmpty() {
		return size() == 0;
	}

	
	public int size() {
		return intTree.size();
	}
	

	public void clear() {
		intTree = new LinkedBinaryTree<>();
	}
	

	private void insertElement(BTPosition<Integer> position, Integer i) {
		Integer element = position.getElement();
		if (i < element)
			shouldInsertLeftElement(position, i);
		else if (i > element)
			shouldInsertRightElement(position, i);
		else
			throw new IllegalArgumentException("A árvore já contém o valor " + i);
	}
	

	private void shouldInsertLeftElement(BTPosition<Integer> position, Integer i) {
		if (position.getLeft() != null)
			insertElement(position.getLeft(), i);
		
		intTree.insertLeft(position, i);
	}
	
		
	private void shouldInsertRightElement(BTPosition<Integer> position, Integer i) {
		if (position.getRight() != null)
			insertElement(position.getRight(), i);
		
		intTree.insertRight(position, i);
	}
	
}
