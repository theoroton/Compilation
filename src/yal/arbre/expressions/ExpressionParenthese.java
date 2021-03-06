package yal.arbre.expressions;

public class ExpressionParenthese extends Expression {

    protected Expression expression;

    public ExpressionParenthese(Expression exp, int n) {
        super(n);
        expression = exp;
    }

    @Override
    public void verifier() {
        expression.verifier();
    }

    @Override
    public String toMIPS() {
        StringBuffer mips = new StringBuffer();
        mips.append(expression.toMIPS());
        return mips.toString();
    }

    @Override
    public String type() {
        return expression.type();
    }
}
