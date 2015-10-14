package com.example.android.BluetoothChat.MoveState;

import java.util.ArrayList;

/**
 * Created by tekitoh on 15/10/02.
 */
abstract public class MoveState {

    abstract public void record(String state);

    abstract public String play();

    public boolean isPlayCommand() {
        return false;
    }

    public void stopRecord() {}

    public boolean isRecorder() {
        return false;
    }

    public ArrayList<String>  getRecorder() {
        return null;
    }

    public ArrayList<Long> getTimestamp() {
        return null;
    }

    public long getStarttimestamp() {
        return -1;
    }
}
