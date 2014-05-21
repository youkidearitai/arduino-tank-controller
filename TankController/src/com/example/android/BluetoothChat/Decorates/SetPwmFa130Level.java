package com.example.android.BluetoothChat.Decorates;

public class SetPwmFa130Level extends CalcLevel {

	public SetPwmFa130Level(CalcLevel calcLevel) {
		super(calcLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int calc(int level) {
		return Math.round((float)((calcLevel.calc(level) - 256) * (3 / 4.5))) + 256;
	}

}
