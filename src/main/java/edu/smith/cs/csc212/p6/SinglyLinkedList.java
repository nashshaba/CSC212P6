package edu.smith.cs.csc212.p6;

import java.util.Iterator;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;




public class SinglyLinkedList<T> implements P6List<T>, Iterable<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;
	
	/**
	 * Delete the item at the front of the list by assigning start to the second node.
	 * Complexity: O(1)
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T removeFront() {
		checkNotEmpty();
		T before = start.value;
		start = start.next;
		return before;
		
	}
	
	/**
	 * Delete the item at the back of the list.
	 * Complexity: O(n)
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T removeBack() {
		checkNotEmpty();
		
		// if there's only 1 item in the list then point start to null
		if (size()==1) {
			T onlyV = start.value;
			start = null;
			return onlyV;
		} else {
		// loop through list until you find a node whose .next.next is null
		// that node is now "current." Delete the last node by pointing current.next to null.
		for (Node<T> current = start; current != null; current = current.next) {
			if (current.next.next==null) {
					T tbr = current.next.value;
					current.next= null;
							
					return tbr;
					}
			}
		}
		// never expecting this to run
		return null;
	}
	
	/**
	 * Delete the item at the specified index in the list.
	 * Complexity: O(n)
	 * 
	 * @param index a number from 0 to size (excluding size).
	 * @return the value that was removed.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		
		T removed = getIndex(index);
		int in=0;
		
		//if you want to remove something from the 0th index then assign start to the second node
		if (index==0) {
			start=start.next;
		} 
		// if you want to remove something from the last index then you can use removeBack().
		else if (index==size()-1){
			return removeBack();
		}
		//loop through the list until you reach the item at the index before the index from which
		//you want to remove. This is now "current." Delete the item at the specified index in the list
		// by assigning current.next=current.next.next
		for (Node<T> current = start; current != null; current = current.next) {
			if(in==index-1) {
				current.next=current.next.next;
				}
			in++;
			}
		
		return removed;
		}

	/**
	 * Add an item to the front of this list. 
	 * Complexity: O(1)
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	/**
	 * Add an item to the back of this list. The item should be at
	 * getIndex(size()-1) after this call.
	 * Complexity: O(n)
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addBack(T item) {
		// if list is empty then create a new node and add make it the start
		if (start==null) {
			start= new Node<T>(item,null);
		}
		//loop through till you reach the end and create a new node and make it the last node in the list
		else{ 
			Node<T> current=start;
			
			while (current.next != null) {
				current=current.next;
				}
			
			current.next= new Node<T>(item,null);
			}
		}
		
	/**
	 * Add an item to an index in this list. 
	 * Complexity: O(n)
	 * 
	 * @param item  the data to add to the list.
	 * @param index the index at which to add the item.
	 */
	@Override
	public void addIndex(T item, int index) {
		//if you want to add to the 0th index use addFront()
		if(index==0) {
			addFront(item);
		}
		// if you want to add to the last index use addBack()
		else if (index==size()) {
			addBack(item);
		} 
		//loop through the list until you reach the item at the index before the index from which
		//you want to remove. This is now "current." Create a new node and add at the specified index
		// by assigning current.next to the new node.
		else {
			int gi=0;
			for (Node<T> current = start; current != null; current = current.next) {
				if (gi==index-1) {
					Node <T> nn = new Node<T>(item,current.next);
					current.next=nn;
				}
				gi++;
			}
		}
	}

	/**
	 * Get the first item in the list.
	 * Complexity: O(1)
	 * 
	 * @return the item.
	 * @throws EmptyListError
	 */
	@Override
	public T getFront() {
		checkNotEmpty();
		return start.value;
	}

	/**
	 * Get the last item in the list.
	 * Complexity: O(n)
	 * 
	 * @return the item.
	 * @throws EmptyListError
	 */
	@Override
	public T getBack() {
		checkNotEmpty();
		return getIndex(size()-1);
	}

	/**
	 * Find the index-th element of this list.
	 * Complexity: O(n)
	 * 
	 * @param index a number from 0 to size, excluding size.
	 * @return the value at index.
	 * @throws BadIndexError if the index does not exist.
	 */
	@Override
	public T getIndex(int index) {
		int at = 0;
		for (Node<T> current = start; current != null; current = current.next) {
			if (at == index) {
				return current.value;
			}
			at++;
		}
		// We couldn't find it, throw an exception!
		throw new BadIndexError();
	}

	/**
	 * Calculate the size of the list.
	 * Complexity: O(n)
	 * 
	 * @return the length of the list, or zero if empty.
	 */
	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	/**
	 * This is true if the list is empty. 
	 * Complexity: O(1)
	 * 
	 * @return true if the list is empty.
	 */
	@Override
	public boolean isEmpty() {
		return size()==0;
		
	}

	/**
	 * Helper method to throw the right error for an empty state.
	 */
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * I'm providing this class so that SinglyLinkedList can be used in a for loop
	 * for {@linkplain ChunkyLinkedList}. This Iterator type is what java uses for
	 * {@code for (T x : list) { }} lops.
	 * 
	 * @author jfoley
	 *
	 * @param <T>
	 */
	private static class Iter<T> implements Iterator<T> {
		/**
		 * This is the value that walks through the list.
		 */
		Node<T> current;

		/**
		 * This constructor details where to start, given a list.
		 * @param list - the SinglyLinkedList to iterate or loop over.
		 */
		public Iter(SinglyLinkedList<T> list) {
			this.current = list.start;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T found = current.value;
			current = current.next;
			return found;
		}
	}
	
	/**
	 * Implement iterator() so that {@code SinglyLinkedList} can be used in a for loop.
	 * @return an object that understands "next()" and "hasNext()".
	 */
	public Iterator<T> iterator() {
		return new Iter<>(this);
	}
}
