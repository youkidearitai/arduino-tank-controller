package com.example.android.BluetoothChat.Interpreters;

/**
 * Created by tekitoh on 15/10/28.
 */
public class TokenStorage {
    public int token;
    public double number;

    public TokenStorage(int token) {
        this.token = token;
    }

    public TokenStorage(int token, double number) {
        this.token = token;
        this.number = number;
    }

    public int getToken() {
        return token;
    }

    public double getNumber() {
        return number;
    }
}
