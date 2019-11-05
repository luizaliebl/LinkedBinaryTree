package br.univille.estd.binarytree;

import static org.hamcrest.CoreMatchers.describedAs;

import java.util.Iterator;

/**
 * Implementacao de uma √°rvore bin√°ria usando estrutura encadeada
 * @author leandersonandre
 *
 * @param <E>
 */
public class LinkedBinaryTree<E> {
	
	protected BTPosition<E> root; // Referencia para a raiz
	protected int size;           // Numero de nodos
	
	/**
	 * Construtor de uma √°rvore vazia
	 */
	public LinkedBinaryTree() {
		root = null; // inicia com uma √°rvore vazia
		size = 0;
	}
	
	/**
	 * Retorna o numero de nodos da √°rvore
	 * @return
	 */
	public int size() {
		return size;
	}
	
	public BTPosition<E> checkPosition(BTPosition<E> v) throws InvalidPositionException{
		if (v == null || !(v instanceof BTPosition)) {
			throw new InvalidPositionException("PosiÁ„o inv·lida");
		}
		return (BTPosition<E>)v;
	}
	
	/**
	 * Retorna se um nodo √© interno
	 */
	public boolean isInternal(BTPosition<E> v) throws InvalidPositionException{
		checkPosition(v);
		return (hasLeft(v) || hasRight(v));
	}
	
	
	/**
	 * Retorna se um nodo √© externo
	 */
	public boolean isExternal(BTPosition<E> v) throws InvalidPositionException{
		checkPosition(v);
		return (!hasLeft(v) && !hasRight(v));		
	}
	
	/**
	 * Retorna se um nodo √© a raiz
	 */
	public boolean isRoot(BTPosition<E> v) throws InvalidPositionException{
		checkPosition(v);
		return v.equals(root);
	}
	/**
	 * Retorna se um nodo tem o filho da esquerda
	 */
	public boolean hasLeft(BTPosition<E> v) throws InvalidPositionException{
		BTPosition<E> vv = checkPosition(v);
		return (vv.getLeft() != null);
	}
	
	/**
	 * Retorna se um nodo tem o filho da direita
	 */
	public boolean hasRight(BTPosition<E> v) throws InvalidPositionException{
		BTPosition<E> vv = checkPosition(v);
		return (vv.getRight() != null);
	}
	
	/**
	 * Retorna a raiz da √°rvore
	 */
	public BTPosition<E> root() throws EmptyTreeException{
		if(root == null ) {
			throw new EmptyTreeException("A ·rvore est· vazia");
		}
		return root;
	}
	
	/**
	 * Retorna o filho da esquerda de um nodo
	 * Lan√ßa BoundaryViolationException se nao tiver filho da esquerda
	 */
	public BTPosition<E> left(BTPosition<E> v) throws InvalidPositionException, BoundaryViolationException{
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> leftPos = vv.getLeft();
		if (leftPos == null) {
			throw new BoundaryViolationException("Raiz n„o tem filho ‡ esquerda");
		}
		return leftPos;
	}
	
	/**
	 * Retorna o filho da direita de um nodo
	 * Lan√ßa BoundaryViolationException se nao tiver filho da direita
	 */
	public BTPosition<E> right(BTPosition<E> v) throws InvalidPositionException, BoundaryViolationException{
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> rightPos = vv.getRight();
		if (rightPos == null) {
			throw new BoundaryViolationException("Raiz n„o tem filho ‡ direita");
		}
		return rightPos;
	}
	
	/**
	 * Retorna o pai de um nodo
	 * Lan√ßa BoundaryViolationException se nao tiver pai
	 */
	public BTPosition<E> parent(BTPosition<E> v) throws InvalidPositionException, BoundaryViolationException{
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> parentPos = vv.getParent();
		if (parentPos == null) {
			throw new BoundaryViolationException("Sem parentes");
		}
		return parentPos;		
	}
	
	/**
	 * Insere a raiz em uma arvore vazia
	 */
	public BTPosition<E> addRoot(E e) throws NonEmptyTreeException{
		if(root != null) {
			throw new NonEmptyTreeException("Tree already has a root");
		}
		size = 1;
		root = new BTPosition(e);
		return root;	
	}	
	
	/**
	 * Insere o filho da esquerda em um nodo
	 */
	public BTPosition<E> insertLeft(BTPosition<E> v, E e) throws InvalidPositionException{
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> leftPos = vv.getLeft();
		if(leftPos != null) {
			throw new InvalidPositionException("J· possui filho a esquerda");
		}
		BTPosition<E> ww = new BTPosition(e,vv);
		vv.setLeft(ww);
		size++;
		return ww;
	}
	
	/**
	 * Insere o filho da direita em um nodo
	 */
	public BTPosition<E> insertRight(BTPosition<E> v, E e) throws InvalidPositionException{
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> rightPos = vv.getRight();
		if(rightPos != null) {
			throw new InvalidPositionException("J· possui filho a esquerda");
		}
		BTPosition<E> ww = new BTPosition(e,vv);
		vv.setRight(ww);
		size++;
		return ww;
	}
	
	/**
	 * Substitui o elemento armazenado no nodo
	 * Retorna o elemento substituido
	 */
	public E replace(BTPosition<E> v, E o) throws InvalidPositionException{
		BTPosition<E> vv = checkPosition(v);
		E temp = v.getElement();
		vv.setElement(o);
		return temp;
	}
	
	/**
	 * Retorna o irmao de um nodo
	 * Lan√ßa BoundaryViolationException se nao tiver um irmao
	 */
	public BTPosition<E> sibling(BTPosition<E> v) throws InvalidPositionException, BoundaryViolationException{
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> parentPos = vv.getParent();
		if(parentPos != null) {
			BTPosition<E> sibPos;
			BTPosition<E> leftPos = parentPos.getLeft();
			if(leftPos == vv) {
				sibPos = parentPos.getRight();
			} else {
				sibPos = parentPos.getLeft();
			}
			if (sibPos != null) {
				return sibPos;
			}
			 throw new BoundaryViolationException("No sibling");
		}
		return null;
	}

	/**
	 * Remove um nodo com zero ou um filho
	 * Nao pode remover um nodo com dois filhos. Deve lancar InvalidPositionException
	 */
	public E remove(BTPosition<E> v) throws InvalidPositionException{
		
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> leftPos = vv.getLeft();
		BTPosition<E> rightPos = vv.getRight();
		if (leftPos != null && rightPos != null) {
			throw new InvalidPositionException("N„o È possÌvel remover com dois filhos");
		}
		BTPosition<E> ww;
		if(leftPos != null) {
			ww = leftPos;
		} else if (rightPos != null) {
			ww = rightPos;
		} else {
			ww = null;
		}
		if (vv == root) {
			if (ww != null) {
				ww.setParent(null);
			}
			root = ww;
		} else {
			BTPosition<E> uu = vv.getParent();
			if(vv == uu.getLeft()) {
				uu.setLeft(ww);
			} else {
				uu.setRight(uu);
			}
			if (ww != null) {
				ww.setParent(uu);
			}
			size--;
			return v.getElement();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 *  Cria um novo nodo para a arvore binaria
	 */
	protected BTPosition<E> createNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right) {
		BTPosition<E> node = new BTPosition<E>();
		node.setParent(parent);
		node.setLeft(left);
		node.setRight(right);
		node.setElement(element);
		size = size + 1;

		return node;
	}

}
