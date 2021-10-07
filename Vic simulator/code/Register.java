// package vicSimulator;

/** Represents a register. A register is the basic storage unit of the Vic computer. */
public class Register {
	
	private int value;  // the current value of this register
	
	/** Constructs a register and sets its value to the given value. */
	public Register(int value) {
		this.value = value;
	}

	/** Constructs a register and sets its value to 0. */
	public Register() {
		this(0);
	}
	
	/** Sets the value of this register to the given value. */
	public void setValue(int value) {
		this.value = value;
	}
	
	/** Adds 1 to the value of this register. */
	public void addOne() {
		value = value + 1;
	}

	/** Returns the value of this register, as an int. */
	public int getValue() {
		return value;
	}
	
	/** Returns the value of this register, as a String. */
    public String toString() {
		return "" + value;
	}
}
