package com.example.android.BluetoothChat.Commands;

import com.example.android.BluetoothChat.Decorates.CalcLevel;
import com.example.android.BluetoothChat.Decorates.StopLevel;

public class StopButtonChangeParameter implements Parameter {
	
	private CalcLevel calcLevel = null;
	
	public StopButtonChangeParameter() {
		this.calcLevel = new StopLevel(null);
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
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
