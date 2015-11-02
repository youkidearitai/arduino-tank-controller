package com.example.android.BluetoothChat.MoveState;

import java.util.ArrayList;
import java.lang.System;
import android.util.Log;

/**
 * Created by tekitoh on 15/10/02.
 */
public class MoveRecorder extends MoveState {

    private ArrayList<String> recorder;
    private ArrayList<Long> timestamp;

    private long startTimestamp;

    public MoveRecorder() {
        this.recorder = new ArrayList<String>();
        this.timestamp = new ArrayList<Long>();
        this.startTimestamp = System.currentTimeMillis();
    }

    public void record(String state) {
        this.recorder.add(0, state);
        this.timestamp.add(0, (System.currentTimeMillis() - this.startTimestamp));
    }

    public void record(long second, String state) {
        Log.d("MoveRecorder.record", String.valueOf(second) + " " + state);
        this.recorder.add(0, state);
        this.timestamp.add(0, second);
    }

    public void stopRecord() {
        this.startTimestamp = System.currentTimeMillis();
    }

    public String play() {
        return null;
    }

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
