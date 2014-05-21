package com.example.android.BluetoothChat.Commands;

public interface Parameter {
	public int getLevel();
	public void setLevel(int level);
	public int getPureLevel();
	
	public boolean isLock();
	public String toString();
}
