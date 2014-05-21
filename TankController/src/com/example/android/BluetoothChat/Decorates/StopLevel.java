package com.example.android.BluetoothChat.Decorates;

public class StopLevel extends CalcLevel {

	public StopLevel(CalcLevel calcLevel) {
		super(calcLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int calc(int level) {
		return 256;
	}

}
