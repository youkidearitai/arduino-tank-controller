package com.example.android.BluetoothChat.MoveState;

import java.util.ArrayList;

/**
 * Created by tekitoh on 15/10/14.
 */
public class MovePlayer extends MoveState {

    private ArrayList<String> recorder;
    private ArrayList<Long> timestamp;
    private long startTimestamp;

    public MovePlayer(ArrayList<String> recorder, ArrayList<Long> timestamp, long startTimestamp) {
        this.recorder = recorder;
        this.timestamp = timestamp;
        this.startTimestamp = startTimestamp;
    }

    public void record(String data) {}

    @Override
    public String play() {
        String record = this.recorder.get(this.recorder.size() - 1);
        this.timestamp.remove(this.timestamp.size() - 1);
        this.recorder.remove(this.recorder.size() - 1);

        return record;
    }

    @Override
    public boolean isPlayCommand() {
        return this.timestamp.get(this.recorder.size() - 1) <
                (System.currentTimeMillis() - this.startTimestamp);
    }

    @Override
    public boolean isRecorder() {
        return this.recorder.size() > 0;
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
