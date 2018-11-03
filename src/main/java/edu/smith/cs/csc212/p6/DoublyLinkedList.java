package edu.smith.cs.csc212.p6;



import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;



public class DoublyLinkedList<T> implements P6List<T> {
	private Node<T> start;
	private Node<T> end;
	
	
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	

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
		
		T a = start.value;
		start = start.after;
		
		//make start.before point to null if there is indeed a start
		if (start!=null) {
			start.before =null;
		}
		return a;
	}

	/**
	 * Delete the item at the back of the list.
	 * Complexity: O(1)
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T removeBack() {
		checkNotEmpty();
		
		// if there in only one node in the list then that same node is the start and also the node
		// so make both start and end point to null to delete that node
		if (size()==1) {
				Node <T> only = end;
				only.after = null;
				
				start=null;
				end=null;
				return only.value;
			}
			// if there are 2 items in a list then make the secondLast/ first item the last and only item
			else if (size()==2) {
				Node<T> last = end;
				Node <T> secondLast= last.before;
			
				start=secondLast;
				end=secondLast;
				secondLast.after=null;
			
				return last.value;
		}
		else {
		
			Node <T> last = end;
			Node <T> secondLast= last.before;
			Node <T> thirdLast= secondLast.before;
		
			end = secondLast;
			end.after=null;
			end.before=thirdLast;
		
			return last.value;
		}
		
		
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

		if (index == 0) {
			return removeFront();
		} 
		else if (index == size()-1) {
			return removeBack();
		} 
		else {
			int count = 0;
			Node<T> a = start;
			while (count != index) {
				count++;
				a = a.after;
			}
			// for [ 'b','a','c'], to remove the item 'a' from the specified index, sever
			//the direct connection of a with 'b' & 'c' and instead form direct connections
			//between 'b' & 'c'
			Node<T> b = a.before;
			Node<T> c= a.after;
			b.after = c;
			c.before= b;
			
			return a.value;

		}
		
	}

	/**
	 * Add an item to the front of this list. 
	 * Complexity: O(1)
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addFront(T item) {
		// if a list is empty then create a new node and make it the start and end
		if (start==null) {
			Node <T> newStart = new Node <T> (item);
			start= newStart;
			end=newStart;
			}
		//else make a new node and set it to be the new start node by assigning current.before to the new node
		//and the newNode.after to current. 
		else {
				Node <T> current = start;
				Node <T> newStart = new Node <T> (item);
				newStart.after=current;
				current.before=newStart;
				start= newStart;
			}
	}

	/**
	 * Add an item to the back of this list. The item should be at
	 * getIndex(size()-1) after this call.
	 * Complexity: O(1)
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addBack(T item) {
		// if a list is empty then create a new node and make it the start and end
		if (start==null) {
			Node<T> newBack = new Node<>(item);
			start= newBack;
			end=newBack;
		}
		////else make a new node and set it to be the new end node by assigning current.after to the new node,
		//the newNode.before to current, and the newNode.after to null.
		else {
			Node <T> current = end;
			Node<T> newBack = new Node<>(item);
			newBack.before = current;
			newBack.after = null;
			current.after = newBack;
			end = newBack;
		
		}
	}
	
	/**
	 * Add an item to an index in this list. 
	 * Complexity: O(n)
	 * 
	 * @param item  the data to add to the list.
	 * @param index the index at which to add the item.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public void addIndex(T item, int index) {
		checkNotEmpty();

		if (index == 0) {
			addFront(item);
		} 
		else if (index == size()) {
			addBack(item);
		} 
		else {
			int count = 0;
			Node<T> a = start;
			while (count != index-1) {
				count++;
				a = a.after;
			}
			//for ['a','b'] if you want to add a new item 'c' at the index where 'b' is currently 
			//sever ties between 'b' & 'a' and for form new ties between 'a' & 'c' and 'c' & 'b'
			//so that 'b' get pushed back to the next index. It should look like ['a','c','b']
			Node <T> b= a.after;
			Node<T> c = new Node<>(item);
			a.after=c;
			b.before=c;
			c.before=a;
			c.after=b;
			
			
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
	 * Complexity: O(1)
	 * 
	 * @return the item.
	 * @throws EmptyListError
	 */
	@Override
	public T getBack() {
		checkNotEmpty();
		return end.value;
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
		checkNotEmpty();
		int at = 0;
		for (Node<T> current = start; current != null; current = current.after) {
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
		int counter=0;
		for (Node<T> n = this.start; n != null; n = n.after){
			counter++;
			
		}
		return counter;
	
		
	}

	/**
	 * This is true if the list is empty. 
	 * Complexity: O(1)
	 * 
	 * @return true if the list is empty.
	 */
	@Override
	public boolean isEmpty() {
		return start==null;
		
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
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
