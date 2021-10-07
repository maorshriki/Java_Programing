package corona;


public class CoronaQueue {

    Person[] data;
    int[] next;
    int[] prev;
    int[] ref; 			 // the reference array
    int size;			 // the current number of subjects in the queue
    int head; 			 // the index of the lists 'head'. -1 if the list is empty.
    int tail;			 // the index of the lists 'tail'. -1 if the list is empty.
    int free;			 // the index of the first 'free' element.

    /**
     * Creates an empty data structure with the given capacity.
     * The capacity dictates how many different subjects may be put into the data structure.
     * Moreover, the capacity gives an upper bound to the ID number of a Person to be put in the data structure.
     * 
     */
    public CoronaQueue(int capacity){
        this.size = 0;
        this.data = new Person[capacity];
        this.next = new int[capacity];
        this.prev = new int[capacity];
        this.head = -1;
        this.tail = -1;
        this.free = capacity - 1;
        for (int i = 0; i < next.length; i++) {
			next[i] = i - 1;
		}
        this.ref = new int[capacity + 1];
        for (int i = 0; i < ref.length; i++) {
			ref[i] = -1;
		}
    }

    /**
     * Returns the size of the queue.
     *
     * @return the size of the queue
     */
    public int size(){
        return this.size;
    }

    /**
     * Inserts a given Person into the queue.
     * Inesertion should be done at the tail of the queue.
     * If the given person is already in the queue this function should do nothing.
     *	Throws an illegal state exception if the queue is full.
     *
     * @param t - the Task to be inserted.
     * @throws IllegalStateException - if queue is full.
     */
    public void enqueue(Person p){
		int id = p.id;
        if(ref[id] != -1) return;
		
    	if (size == data.length){
    		throw new IllegalStateException("queue is full");
    	}
        if(tail == -1){
        	data[free] = p;
        	int tempNext = next[free];
        	next[free] = -1;
        	prev[free] = -1;
        	ref[id] = free;
        	tail = free;
        	head = free;
        	free = tempNext;
        	size++;
        	return;
        }
        data[free] = p;
        ref[id] = free;
        int tempNext = next[free];
        next[free] = -1;
        prev[free] = tail;
        next[tail] = free;
        tail = free;
        free = tempNext;
        size++;
    }


    /**
     * Removes and returns a Person from the queue.
     * The person removed is the one which sits at the head of the queue.
     * If the queue is empty returns null.
     */
    public Person dequeue(){
    	if (size == 0) return null;
    	Person ans = data[head];
    	data[head] = null;
    	ref[ans.id] = -1;
    	size--;
    	int newHead = next[head];
    	next[head] = free;
    	free = head;
    	head = newHead;
    	if(size == 0){
    		tail = -1;
    	}
    	return ans;
    }

    /**
     * Removes a Person from (possibly) the middle of the queue.
     * 
     * Does nothing if the Person is not already in the queue.
     * Recall that you are not allowed to traverse all elements in the queue. In particular no loops or recursion.
     * Think about all the different edge cases and the variables which need to be updated.
     * Make sure you understand the role of the reference array for this function.
     * 
     * @param p - the Person to remove
     */
    public void remove(Person p){
    	int index = ref[p.id];
    	if (index ==  -1) return;
    	if (index ==  head) dequeue();
    	else{
    		data[index] = null;
    		next[prev[index]] = next[index];
    		if (index == tail){
    			tail = prev[index];
    		} else {
    			prev[next[index]] = prev[index];
    		}
    		next[index] = free;
    		free = index;
    		size--;
    		ref[p.id] = -1;
    	}
    }
    
    /*
     * The following functions may be used for debugging your code. 
     */
    private void debugNext(){
    	for (int i = 0; i < next.length; i++) {
			System.out.println(next[i]);
		}
    	System.out.println();
    }
    private void debugPrev(){
    	for (int i = 0; i < prev.length; i++) {
			System.out.println(prev[i]);
		}
    	System.out.println();
    }
    private void debugData(){
    	for (int i = 0; i < data.length; i++) {
			System.out.println(data[i]);
		}
    	System.out.println();
    }
    
    private void debugRef(){
    	for (int i = 0; i < ref.length; i++) {
			System.out.println(ref[i]);
		}
    	System.out.println();
    }
    
    /*
     * Test code; output should be:
		Aaron, ID number: 1
		Baron, ID number: 2
		Cauron, ID number: 3
		Dareon, ID number: 4
		Aaron, ID number: 1
		Baron, ID number: 2
		Aaron, ID number: 1
		
		Baron, ID number: 2
		Cauron, ID number: 3
		
		Aaron, ID number: 1
		Dareon, ID number: 4
		
		Aaron, ID number: 1
		Cauron, ID number: 3
     */
    public static void main (String[] args){
    	CoronaQueue demo = new CoronaQueue(4);
    	Person a = new Person(1, "Aaron");
    	Person b = new Person(2, "Baron");
    	Person c = new Person(3, "Cauron");
    	Person d = new Person(4, "Dareon");
    	
    	demo.enqueue(a);
    	demo.enqueue(b);
    	demo.enqueue(c);
    	demo.enqueue(d);
    	System.out.println(demo.dequeue());
    	System.out.println(demo.dequeue());
    	demo.enqueue(a);
    	System.out.println(demo.dequeue());
    	demo.enqueue(b);
    	System.out.println(demo.dequeue());
    	System.out.println(demo.dequeue());
    	System.out.println(demo.dequeue());
    	demo.enqueue(a);
    	System.out.println(demo.dequeue());
    	
    	System.out.println();
    	demo.enqueue(a);
    	demo.enqueue(b);
    	demo.enqueue(c);
    	demo.enqueue(d);
    	demo.remove(a);
    	System.out.println(demo.dequeue());
    	demo.remove(d);
    	System.out.println(demo.dequeue());
    	
    	System.out.println();
    	demo.enqueue(a);
    	demo.enqueue(b);
    	demo.enqueue(c);
    	demo.enqueue(d);
    	demo.remove(b);
    	demo.remove(c);
    	System.out.println(demo.dequeue());
    	System.out.println(demo.dequeue());
    	
    	System.out.println();
    	demo.enqueue(a);
    	demo.enqueue(b);
    	demo.enqueue(c);
    	demo.enqueue(d);
    	demo.remove(b);
    	demo.remove(d);
    	System.out.println(demo.dequeue());
    	System.out.println(demo.dequeue());
    	
    	


    	
    }
}
