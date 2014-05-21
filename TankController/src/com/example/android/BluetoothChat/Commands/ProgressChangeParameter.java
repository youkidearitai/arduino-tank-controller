package com.example.android.BluetoothChat.Commands;

import com.example.android.BluetoothChat.Decorates.CalcLevel;
import com.example.android.BluetoothChat.Decorates.ConvertFromGui;
import com.example.android.BluetoothChat.Decorates.GetLevel;
import com.example.android.BluetoothChat.Decorates.SetMaxLevel;
import com.example.android.BluetoothChat.Decorates.SetPwmFa130Level;

public class ProgressChangeParameter implements Parameter {
	
	private CalcLevel calcLevel = null;
	
	private int level = 256;

	public ProgressChangeParameter() {
		calcLevel = new GetLevel(null);
		calcLevel = new ConvertFromGui(calcLevel);
		calcLevel = new SetMaxLevel(calcLevel);
		calcLevel = new SetPwmFa130Level(calcLevel);
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return this.calcLevel.calc(level);
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
