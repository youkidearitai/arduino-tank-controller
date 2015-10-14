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

    public void stopRecord() {
        this.startTimestamp = System.currentTimeMillis();
    }

    public boolean isPlayCommand() {
        return this.timestamp.get(this.recorder.size() - 1) < (System.currentTimeMillis() - this.startTimestamp);
    }

    public String play() {
        String record = this.recorder.get(this.recorder.size() - 1);
        this.timestamp.remove(this.timestamp.size() - 1);
        this.recorder.remove(this.recorder.size() - 1);

        return record;
    }

    public boolean isRecorder() {
        return this.recorder.size() > 0;
    }
}
