import java.util.ArrayList;
import java.util.List;

public class SubCall implements Statement {
    private String name;
    private List<Expression> arguments;
public SubCall(TokenStream tokens) throws Exception {
    this.name = tokens.next().toString();

    if (!tokens.next().toString().equals("(")) {
        throw new Exception("SYNTAX ERROR: Expected '(' after subroutine name in call");
    }

    this.arguments = new ArrayList<>();
    Token nextToken = tokens.lookAhead();
    while (!nextToken.toString().equals(")")) {
        this.arguments.add(new Expression(tokens));
        nextToken = tokens.lookAhead();
        if (nextToken.toString().equals(",")) {
            tokens.next(); // Consume the comma
            nextToken = tokens.lookAhead();
        } else if (!nextToken.toString().equals(")")) {
            throw new Exception("SYNTAX ERROR: Expected ',' or ')' in subroutine call arguments list");
        }
    }
    tokens.next(); // Consume the closing ')'
}

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
