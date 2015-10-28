package com.example.android.BluetoothChat.Interpreters;

/**
 * Created by tekitoh on 15/10/28.
 */
public class TankInterpreter {

    public LexicalAnalyzer lexicalAnalyzer;

    public CodeAnalyzer codeAnalyzer;

    public String program;

    public TankInterpreter(String program) {
        this.program = program;
        this.lexicalAnalyzer = new LexicalAnalyzer(program);
    }
}
