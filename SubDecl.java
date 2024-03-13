import java.util.List;

public class SubDecl implements Statement {
    private String name;
    private List<Token> parameters;
    private Compound compoundStatement;
public SubDecl(TokenStream tokens) throws Exception {
    // Assume that 'sub' token has already been consumed.
    this.name = tokens.next().toString();

    // Parse parameters inside the parentheses
    if (!tokens.next().toString().equals("(")) {
        throw new Exception("SYNTAX ERROR: Expected '(' after subroutine name");
    }

    this.parameters = new ArrayList<>();
    Token nextToken = tokens.lookAhead();
    while (!nextToken.toString().equals(")")) {
        this.parameters.add(tokens.next());
        nextToken = tokens.lookAhead();
        if (nextToken.toString().equals(",")) {
            tokens.next(); // Consume the comma
            nextToken = tokens.lookAhead();
        } else if (!nextToken.toString().equals(")")) {
            throw new Exception("SYNTAX ERROR: Expected ',' or ')' in subroutine parameters list");
        }
    }
    tokens.next(); // Consume the closing ')'

    // Now parse the compound statement that makes up the subroutine body
    this.compoundStatement = new Compound(tokens);
}

    public SubDecl(String name, List<Token> params, Compound stmt) {
        this.name = name;
        this.parameters = params;
        this.compoundStatement = stmt;
    }

    @Override
    public void execute() {
        Interpreter.executeSubDecl(this.name, this.parameters, this.compoundStatement);
    }

    // Additional methods for parsing the subroutine declaration can be added here.
}
