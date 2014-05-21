package com.example.android.BluetoothChat.Decorates;

public class SetMaxLevel extends CalcLevel {

	public SetMaxLevel(CalcLevel calcLevel) {
		super(calcLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int calc(int level) {
		level = this.calcLevel.calc(level);
		
		if (level >= 511) {
			level = 511;
		}
		
		if (level <= 1) {
			level = 1;
		}
		
		return level;
	}

}
