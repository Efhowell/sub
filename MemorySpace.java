import java.util.Stack;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class MemorySpace {
    static Map<String, Subroutine> subroutines = new HashMap<>();

    static class Subroutine {
        List<Token> parameters;
        Compound statement;

        Subroutine(List<Token> parameters, Compound statement) {
            this.parameters = parameters;
            this.statement = statement;
        }
    }

    private Stack<Scope> stackSegment;
    private ArrayList<DataValue> heapSegment;

    public MemorySpace() {
        this.stackSegment = new Stack<Scope>();
        this.stackSegment.push(new Scope(null));
        this.heapSegment = new ArrayList<DataValue>();
    }

    public void beginNewScope() {
        this.stackSegment.push(new Scope(this.stackSegment.peek()));
    }

    public void endCurrentScope() {
        this.stackSegment.pop();
    }

    public void declareVariable(Token variable) {
        this.stackSegment.peek().storeInScope(variable, -1);
    }

    public boolean isDeclared(Token variable) {
        return (this.findScopeinStack(variable) != null);
    }

    public void storeValue(Token variable, DataValue val)  {
        Scope found = this.findScopeinStack(variable);
        int addr = this.findValueInHeap(val);
        found.storeInScope(variable, addr);
    }

    public DataValue lookupValue(Token variable) {
        Scope found = this.findScopeinStack(variable);
        return this.heapSegment.get(found.lookupInScope(variable));
    }

    private int findValueInHeap(DataValue val) {
        int addr = this.heapSegment.indexOf(val);
        if (addr == -1) {
            addr = this.heapSegment.size();
            this.heapSegment.add(val);
        }
        return addr;
    }

    private Scope findScopeinStack(Token variable) {
        Scope stepper = this.stackSegment.peek();
        while (stepper != null && stepper.lookupInScope(variable) == null) {
            stepper = stepper.getParentScope();
        }
        return stepper;
    }
}

