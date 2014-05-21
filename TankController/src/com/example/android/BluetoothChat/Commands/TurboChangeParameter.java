package com.example.android.BluetoothChat.Commands;

import com.example.android.BluetoothChat.Decorates.CalcLevel;
import com.example.android.BluetoothChat.Decorates.ConvertFromGui;
import com.example.android.BluetoothChat.Decorates.GetLevel;
import com.example.android.BluetoothChat.Decorates.SetMaxLevel;

public class TurboChangeParameter implements Parameter {
	
	private CalcLevel calcLevel = null;
	private int level = 256;
	
	public TurboChangeParameter() {
		calcLevel = new GetLevel(null);
		calcLevel = new ConvertFromGui(calcLevel);
		calcLevel = new SetMaxLevel(calcLevel);
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return this.calcLevel.calc(level);
	}

	@Override
	public void setLevel(int level) {
		// TODO Auto-generated method stub
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
