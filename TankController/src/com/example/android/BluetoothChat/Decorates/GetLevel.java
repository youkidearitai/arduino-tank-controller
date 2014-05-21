package com.example.android.BluetoothChat.Decorates;

public class GetLevel extends DecoratorLevel {

	public GetLevel(CalcLevel calcLevel) {
		super(calcLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int calc(int level) {
		return level;
	}

}
