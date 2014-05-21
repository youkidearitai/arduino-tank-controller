package com.example.android.BluetoothChat.CommandFactory;

import com.example.android.BluetoothChat.Commands.ProgressChangeParameter;
import com.example.android.BluetoothChat.Commands.TurboChangeParameter;

public class AccelFactory extends CommandFactory {

	public AccelFactory() {
		normalParameter = new ProgressChangeParameter();
		turboParameter = new TurboChangeParameter();
	}

}
