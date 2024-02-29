import java.util.ArrayList;
import java.util.List;

public class SubCall implements Statement {
    private String name;
    private List<Expression> arguments;

    public SubCall(String name, List<Expression> args) {
        this.name = name;
        this.arguments = args;
    }

    @Override
    public void execute() {
        List<DataValue> argValues = new ArrayList<>();
        for (Expression arg : this.arguments) {
            argValues.add(arg.evaluate());
        }
        Interpreter.executeSubCall(this.name, argValues);
    }

    // Additional methods for parsing the subroutine call can be added here.
}
