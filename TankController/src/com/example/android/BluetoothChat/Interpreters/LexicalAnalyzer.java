package com.example.android.BluetoothChat.Interpreters;

import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by tekitoh on 15/10/27.
 */
public class LexicalAnalyzer {

    public String str = null;
    public int position = 0;

    // この構文のトークンの保存先
    public ArrayList<TokenStorage> tokens = null;

    // 字句解析用トークン
    public static final int EOL   = 0;
    public static final int WORD  = 1;
    public static final int NUM   = 2;
    public static final int AHEAD = 3;
    public static final int BACK  = 4;
    public static final int LEFT  = 5;
    public static final int RIGHT = 6;
    public static final int STOP  = 7;
    public static final int BRAKE = 8;
    public static final int LOOP  = 9;
    public static final int SEPL  = 10;
    public static final int SEC   = 11;
    public static final int RAPID = 12;
    public static final int NRML  = 13;
    public static final int COMMA = 14;

    private Pattern isint;

    public LexicalAnalyzer(String str) {
        this.str = str;
        this.position = 0;
        this.tokens = new ArrayList<TokenStorage>();

        this.isint = Pattern.compile("[0-9]");
    }

    public void lexString() {
        int length = this.str.length();

        for (this.position = 0; this.position < length; this.position++) {
            String word = this.str.substring(this.position, this.position + 1);

            Log.d("LexicalAnalyzer", word);

            if (word.equals(",")) {
                this.tokens.add(new TokenStorage(COMMA));
            }

            if (word.equals("を")) {
                this.tokens.add(new TokenStorage(SEPL));
            }

            if (word.equals("速") || word.equals("早")) {
                this.tokens.add(new TokenStorage(RAPID));
            }

            if (word.equals("遅")) {
                this.tokens.add(new TokenStorage(NRML));
            }

            if (word.equals("回")) {
                this.tokens.add(new TokenStorage(LOOP));
            }

            if (word.equals("秒")) {
                this.tokens.add(new TokenStorage(SEC));
            }

            if (word.equals("つ")) {
                this.tokens.add(new TokenStorage(SEC));
            }

            if (word.equals("前")) {
                this.tokens.add(new TokenStorage(AHEAD));
            }

            if (word.equals("後")) {
                this.tokens.add(new TokenStorage(BACK));
            }

            if (word.equals("左")) {
                this.tokens.add(new TokenStorage(LEFT));
            }

            if (word.equals("右")) {
                this.tokens.add(new TokenStorage(RIGHT));
            }

            if (word.equals("止")) {
                this.tokens.add(new TokenStorage(STOP));
            }

            if (this.isint.matcher(word).matches()) {
                boolean dot = true;

                while (this.position < length) {
                    this.position++;
                    String temp = this.str.substring(this.position, this.position + 1);

                    if (this.isint.matcher(temp).matches()) {
                        word += temp;
                    } else if (temp.equals(".") && dot) {
                        word += temp;
                        dot = false;
                    } else {
                        this.position--;
                        break;
                    }
                }

                this.tokens.add(
                    new TokenStorage(
                        NUM,
                        Double.parseDouble(word)
                    )
                );

                Log.d("LexicalAnalyzerInt", word);
            }

            if (word.equals("一")) {
                this.tokens.add(new TokenStorage(NUM, 1.0));
            }

            if (word.equals("二")) {
                this.tokens.add(new TokenStorage(NUM, 2.0));
            }

            if (word.equals("三")) {
                this.tokens.add(new TokenStorage(NUM, 3.0));
            }

            if (word.equals("四")) {
                this.tokens.add(new TokenStorage(NUM, 4.0));
            }

            if (word.equals("五")) {
                this.tokens.add(new TokenStorage(NUM, 5.0));
            }

            if (word.equals("六")) {
                this.tokens.add(new TokenStorage(NUM, 6.0));
            }

            if (word.equals("七")) {
                this.tokens.add(new TokenStorage(NUM, 7.0));
            }

            if (word.equals("八")) {
                this.tokens.add(new TokenStorage(NUM, 8.0));
            }

            if (word.equals("九")) {
                this.tokens.add(new TokenStorage(NUM, 9.0));
            }
        }

        this.tokens.add(new TokenStorage(EOL));
    }

    public ArrayList<TokenStorage> getTokens() {
        return tokens;
    }
}
