package com.example.android.BluetoothChat.Interpreters;

import android.util.Log;

/**
 * Created by tekitoh on 15/10/28.
 */
public class CalculateExpressionImpl extends Expression {
    public CalculateExpressionImpl(Expression expression) {
        super(expression);
    }


    @Override
    public void term() {
        Log.d("CalculateExpressionImpl", "CAL");

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.AHEAD) {
            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                "511,511\r\n"
            );
        }

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.BACK) {
            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                "1,1\r\n"
            );
        }

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.LEFT) {
            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                "511,256\r\n"
            );
        }

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.RIGHT) {
            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                "256,511\r\n"
            );
        }

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.STOP) {
            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                "256,256\r\n"
            );
        }
    }
}
