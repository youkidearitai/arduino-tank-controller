package com.example.android.BluetoothChat.MoveState;

/**
 * Created by tekitoh on 15/10/02.
 */
public class MoveStateContext {
    private MoveState state;

    public MoveStateContext() {
        this.state = new MockState();
    }

    public void startRecord() {
        this.state = new MoveRecorder();
    }

    public void startPlay() {
        this.state.stopRecord();
    }

    public void record(String state) {
        this.state.record(state);
    }

    public String play() {
        return this.state.play();
    }

    public void endRecord() {
        this.state = new MockState();
    }

    public boolean isRecorder() {
        return this.state.isRecorder();
    }

    public boolean isPlayCommand() {
        return this.state.isPlayCommand();
    }

}
