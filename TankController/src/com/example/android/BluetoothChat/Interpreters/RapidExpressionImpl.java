package com.example.android.BluetoothChat.Interpreters;

/**
 * Created by tekitoh on 15/11/07.
 */
public class RapidExpressionImpl extends Expression {
    public RapidExpressionImpl(Expression expression) {
        super(expression);
    }

    @Override
    public void term() {
        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.RAPID) {
            CodeAnalyzer.parameter = CodeAnalyzer.rapidParameter;
        }

        this.expression.term();
    }
}
