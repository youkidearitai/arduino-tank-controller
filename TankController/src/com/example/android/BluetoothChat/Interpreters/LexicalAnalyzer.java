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
    public static final int WORD  = 1;
    public static final int NUM   = 2;
    public static final int AHEAD = 3;
    public static final int BACK  = 4;
    public static final int LEFT  = 5;
    public static final int RIGHT = 6;
    public static final int STOP  = 7;
    public static final int BRAKE = 8;

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
            if (word.equals("前")) {
                this.tokens.add(this.position, new TokenStorage(AHEAD));
                continue;
            }

            if (word.equals("後")) {
                this.tokens.add(this.position, new TokenStorage(BACK));
                continue;
            }

            if (word.equals("左")) {
                this.tokens.add(this.position, new TokenStorage(LEFT));
                continue;
            }

            if (word.equals("右")) {
                this.tokens.add(this.position, new TokenStorage(RIGHT));
                continue;
            }

            if (word.equals("止")) {
                this.tokens.add(this.position, new TokenStorage(STOP));
                continue;
            }

            if (this.isint.matcher(word).matches()) {
                Log.d("LexicalAnalyzerInt", word);
                this.tokens.add(
                    this.position, new TokenStorage(NUM, Integer.parseInt(word))
                );
                continue;
            }

            if (word.equals("一")) {
                this.tokens.add(
                    this.position, new TokenStorage(NUM, 1)
                );
                continue;
            }

            if (word.equals("二")) {
                this.tokens.add(
                    this.position, new TokenStorage(NUM, 2)
                );
                continue;
            }

            if (word.equals("三")) {
                this.tokens.add(
                    this.position, new TokenStorage(NUM, 3)
                );
                continue;
            }

            if (word.equals("四")) {
                this.tokens.add(
                    this.position, new TokenStorage(NUM, 4)
                );
                continue;
            }

            if (word.equals("五")) {
                this.tokens.add(
                    this.position, new TokenStorage(NUM, 5)
                );
                continue;
            }

            if (word.equals("六")) {
                this.tokens.add(
                    this.position, new TokenStorage(NUM, 6)
                );
                continue;
            }

            if (word.equals("七")) {
                this.tokens.add(
                    this.position, new TokenStorage(NUM, 7)
                );
                continue;
            }

            if (word.equals("八")) {
                this.tokens.add(
                    this.position, new TokenStorage(NUM, 8)
                );
                continue;
            }

            if (word.equals("九")) {
                this.tokens.add(
                    this.position, new TokenStorage(NUM, 9)
                );
                continue;
            }

            this.tokens.add(this.position, new TokenStorage(WORD));
        }
    }

    public ArrayList<TokenStorage> getTokens() {
        return tokens;
    }
}
