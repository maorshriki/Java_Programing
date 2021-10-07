// package vicSimulator;

/** Represents a memory unit, which is an indexed sequence of registers. 
 *  Enables reading from, or writing to, any individual register according to a given index. 
 *  The index is typically called "address". The addresses run from 0 to the memory's size, minus 1. */  
public class Memory {
		
	private Register[] m;  // an array of Register objects
	
	/** Constructs a memory of size registers, and sets all the register values to 0. */
	public Memory (int size) {
		m = new Register[size];
		for (int address = 0; address < m.length; address++) {
			m[address] = new Register();
		}		
		reset();
	}
	
	/** Sets the values of all the registers in this memory to 0. */
    public void reset () {
		for (int address = 0; address < m.length; address++) 		
		    m[address].setValue(0);
	}

   	/** Returns the value of the register whose address is the given address. */
   	public int getValue (int address) {
		return m[address].getValue();
	}
	
    /** Sets the register in the given address to the given value. */
    public void setValue (int address, int value) {
		m[address].setValue(value);
	}		
	
    /** Returns a subset of the memory's contents, as a formated string. Specifically:
     *  Returns the first 10 registers (where the top of the program normally resides)
     *  and the last 10 registers (where the variables normally reside).
     *  For each register, returns the register's address, and value. */
     public String toString () {		
	    String s = "";
	    for (int address = 0; address < 10; address++) {
		    int v = m[address].getValue();
		    s = s + address + "\t" + v + "\n";
	    }
	    s = s + "..." + "\n";
	    for (int address = (m.length - 10); address < m.length; address++) {
		    int v = m[address].getValue();
		    s = s + address + "\t" + v + "\n";
	    }	
	    return s;
    } 	
}
