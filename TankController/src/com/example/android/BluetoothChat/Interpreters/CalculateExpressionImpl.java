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
        Log.d("CalculateExpressionImpl", String.valueOf(CodeAnalyzer.seconds));

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.AHEAD) {
            CodeAnalyzer.parameter.setLevel(511);
            int level = CodeAnalyzer.parameter.getLevel();

            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                String.valueOf(level) + "," + String.valueOf(level) + "\r\n"
            );
        }

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.BACK) {
            CodeAnalyzer.parameter.setLevel(1);
            int level = CodeAnalyzer.parameter.getLevel();

            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                String.valueOf(level) + "," + String.valueOf(level) + "\r\n"
            );
        }

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.LEFT) {
            CodeAnalyzer.parameter.setLevel(511);
            int leftLevel = CodeAnalyzer.parameter.getLevel();
            CodeAnalyzer.parameter.setLevel(256);
            int rightLevel = CodeAnalyzer.parameter.getLevel();

            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                String.valueOf(leftLevel) + "," + String.valueOf(rightLevel) + "\r\n"
            );
        }

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.RIGHT) {
            CodeAnalyzer.parameter.setLevel(256);
            int leftLevel = CodeAnalyzer.parameter.getLevel();
            CodeAnalyzer.parameter.setLevel(511);
            int rightLevel = CodeAnalyzer.parameter.getLevel();


            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                String.valueOf(leftLevel) + "," + String.valueOf(rightLevel) + "\r\n"
            );
        }

        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.STOP) {
            CodeAnalyzer.parameter.setLevel(256);
            int leftLevel = CodeAnalyzer.parameter.getLevel();
            CodeAnalyzer.parameter.setLevel(256);
            int rightLevel = CodeAnalyzer.parameter.getLevel();

            CodeAnalyzer.moveRecorder.record(
                CodeAnalyzer.seconds * 1000,
                String.valueOf(leftLevel) + "," + String.valueOf(rightLevel) + "\r\n"
            );
        }
    }
}
