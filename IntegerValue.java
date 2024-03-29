/**
 * Class that represents an integer value.
 *   @author Dave Reed
 *   @version 12/24/23
 */
public class IntegerValue implements DataValue {
    public int value;

    /**
     * Constructs an integer value.
     *   @param num the number being stored
     */
    public IntegerValue(int num) {
        this.value = num;
    }

    /**
     * Accesses the stored integer value.
     *   @return the integer value (as an Object)
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Identifies the actual type of the value.
     *   @return Token.Type.INTEGER
     */
    public DataValue.Type getType() {
        return DataValue.Type.INTEGER_VALUE;
    }

    /**
     * Converts the integer value to a String.
     *   @return a String representation of the integer value
     */
    public String toString() {
        return "" + this.value;
    }

    /**
     * Comparison method for IntegerValues.
     *   @param other the value being compared with
     *   @return negative if <, 0 if ==, positive if >
     */
    public int compareTo(DataValue other) {
         return ((Integer)this.getValue()).compareTo((Integer)other.getValue());
    }
}