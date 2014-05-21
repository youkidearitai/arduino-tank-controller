package com.example.android.BluetoothChat.Commands;

import com.example.android.BluetoothChat.Decorates.BrakeLevel;
import com.example.android.BluetoothChat.Decorates.CalcLevel;

public class BrakeButtonChangeParameter implements Parameter {
	private CalcLevel calcLevel = null;
	
	public BrakeButtonChangeParameter() {
		calcLevel = new BrakeLevel(null);
	}

	@Override
	public int getLevel() {
		return calcLevel.calc(0);
	}

	@Override
	public void setLevel(int level) {
		// Do not anything because this is null object
	}

	@Override
	public int getPureLevel() {
		// TODO Auto-generated method stub
		return 256;
	}

	@Override
	public boolean isLock() {
		// TODO Auto-generated method stub
		return false;
	}

}
