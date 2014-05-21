package CommandFactory;

import com.example.android.BluetoothChat.Commands.HoldParameter;
import com.example.android.BluetoothChat.Commands.HoldTurboParameter;

public class HoldFactory extends CommandFactory {
	
	public HoldFactory() {
		normalParameter = new HoldParameter();
		turboParameter = new HoldTurboParameter();
	}

}
