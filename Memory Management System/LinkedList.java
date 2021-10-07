package linkedlist;

/**
 * Represents a list of Nodes. 
 */
public class LinkedList<T> {
	
	private Node<T> first = null; // pointer to the first (dummy) node of this list
	private Node<T> last = null;  //  pointer to the last node of this list
	private int size = 0;         // number of elements in this list */
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		// Creates a dummy node and makes first and last point to it.
		first = new Node<T>(null);
		last = first;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node<T> getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		// Skips the dummy node
		Node<T> current = first.next;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

	/**
	 * Gets the object located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved object
	 * @return the object at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException();
		}
		return getNode(index).data;
	}	

	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list. 
	 * 
	 * @param insert
	 *        the object to be inserted into the list
	 * @param index
	 *        the index before which the object should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, T insert) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		size++;
		Node<T> newNode = new Node<T>(insert);
		Node<T> before = this.first;
		Node<T> after = this.first.next;
		for (int i = 0; i < index; i++) {
			after = after.next;
			before = before.next;
		}
		if (after == null) {
			before.next = newNode;
			last = newNode;
		} else {
			before.next = newNode;
			newNode.next = after;
		}
	}
	
	/**
	 * Creates a new node with a reference to the given object, and appends it
	 * to the end of this list(the node will become the list's last node).
	 * The running time of this method must be O(1).
	 * 
	 * @param insert
	 *        the given object
	 */
	public void addLast(T insert) {
		Node<T> node = new Node<T>(insert);
		last.next = node;
		last = node;		
		this.size++;
	}

	/**
	 * Creates a new node with a reference to the given object, and inserts it
	 * at the beginning of this list (the node will become the list's first node).
	 * The running time of this method must be O(1).
	 * 
	 * @param insert
	 *        the given object
	 */
	public void addFirst(T insert) {
		add(0, insert);
	}

	
	/**
	 * Gets the index of the node pointing to the given object.
	 * 
	 * @param t
	 *        the given object
	 * @return the index of the first occurence of the object, or -1 if the object is not in this list
	 */
	public int indexOf(T t) {
		Node<T> current = first.next; // Skips the dummy node
		int index = 0;
		while (current != null) {
			if (current.data.equals(t)) {
				return index;
			}
			current = current.next;
			index++;
		}
		return -1;
	}


	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException();
		}
		this.size--;
		if (index == 0) {
			this.first.next = this.first.next.next;
		} else {
			Node<T> prev = getNode(index - 1);
			prev.next = prev.next.next;
		}
	}

	/**
	 * Removes from this list the first occurence of a node pointing to the given object.
	 * 
	 * @param remove the object that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(T remove) {
		int index = indexOf(remove);
		if (index == -1) {
			throw new IllegalArgumentException();
		}
		remove(index);
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public LinkedListIterator<T> iterator(){
		return new LinkedListIterator<T>(first.next);
	}
	
	/**
	 * A textual representation of this list, useful for debugging.
	 */
	public String toString() {
		String s = "";
		Node<T> current = first.next;  // Skips the dummy
		while (current != null) {
			s = s + current.data + " ";
			current = current.next;
		}		
		return s;
	}
}