package com.example.android.BluetoothChat.Interpreters;

import android.util.Log;

/**
 * Created by tekitoh on 15/11/02.
 */
public class ForLoopExpressionImpl extends Expression {
    public int baseAddress = 0;
    public int loopCounter = 0;
    public int loopLimit = 0;

    public ForLoopExpressionImpl(Expression expression) {
        super(expression);
        baseAddress = 0;
        loopCounter = 0;
        loopLimit = 0;
    }

    @Override
    public void term() {
        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.LOOP) {
            TokenStorage numToken = CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex - 1);
            if (numToken.getToken() == CodeAnalyzer.NUM) {
                loopLimit = numToken.getNumber();

                if (loopCounter < loopLimit) {
                    loopCounter++;
                } else {
                    baseAddress = CodeAnalyzer.tokenIndex;
                    loopCounter = 0;
                    
                    if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex + 1).getToken() == CodeAnalyzer.EOL) {
                        CodeAnalyzer.moveRecorder.record(
                                CodeAnalyzer.seconds * 1000,
                                "256,256\r\n"
                        );
                    }
                }

                CodeAnalyzer.tokenIndex = baseAddress;
                Log.d("ForLoop.tokenIndex", String.valueOf(CodeAnalyzer.tokenIndex));
            }
        }
        this.expression.term();
    }
}
