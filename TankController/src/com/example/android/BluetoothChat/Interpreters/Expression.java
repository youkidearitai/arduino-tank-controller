package com.example.android.BluetoothChat.Interpreters;

/**
 * Created by tekitoh on 15/10/27.
 */
abstract public class Expression {
    public Expression expression;

    public Expression(Expression expression) {
        this.expression = expression;
    }

    abstract public void term();
}
