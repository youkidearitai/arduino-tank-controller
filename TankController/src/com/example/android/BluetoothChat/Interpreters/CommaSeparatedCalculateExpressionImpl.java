package com.example.android.BluetoothChat.Interpreters;

/**
 * Created by tekitoh on 15/11/17.
 */
public class CommaSeparatedCalculateExpressionImpl extends Expression {
    public CommaSeparatedCalculateExpressionImpl(Expression expression) {
        super(expression);
    }

    @Override
    public void term() {
        if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getToken() == CodeAnalyzer.NUM) {
            if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex + 1).getToken() == CodeAnalyzer.COMMA) {
                if (CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex + 2).getToken() == CodeAnalyzer.NUM) {
                    int llevel = (int)CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex).getNumber();
                    int rlevel = (int)CodeAnalyzer.tokens.get(CodeAnalyzer.tokenIndex + 2).getNumber();
                    String level = String.valueOf(llevel) + "," + String.valueOf(rlevel);

                    CodeAnalyzer.moveRecorder.record(CodeAnalyzer.seconds, level + "\r\n");
                }
            }

        }

        this.expression.term();
    }
}
