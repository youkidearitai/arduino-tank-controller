package com.example.android.BluetoothChat.Decorates;

public abstract class DecoratorLevel extends CalcLevel {

	public DecoratorLevel(CalcLevel calcLevel) {
		super(calcLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract int calc(int level);

}
