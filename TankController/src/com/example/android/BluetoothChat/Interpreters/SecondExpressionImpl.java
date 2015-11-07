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
        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.NUM) {
            if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex + 1).getToken() == CodeAnalyzer.SEC) {
                CodeAnalyzer.seconds += CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getNumber();
                Log.d("SecondExpressionImpl", String.valueOf(CodeAnalyzer.seconds));
            }
        }
        this.expression.term();
    }
}
