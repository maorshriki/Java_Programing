// package vicSimulator;

// import std.StdIn;

/** Represents a Vic computer.
 *  It is assumed that users of this class are familiar with the Vic computer, 
 *  described in www.idc.ac.il/vic. 
 * <br/> The Computer's hardware consists of the following components:
 * <UL>  
 * <LI> Data register: a register.
 * <LI> Program counter: a register.
 * <LI> Memory: a sequence of registers.
 * <LI> Input unit: a stream of numbers. In this implementation, the input unit is simulated by a text file. 
 *  When the computer is instructed to execute a READ command, it reads the next number from this file and 
 *  puts it in the data register.
 * <LI> Output unit: a stream of numbers. In this implementation, the output unit is simulated by
 *      standard output (by default, the console).
 *      When the computer is instructed to execute a WRITE command, it writes the current value of the 
 *      data register to the standard output.
 * <LI> Processor: In this implementation, the processor is emulated by the run method of this class.
 * </UL>  
 * The Computer's software is a program written in the numeric Vic machine language.
 * Such a program normally resides in a text file which is loaded into the computer's memory.
 * This is done by the loadProgram method of this class. */

public class Computer {
	
	/** This constant represents the size of the memory unit of this Computer
	 *  (number of registers). The default value is 100. */
	public final static int MEM_SIZE = 100;

	/** This constant represents the memory address at which the constant 0 is stored.
	 *  The default value is MEM_SIZE - 2. */
	public final static int LOCATION_OF_ZERO = MEM_SIZE - 2;
	
	/** This constant represents the memory address at which the number 1 is stored.
	 *  The default value is MEM_SIZE - 1. */
	public final static int LOCATION_OF_ONE = MEM_SIZE - 1;
	
	// The commands of the Vic machine langauge.
	private final static int READ = 8;
	private final static int WRITE = 9;
	private final static int ADD = 1;
	private final static int SUB = 2;
	private final static int LOAD = 3;
	private final static int STORE = 4;
	private final static int GOTO = 5;
	private final static int GOTOZ = 6;
	private final static int GOTOP = 7;
	private final static int STOP = 0;

	// The fields of this computer
	private Memory m;
	private Register dReg;
	private Register pc;
	
	/** Constructs a Vic computer.
	 *  Specifically: constructs a memory that has MEM_SIZE registers, a data register, 
	 *  and a program counter. Next, resets the computer (see the reset method API).
	 *  Note: the loading of a program into memory, and the initialization of the input 
	 *  file, are not done by the constructor. This is done by the public methods 
	 *  setInput and loadProgram, respectively. */
	public Computer() {
		m = new Memory(MEM_SIZE);
		dReg = new Register();
		pc = new Register();
		reset();
	}
	
	/** Resets the computer. Specifically:
	 *  Resets the memory, sets the memory registers at addresses LOCATION_OF_ZERO
	 *  and LOCATION_OF_ONE to 0 and to 1, respectively, sets the data register 
	 *  and the program counter to 0. */
	public void reset() {
		m.reset();
		m.setValue(LOCATION_OF_ZERO, 0); // 0 constant
		m.setValue(LOCATION_OF_ONE, 1);  // 1 constant
		dReg.setValue(0);
		pc.setValue(0);
	}
	
	/** Executes the program currently stored in memory.
	 *  This is done by affecting the following fetch-execute cycle:
	 *  Fetches from memory the current word (3-digit number), i.e. the contents of the
	 *  memory register whose address is the current value of the program counter.
	 *  Extracts from this word the op-code (left-most digit) and the address (next 2 digits).
	 *  Next, executes the command mandated by the op-code, using the address if necessary.
	 *  As a side-effect of executing this command, modifies the program counter.
	 *  Next, loops to fetch the next word, and so on. */
	public void run() {
	    // Fetch and parse the first word in memory (address 0)
		int currentWord = m.getValue(pc.getValue());
		int opCode = opCode(currentWord);
		int addr = address(currentWord);		
		
		// Main execution loop
		while (opCode != STOP) {			
		    switch (opCode) {		        
		    	case READ:  { execRead(); break; }
		    	case WRITE: { execWrite(); break; }
		    	case ADD:   { execAdd(addr); break; }
		    	case SUB:   { execSub(addr); break; }
		    	case LOAD:  { execLoad(addr); break; }
		    	case STORE: { execStore(addr); break; }
		    	case GOTO:  { execGoto(addr); break; }
		    	case GOTOZ: { execGotoz(addr); break; }
		    	case GOTOP: { execGotop(addr); break; }
		    	default: {System.out.println("unrecognized op code"); break; }    		
		    }
		    // IMPORTANT: At this point the program counter has been modified,
		    // as a side-effect of executing the current command.
		    
		    // Fetch and parse the next word 
		    currentWord = m.getValue(pc.getValue());
			opCode = opCode(currentWord);
			addr = address(currentWord);
		}
		System.out.println("Run terminated normally");
	}
	
	// Private execution routines, one for each Vic command
	private void execRead() {
		dReg.setValue(StdIn.readInt());
		pc.addOne();
	}
	
	private void execWrite() {
		System.out.println(dReg);
		pc.addOne();
	}
	
	private void execAdd(int addr) {
	    dReg.setValue(dReg.getValue() + m.getValue(addr));
	    pc.addOne();
	}
	
	private void execSub(int addr) {
	    dReg.setValue(dReg.getValue() - m.getValue(addr));
	    pc.addOne();
	}
	
	private void execLoad(int addr) {
	    dReg.setValue(m.getValue(addr));
	    pc.addOne();
	}
	
	private void execStore(int addr) {
	    m.setValue(addr, dReg.getValue());
	    pc.addOne();
	}
	
	private void execGoto(int addr) {
	    pc.setValue(addr); 		
	}
	
	private void execGotoz(int addr) {
	    if (dReg.getValue() == 0)
     	    pc.setValue(addr);
	    else
	    	pc.addOne();
	}
	
	private void execGotop(int addr) {
	    if (dReg.getValue() > 0)
     	    pc.setValue(addr);
	    else
	    	pc.addOne();
	}
	
	private int opCode(int threeDigitNumber) {
		return (int) (threeDigitNumber / 100);		
	}
		
	private int address (int threeDigitNumber) {
		return (int) (threeDigitNumber % 100);
	}	
	
	/** Loads a program into memory, starting at address 0. The program is stored in the given text file.
	 * @param fileName The name of the file from which the program is read. It is assumed that the file 
	 * contains a stream of valid commands written in the numeric Vic machine language
	 * (the language specification is described in www.idc.ac.il/vic).
	 * The program is stored in the memory, one command per memory register, starting at address 0. */
	public void loadProgram(String fileName) {		
		StdIn.setInput(fileName);
		for (int addr = 0; StdIn.hasNextLine(); addr++) {			
			m.setValue(addr, StdIn.readInt());
		}
	}
	
	/** Initializes the input unit from the given text file.
	 *  It is assumed that the file contains a stream of valid data values,
	 *  each being an integer in the range -999 to 999.
	 *  Each time the computer is instructed to execute a READ command,
	 *  the next line from this file is read and placed in the data register
	 *  (this READ logic is part of the run method implementation).
	 *  Thus, the role of this method is to initialize the file in order to
	 *  enable the execution of subsequent READ commands. */
	public void setInput(String fileName) {	
		StdIn.setInput(fileName);		
	}
	
	/** This method is used for debugging purposes.
	 *  It displays the value of the data register, 
	 *  the value of the program counter,
	 *  and the values of the first and last 10 memory registers. */
	public String toString () {
	    String s = "D = " + dReg.getValue() + "\n" +
	    "PC = " + pc.getValue() + "\n" +
	    "Memory state:" + "\n" + m;  // "m" invokes the toString method of the Memory class.
	    return s;
	}
}
