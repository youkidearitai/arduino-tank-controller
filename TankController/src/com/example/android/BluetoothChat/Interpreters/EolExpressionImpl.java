package com.example.android.BluetoothChat.Interpreters;

/**
 * Created by tekitoh on 15/11/02.
 */
public class EolExpressionImpl extends Expression {
    public EolExpressionImpl(Expression expression) {
        super(expression);
    }

    @Override
    public void term() {
        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.EOL) {
            CodeAnalyzer.moveRecorder.record(
                    CodeAnalyzer.seconds * 1000,
                    "256,256\r\n"
            );
        }
        this.expression.term();
    }
}
