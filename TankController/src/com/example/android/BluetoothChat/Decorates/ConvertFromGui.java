package com.example.android.BluetoothChat.Decorates;

public class ConvertFromGui extends CalcLevel {

	public ConvertFromGui(CalcLevel calcLevel) {
		super(calcLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int calc(int level) {
		return this.calcLevel.calc(level) + 1;
	}

}
