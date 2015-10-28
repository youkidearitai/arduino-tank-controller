package com.example.android.BluetoothChat.Interpreters;

import com.example.android.BluetoothChat.MoveState.MoveRecorder;

import java.util.ArrayList;

/**
 * Created by tekitoh on 15/10/27.
 */
public class CodeAnalyzer {
    public static ArrayList<TokenStorage> tokens;
    public static int tokenIndex = 0;

    public static final int WORD  = 1;
    public static final int NUM   = 2;
    public static final int AHEAD = 3;
    public static final int BACK  = 4;
    public static final int LEFT  = 5;
    public static final int RIGHT = 6;
    public static final int STOP  = 7;
    public static final int BRAKE = 8;

    public static MoveRecorder moveRecorder = new MoveRecorder();
    public static int seconds = 1;

    public Expression expression;

    public CodeAnalyzer(ArrayList<TokenStorage> tokens) {
        moveRecorder = new MoveRecorder();
        seconds = 1;

        CodeAnalyzer.tokens = tokens;
        this.expression = new CalculateExpressionImpl(null);
        this.expression = new SecondExpressionImpl(this.expression);
    }

    public void analyze() {
        int size = CodeAnalyzer.tokens.size();
        for (CodeAnalyzer.tokenIndex = 0; CodeAnalyzer.tokenIndex < size; CodeAnalyzer.tokenIndex++) {
            this.expression.term();
            CodeAnalyzer.tokenIndex++;
        }
        moveRecorder.stopRecord();
    }

    public MoveRecorder getMoveRecorder() {
        return CodeAnalyzer.moveRecorder;
    }
}
