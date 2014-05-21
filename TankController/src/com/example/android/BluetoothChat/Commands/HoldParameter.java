package com.example.android.BluetoothChat.Commands;

import com.example.android.BluetoothChat.Decorates.CalcLevel;
import com.example.android.BluetoothChat.Decorates.GetLevel;
import com.example.android.BluetoothChat.Decorates.SetMaxLevel;
import com.example.android.BluetoothChat.Decorates.SetPwmFa130Level;

public class HoldParameter implements Parameter {
	
	private CalcLevel calcLevel = null;
	private int level = 256;
	
	public HoldParameter() {
		calcLevel = new GetLevel(null);
		calcLevel = new SetMaxLevel(calcLevel);
		calcLevel = new SetPwmFa130Level(calcLevel);
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return calcLevel.calc(level);
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int getPureLevel() {
		// TODO Auto-generated method stub
		return level;
	}

	@Override
	public boolean isLock() {
		// TODO Auto-generated method stub
		return true;
	}

}
