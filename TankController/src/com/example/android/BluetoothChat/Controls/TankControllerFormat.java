package com.example.android.BluetoothChat.Controls;

import java.util.ArrayList;
import com.example.android.BluetoothChat.Commands.Parameter;

public class TankControllerFormat {

	private ArrayList<Parameter> leftParams = new ArrayList<Parameter>();
	private ArrayList<Parameter> rightParams = new ArrayList<Parameter>();
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	
	private String separator = ",";
	private String enclosure = "\r\n";
	
	public TankControllerFormat() {
	}
	
	private ArrayList<Parameter> getSideParameter(int side) {
		switch (side) {
		case LEFT:
			return leftParams;
		case RIGHT:
			return rightParams;
		default:
			return null;	
		}
	}
	
	private Parameter nowParameter(int side) {
		ArrayList<Parameter> p = getSideParameter(side);
		return p.get(p.size() - 1);
	}
	
	public void setCommandParameter(int side, Parameter param) {
		getSideParameter(side).add(param);
	}
	
	public int getCommandParameterLevel(int side) {
		return nowParameter(side).getLevel();
	}
	
	public int getPureParameterLevel(int side) {
		return nowParameter(side).getPureLevel();
	}
	
	public void control(int leftLevel, int rightLevel) {
		nowParameter(LEFT).setLevel(leftLevel);
		nowParameter(RIGHT).setLevel(rightLevel);
	}
	
	public String getSendParam() {
		return nowParameter(LEFT).getLevel() + separator + nowParameter(RIGHT).getLevel() + enclosure;
	}
	
	public boolean isControl() {
		return getSideParameter(LEFT).size() == 0 && getSideParameter(RIGHT).size() == 0;
	}
	
	public void setCompleteSend() {
		getSideParameter(LEFT).clear();
		getSideParameter(RIGHT).clear();
	}
	
	public boolean isResendParameter() {
		return getSideParameter(LEFT).size() > 1 || getSideParameter(RIGHT).size() > 1;
	}
	
}
