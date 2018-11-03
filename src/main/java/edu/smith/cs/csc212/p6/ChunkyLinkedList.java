package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;


/**
 * This is a data structure that has an array inside each node of a Linked List.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyLinkedList<T> implements P6List<T> {
	private int chunkSize;
	private SinglyLinkedList<FixedSizeList<T>> chunks;
	

	public ChunkyLinkedList(int chunkSize) {
		this.chunkSize = chunkSize;
		chunks = new SinglyLinkedList<>();
		chunks.addBack(new FixedSizeList<>(chunkSize));
	}

	/**
	 * Delete the first item of the first chunk.
	 * Complexity: O(1)
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T removeFront() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}else {
			FixedSizeList<T> firstArray = chunks.getFront();
			T deleted = firstArray.removeFront();
			//if the chunk is empty then remove the chunk
			if (firstArray.isEmpty()) {
				chunks.removeFront();
			}
			return deleted;
			
		}
	}
	
	/**
	 * Delete the last item of the last chunk.
	 * Complexity: O(1)
	 * 
	 * @return the value of the item that was deleted.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T removeBack() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}else {
			FixedSizeList<T> lastArray = chunks.getBack();
			T deleted = lastArray.removeBack();
			//if the chunk is empty then remove it 
			if (lastArray.isEmpty()) {
				chunks.removeBack();
			}
			return deleted;
			
		}
	}

	/**
	 * Delete the item at the specified index in the list.
	 * Complexity: O(n^2)
	 * 
	 * @param index a number from 0 to size (excluding size).
	 * @return the value that was removed.
	 * @throws EmptyListError if the list is empty.
	 * @throws BadIndexError if the index does not exist.
	 */
	@Override
	public T removeIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		else if (index==0) {
			return this.removeFront();
		}
		else if (index==size()) {
			return this.removeBack();
		} 
		else {
			int start = 0;
			int chunkIndex = 0;
			for (FixedSizeList<T> chunk : this.chunks) {
				// calculate bounds of this chunk.
				int end = start + chunk.size();
				
				// Check whether the index should be in this chunk:
				if (start <= index && index < end) {
					// if chunk is empty, then we need to remove the chunk here 
					T deleted =  chunk.removeIndex(index-start);
					if (chunk.isEmpty()) {
						chunk.removeIndex(chunkIndex);
						}
					return deleted;
				}
				chunkIndex += 1;
				start = end;
			}
			
		}
		throw new BadIndexError();
	}

	/**
	 * Add an item to the start of of the first chunk. 
	 * Complexity: O(1)
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addFront(T item) {
		FixedSizeList<T> firstArray = chunks.getFront();
		if (firstArray.size() < this.chunkSize) {
			firstArray.addFront(item);
		} else {
			//if chunk is full create a new chunk and add it to the front then add
			// the item to the front of the new chunk
			FixedSizeList<T> newArray = new FixedSizeList<T>(this.chunkSize);
			chunks.addFront(newArray);
			newArray.addFront(item);
		} 
	}

	/**
	 * Add an item to the end of the last chunk.
	 * Complexity: O(1)
	 * 
	 * @param item the data to add to the list.
	 */
	@Override
	public void addBack(T item) {
		FixedSizeList<T> lastArray = chunks.getBack();
		if (lastArray.size() < this.chunkSize) {
			lastArray.addBack(item);
		} else {
			//if chunk is full create a new chunk and add it to the front then add
			// the item to the front of the new chunk
			FixedSizeList<T> newArray = new FixedSizeList<T>(this.chunkSize);
			chunks.addBack(newArray);
			newArray.addBack(item);
			}
		}

	/**
	 * Add an item to an index in this list. 
	 * Complexity: O(n^2)
	 * 
	 * @param item  the data to add to the list.
	 * @param index the index at which to add the item.
	 */
	@Override
	public void addIndex(T item, int index) {
		
		if (index==0) {
			this.addFront(item);
			return;
		}
		else if (index==size()) {
			this.addBack(item);
			return;
		}
		else {
			int start = 0;
			int chunkIndex = 0;
			for (FixedSizeList<T> chunk : this.chunks) {
				// calculate bounds of this chunk.
				int end = start + chunk.size();
				
				// Check whether the index should be in this chunk:
				if (start <= index && index < end) {
					// if chunk is full, then we need to insert a new chunk here (before chunkIndex+1) and add to that
					
					if (chunk.size()>= this.chunkSize) {
						FixedSizeList<T> newChunk = new FixedSizeList<T>(this.chunkSize);
						chunks.addIndex(newChunk,chunkIndex);
						newChunk.addIndex(item, index-start);
					}
					chunk.addIndex(item, index - start);
					return;
				}
				
				chunkIndex += 1;
				start = end;
		}
	}
}
	
	/**
	 * Get the first item in first chunk of the list.
	 * Complexity: O(1)
	 * 
	 * @return the item.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T getFront() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		return this.chunks.getFront().getFront();
	}

	/**
	 * Get the last item in last chunk of the list.
	 * Complexity: O(1)
	 * 
	 * @return the item.
	 * @throws EmptyListError if the list is empty.
	 */
	@Override
	public T getBack() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		return this.chunks.getBack().getBack();
	}

	/**
	 * Find the index-th element of this list.
	 * Complexity: O(n^2)
	 * 
	 * @param index a number from 0 to size, excluding size.
	 * @return the value at index.
	 * @throws EmptyListError if the list is empty.
	 * @throws BadIndexError if the index does not exist.
	 */
	@Override
	public T getIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.getIndex(index - start);
			}
			
			// update bounds of next chunk.
			start = end;
		}
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
		int total = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			total += chunk.size();
		}
		return total;
	}

	/**
	 * This is true if the list is empty or if the chunk inside the list is empty. 
	 * Complexity: O(1)
	 * 
	 * @return true if the list is empty.
	 */
	@Override
	public boolean isEmpty() {
		return this.chunks.isEmpty() || this.chunks.getFront().isEmpty();
	}
}
