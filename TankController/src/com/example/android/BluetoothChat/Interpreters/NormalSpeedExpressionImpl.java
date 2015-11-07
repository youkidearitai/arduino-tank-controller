package com.example.android.BluetoothChat.Interpreters;

import com.example.android.BluetoothChat.Commands.HoldParameter;

/**
 * Created by tekitoh on 15/11/07.
 */
public class NormalSpeedExpressionImpl extends Expression {
    public NormalSpeedExpressionImpl(Expression expression) {
        super(expression);
    }

    @Override
    public void term() {
        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.NRML) {
            CodeAnalyzer.parameter = CodeAnalyzer.normalParameter;
        }
        this.expression.term();
    }
}
