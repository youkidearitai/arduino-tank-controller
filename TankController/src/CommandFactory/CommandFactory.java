package CommandFactory;

import com.example.android.BluetoothChat.Commands.Parameter;

public abstract class CommandFactory {
	protected Parameter normalParameter;
	protected Parameter turboParameter;
	
	public static final int NORMAL = 0;
	public static final int TURBO = 1;
	
	public Parameter createCommand(int type) {
		switch (type) {
		case NORMAL:
			return normalParameter;
		case TURBO:
			return turboParameter;
		default:
			return normalParameter;
		}
	}
}
