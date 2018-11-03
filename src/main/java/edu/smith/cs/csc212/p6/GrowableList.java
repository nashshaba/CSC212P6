package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.EmptyListError;


public class GrowableList<T> implements P6List<T> {
	public static int START_SIZE = 4; //you can change this to 4 or smaller value to test
	private Object[] array;
	private int fill;
	
	public GrowableList() {
		this.array = new Object[START_SIZE];
		this.fill = 0;
	}

	/**
	 * Delete the item at the front of the list.
	 * Complexity: O(n)
	 * 
	 * @return the value of the item that was deleted.
	 */
	@Override
	public T removeFront() {
		return removeIndex(0);
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
		if (this.size() == 0) {
			throw new EmptyListError();
		}
		T value = this.getIndex(fill-1);
		this.array[fill-1] = null;
		fill--;
		return value;
		
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
		if (this.size() == 0) {
			throw new EmptyListError();
		}
		T removed = this.getIndex(index);
		fill--;
		for (int i=index; i<fill; i++) {
			this.array[i] = this.array[i+1];
		}
		this.array[fill] = null;
		return removed;
		
	}

	/**
	 * Add an item to the front of this list. The item should be at getIndex(0)
	 * after this call.
	 * Complexity: O(n)
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addFront(T item) {
		addIndex(item, 0);
		
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
		// I've implemented part of this for you.
		if (fill >= this.array.length) { 
			addIndex(item,-1);
		}
		
		this.array[fill++] = item;
	}

	/**
	 * Add an item to an index in this list. 
	 * Complexity: O(n).
	 * 
	 * @param item  the data to add to the list.
	 * @param index the index at which to add the item.
	 */
	@Override
	public void addIndex(T item, int index) {
		if (fill >= array.length) {
			addIndex(item,index);
		}
		// loop backwards, shifting items to the right.
		for (int j=fill; j>index; j--) {
			array[j] = array[j-1];
		}
		array[index] = item;
		fill++;	
		
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
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		return this.getIndex(0);
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
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		return this.getIndex(this.fill-1);
	}

	/**
	 * Do not allow unchecked warnings in any other method.
	 * Keep the "guessing" the objects are actually a T here.
	 * Do that by calling this method instead of using the array directly.
	 */
	@SuppressWarnings("unchecked")
	
	/**
	 * Find the index-th element of this list.
	 * Complexity: O(1)
	 * 
	 * @param index a number from 0 to size, excluding size.
	 * @return the value at index.
	 */
	@Override
	public T getIndex(int index) {
		return (T) this.array[index];
	}

	/**
	 * Calculate the size of the list.
	 * Complexity: O(1)
	 * 
	 * @return the length of the list, or zero if empty.
	 */
	@Override
	public int size() {
		return fill;
	}

	/**
	 * This is true if the list is empty. 
	 * Complexity: O(1)
	 * 
	 * @return true if the list is empty.
	 */
	@Override
	public boolean isEmpty() {
		return fill == 0;
	}


}
