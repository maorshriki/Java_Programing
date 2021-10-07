package mms;

import linkedlist.LinkedList;
import linkedlist.LinkedListIterator;

/**
 * Represents a managed memory space. The memory space is handled by managing a list 
 * of allocated memory blocks, and a list free memory blocks. Blocks move from one
 * list to the other as side effects of executing "malloc" and "free" function calls.
 */
public class MemorySpace {
	
	// A list that keeps track of the memory blocks that are presently allocated
	private LinkedList<MemoryBlock> allocatedList;

	// A list that keeps track of the memory blocks that are presently free
	private LinkedList<MemoryBlock> freeList;

	/**
	 * Constructs a new managed memory space of a given maximal size.
	 * Specifically, constructs an empty list of allocated blocks, 
	 * and a free list containing a single block which represents the entire memory
	 * space. The base address of this single block is zero, and its length is the
	 * given memory size.
	 * 
	 * @param maxSize
	 *            the size of the memory space to be managed
	 */
	public MemorySpace(int maxSize) {
		allocatedList = new LinkedList<MemoryBlock>();
		freeList = new LinkedList<MemoryBlock>();
		freeList.addLast(new MemoryBlock(0, maxSize));
	}

	/**
	 * Allocates a memory block of a requested length (in words). Returns the
	 * base address of the allocated block, or -1 if unable to allocate.
	 * 
	 * This implementation scans the freeList, looking for the first free memory block 
	 * whose length equals at least the given length. If such a block is found, the method 
	 * performs the following operations:
	 * 
	 * (1) A new memory block is constructed. The base address of the new block is set to
	 * the base address of the found free block. The length of the new block is set to the value 
	 * of the method's length parameter.
	 * 
	 * (2) The new memory block is appended to the end of the allocatedList.
	 * 
	 * (3) The base address and the length of the found free block are updated, to reflect the allocation.
	 * For example, suppose that the requested block length is 17, and suppose that the base
	 * address and length of the the found free block are 250 and 20, respectively.
	 * In such a case, the base address and length of of the allocated block
	 * are set to 250 and 17, respectively, and the base address and length
	 * of the found free block are set to 267 and 3, respectively.
	 * 
	 * (4) The new memory block is returned.
	 * 
	 * If the length of the found block is exactly the same as the requested length, 
	 * then the found block is removed from the freeList and appended to the allocatedList.
	 * 
	 * @param length
	 *        the length (in words) of the memory block that has to be allocated
	 * @return the base address of the allocated block, or -1 if unable to allocate
	 */
	public int malloc(int length) {		
		LinkedListIterator<MemoryBlock> freeit = freeList.iterator();
		MemoryBlock freeBlock; 
		while(freeit.hasNext()) {
			freeBlock = freeit.next();
			if (freeBlock.length > length) {
				MemoryBlock newBlock = new MemoryBlock(freeBlock.baseAddress, length);
				allocatedList.addLast(newBlock);
				freeBlock.length = freeBlock.length - length;
				freeBlock.baseAddress = freeBlock.baseAddress + length;
				return newBlock.baseAddress;
			}
			if (freeBlock.length == length) {
				allocatedList.addLast(freeBlock);
				freeList.remove(freeBlock);
				return freeBlock.baseAddress;
			}
		}
		return -1;
	}

	/**
	 * Frees the memory block whose base address equals the given address.
	 * This implementation deletes the block whose base address equals the given 
	 * address from the allocatedList, and adds it at the end of the free list. 
	 * 
	 * @param baseAddress
	 *            the starting address of the block to freeList
	 */
	public void free(int address) {
		LinkedListIterator<MemoryBlock> allocatedit = allocatedList.iterator();
		MemoryBlock allocatedBlock;
		while (allocatedit.hasNext()) {
		    allocatedBlock = allocatedit.next();
			if (allocatedBlock.baseAddress == address) {
				freeList.addLast(allocatedBlock);
				allocatedList.remove(allocatedBlock);
				return;
			}
		}
	}
	
	/**
	 * A textual representation of the current states of the free list and the allocated list, 
	 * using some sensible and easy to read format.
	 */
	public String toString() {
		return freeList.toString() + "\n" + allocatedList.toString();		
	}
	
}
