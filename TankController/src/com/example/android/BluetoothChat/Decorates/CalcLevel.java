package com.example.android.BluetoothChat.Decorates;

public abstract class CalcLevel {
	protected CalcLevel calcLevel = null;
	
	public CalcLevel(CalcLevel calcLevel) {
		this.calcLevel = calcLevel;
	}
	public abstract int calc(int level);
}
