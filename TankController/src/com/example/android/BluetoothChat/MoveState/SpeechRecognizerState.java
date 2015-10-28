package com.example.android.BluetoothChat.MoveState;

import android.speech.SpeechRecognizer;
import android.util.Log;

import com.example.android.BluetoothChat.Interpreters.CodeAnalyzer;
import com.example.android.BluetoothChat.Interpreters.LexicalAnalyzer;

import java.util.ArrayList;

/**
 * Created by tekitoh on 15/10/28.
 */
public class SpeechRecognizerState extends MoveState {

    private ArrayList<String> recorder;
    private ArrayList<Long> timestamp;
    private long startTimestamp;

    public String program;
    public LexicalAnalyzer lexicalAnalyzer;
    public CodeAnalyzer codeAnalyzer;

    public MoveRecorder moveRecorder;

    public SpeechRecognizerState(String program) {
        Log.d("SpeechRecognizerState", program);
        this.program = program;
        this.lexicalAnalyzer = new LexicalAnalyzer(program);
        this.lexicalAnalyzer.lexString();

        this.lexicalAnalyzer.getTokens();
        this.codeAnalyzer = new CodeAnalyzer(this.lexicalAnalyzer.getTokens());
        this.codeAnalyzer.analyze();
        this.moveRecorder = this.codeAnalyzer.getMoveRecorder();

        this.recorder = this.moveRecorder.getRecorder();
        this.timestamp = this.moveRecorder.getTimestamp();
        this.startTimestamp = this.moveRecorder.getStarttimestamp();
    }

    @Override
    public void record(String state) {
    }

    @Override
    public String play() {
        return null;
    }

    public void stopRecord() {
        this.startTimestamp = System.currentTimeMillis();
    }

    @Override
    public boolean isRecorder() {
        return true;
    }

    @Override
    public ArrayList<String> getRecorder() {
        return recorder;
    }

    @Override
    public ArrayList<Long> getTimestamp() {
        return timestamp;
    }

    @Override
    public long getStarttimestamp() {
        return startTimestamp;
    }
}
