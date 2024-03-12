import java.util.Scanner;
import java.util.List; // Import statement for List


/**
 * Main method for the interactive SILLY Interpreter. 
 *   @author Dave Reed 
 *   @version 12/22/23
 */
public class Interpreter {

    // Method to execute a subroutine declaration
    public static void executeSubDecl(String name, List<Token> params, Compound stmt) {
        if (MemorySpace.subroutines.containsKey(name)) {
            throw new RuntimeException("Subroutine already declared: " + name);
        }
        MemorySpace.subroutines.put(name, new MemorySpace.Subroutine(params, stmt));
    }

    // Method to execute a subroutine call
    public static void executeSubCall(String name, List<DataValue> argValues) {
        if (!MemorySpace.subroutines.containsKey(name)) {
            throw new RuntimeException("Subroutine not found: " + name);
        }

        MemorySpace.Subroutine subroutine = MemorySpace.subroutines.get(name);
        if (subroutine.parameters.size() != argValues.size()) {
            throw a RuntimeException("Incorrect number of arguments for subroutine: " + name);
        }

        // Create a new scope for the subroutine
        MEMORY.beginNewScope();
        try {
            // Initialize parameters with provided arguments
            for (int i = 0; i < argValues.size(); i++) {
                MEMORY.storeValue(subroutine.parameters.get(i), argValues.get(i));
            }

            // Execute the subroutine's compound statement
            subroutine.statement.execute();
        } finally {
            // Ensure the scope is ended after execution
            MEMORY.endCurrentScope();
        }
    }

    public static MemorySpace MEMORY = new MemorySpace();
    
    public static void main(String[] args) throws Exception {   
        System.out.print("Enter the program file name or hit RETURN for interactive: ");       
        Scanner input = new Scanner(System.in);
        String response = input.nextLine().strip();
        
        TokenStream inStream = new TokenStream();
        if (!response.equals("")) {
            inStream = new TokenStream(response);
        }      
        
        while (response.equals("") || inStream.hasNext()) {
            System.out.print(">>> ");
            Statement stmt = Statement.getStatement(inStream);
            if (!response.equals("")) System.out.println(stmt);
    		try {
    			stmt.execute();
    		}
    		catch (Exception e) {
    			System.out.println(e);
    		}
        } 
        input.close();
    }
}
