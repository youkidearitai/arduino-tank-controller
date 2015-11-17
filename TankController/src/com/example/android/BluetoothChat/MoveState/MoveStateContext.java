package com.example.android.BluetoothChat.MoveState;

import java.util.ArrayList;

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
        this.state = new MovePlayer(
                this.state.getRecorder(),
                this.state.getTimestamp(),
                this.state.getStarttimestamp()
        );
    }

    public String reverseEngineeringCode() {
        ArrayList<String> recorder = this.state.getRecorder();
        ArrayList<Long> timestamp = this.state.getTimestamp();
        String separator = "を";
        String code = "";
        long betweentime;

        for (int i = (recorder.size() - 1); 0 < i; i--) {
            betweentime = timestamp.get(i - 1) - timestamp.get(i);
            code += recorder.get(i) + separator + String.valueOf((betweentime / 1000.0)) + "秒";
        }

        return code;
    }

    public void startRecognizer(String program) {
        this.state = new SpeechRecognizerState(program);
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
