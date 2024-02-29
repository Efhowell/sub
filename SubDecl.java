import java.util.List;

public class SubDecl implements Statement {
    private String name;
    private List<Token> parameters;
    private Compound compoundStatement;

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
