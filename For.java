public class For implements Statement {
    private String loopVariable;
    private Expression startExpression, endExpression;
    private Compound loopBody;

    public For(TokenStream input) throws Exception {
        input.next(); // Consume 'for'
        this.loopVariable = input.next().toString(); // Get loop variable name

        if (!input.next().toString().equals("from")) {
            throw new Exception("SYNTAX ERROR: Expected 'from' after loop variable");
        }
        this.startExpression = new Expression(input);

        if (!input.next().toString().equals("to")) {
            throw new Exception("SYNTAX ERROR: Expected 'to' after start expression");
        }
        this.endExpression = new Expression(input);

        this.loopBody = new Compound(input);
    }

    @Override
    public void execute() throws Exception {
        IntegerValue startValue = (IntegerValue) this.startExpression.evaluate();
        IntegerValue endValue = (IntegerValue) this.endExpression.evaluate();

        if (startValue.value <= endValue.value) {
            for (int i = startValue.value; i <= endValue.value; i++) {
                // You need to make sure that the loop variable is declared before you can assign a value
                Token loopVarToken = new Token(loopVariable);
                if (!Interpreter.MEMORY.isDeclared(loopVarToken)) {
                    Interpreter.MEMORY.declareVariable(loopVarToken);
                }
                // Now assign the current value of i to the loop variable
                Interpreter.MEMORY.storeValue(loopVarToken, new IntegerValue(i));

                // Execute the loop body
                this.loopBody.execute();
            }
        }
    }

    @Override
    public String toString() {
        return "for " + loopVariable + " from " + startExpression + " to " + endExpression + " " + loopBody;
    }
}

                // Now assign the current value of i to the loop variable
                Interpreter.MEMORY.storeValue(loopVarToken, new IntegerValue(i));
    
                // Execute the loop body
                this.loopBody.execute();
            }
        }
    }

    @Override
    public String toString() {
        return "for " + loopVariable + " from " + startExpression + " to " + endExpression + " " + loopBody;
    }
}
