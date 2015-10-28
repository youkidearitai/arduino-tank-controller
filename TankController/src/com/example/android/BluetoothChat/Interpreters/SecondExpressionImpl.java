package com.example.android.BluetoothChat.Interpreters;

import android.util.Log;

/**
 * Created by tekitoh on 15/10/28.
 */
public class SecondExpressionImpl extends Expression {
    public SecondExpressionImpl(Expression expression) {
        super(expression);
    }

    @Override
    public void term() {
        this.expression.term();

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.NUM) {
            CodeAnalyzer.seconds += CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getNumber();
            Log.d("SecondExpressionImpl", String.valueOf(CodeAnalyzer.seconds));
            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                "256,256\r\n"
            );
        }
    }
}
