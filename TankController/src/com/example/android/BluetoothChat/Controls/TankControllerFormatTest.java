package com.example.android.BluetoothChat.Controls;


import com.example.android.BluetoothChat.CommandFactory.AccelFactory;
import com.example.android.BluetoothChat.CommandFactory.CommandFactory;
import com.example.android.BluetoothChat.CommandFactory.HoldFactory;
import com.example.android.BluetoothChat.Commands.StopButtonChangeParameter;

import junit.framework.TestCase;

public class TankControllerFormatTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testControl() {
		CommandFactory commandFactory = new AccelFactory();
		CommandFactory pureFactory = new HoldFactory();
		
		TankControllerFormat tankController = new TankControllerFormat();
		TankControllerFormatTest.assertTrue(tankController.isControl());

		tankController.setCommandParameter(TankControllerFormat.LEFT, commandFactory.createCommand(CommandFactory.NORMAL));
		tankController.setCommandParameter(TankControllerFormat.RIGHT, commandFactory.createCommand(CommandFactory.NORMAL));
		
		TankControllerFormatTest.assertFalse(tankController.isControl());

		tankController.control(444, 444);
		String actual = tankController.getSendParam();
		String expected = "382,382\r\n";
		
		TankControllerFormatTest.assertTrue(tankController.isControl());
		
		TankControllerFormatTest.assertEquals(expected, actual);
		
		tankController.setCommandParameter(TankControllerFormat.LEFT, commandFactory.createCommand(CommandFactory.NORMAL));
		tankController.setCommandParameter(TankControllerFormat.RIGHT, new StopButtonChangeParameter());
		tankController.control(444, 256);
		actual = tankController.getSendParam();
		expected = "382,256\r\n";
		
		tankController.setCommandParameter(TankControllerFormat.LEFT, pureFactory.createCommand(CommandFactory.NORMAL));
		tankController.setCommandParameter(TankControllerFormat.RIGHT, new StopButtonChangeParameter());
		tankController.control(444, 256);
		actual = tankController.getSendParam();
		expected = "381,256\r\n";
		TankControllerFormatTest.assertEquals(expected, actual);
		
		tankController.setCommandParameter(TankControllerFormat.LEFT, commandFactory.createCommand(CommandFactory.NORMAL));
		tankController.setCommandParameter(TankControllerFormat.RIGHT, new StopButtonChangeParameter());
		tankController.control(5111, 256);
		actual = tankController.getSendParam();
		expected = "426,256\r\n";
		
		TankControllerFormatTest.assertEquals(expected, actual);
		
		tankController.setCommandParameter(TankControllerFormat.LEFT, commandFactory.createCommand(CommandFactory.NORMAL));
		tankController.setCommandParameter(TankControllerFormat.RIGHT, new StopButtonChangeParameter());
		tankController.control(0, 256);
		actual = tankController.getSendParam();
		expected = "86,256\r\n";
		
		TankControllerFormatTest.assertEquals(expected, actual);
		
		TankControllerFormatTest.assertTrue(tankController.isControl());
	}

}
