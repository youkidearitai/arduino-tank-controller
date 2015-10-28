/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.BluetoothChat;

// Import package for TankController control parameter object.
import com.example.android.BluetoothChat.CommandFactory.AccelFactory;
import com.example.android.BluetoothChat.CommandFactory.CommandFactory;
import com.example.android.BluetoothChat.CommandFactory.HoldFactory;
import com.example.android.BluetoothChat.Commands.BrakeButtonChangeParameter;
import com.example.android.BluetoothChat.Commands.Parameter;
import com.example.android.BluetoothChat.Commands.StopButtonChangeParameter;
import com.example.android.BluetoothChat.Controls.TankControllerFormat;
import com.example.android.BluetoothChat.MoveState.MoveStateContext;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This is the main Activity that displays the current chat session.
 */
@SuppressLint("HandlerLeak")
public class BluetoothChat extends Activity {
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Layout Views
    private ListView mConversationView;
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;
    
    // Add TankController code
    private TankControllerFormat tankController = null;

    private Parameter stopParameter = null;
    private Parameter brakeParameter = null;
    
    private CommandFactory leftAccelFactory = null;
    private CommandFactory rightAccelFactory = null;
    private CommandFactory leftPureFactory = null;
    private CommandFactory rightPureFactory = null;

    private MoveStateContext recorder = null;

    private SpeechRecognizer recognizer = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");

        // Set up the window layout
        setContentView(R.layout.main);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        // Create a factory object for every seekBar.
        // シークバーごとにファクトリオブジェクトを作成する
        leftAccelFactory = new AccelFactory();
        rightAccelFactory = new AccelFactory();

        stopParameter = new StopButtonChangeParameter();
        brakeParameter = new BrakeButtonChangeParameter();
        
        leftPureFactory = new HoldFactory();
        rightPureFactory = new HoldFactory();
        
        tankController = new TankControllerFormat();
        recorder = new MoveStateContext();

        // SpeechRecognizer
        recognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        recognizer.setRecognitionListener(new RecognitionListener() {

            @Override
            public void onReadyForSpeech(Bundle params) {
                Button button = (Button)findViewById(R.id.voice);
                button.setText("Tell me...");
            }

            @Override
            public void onBeginningOfSpeech() {
                Button button = (Button)findViewById(R.id.voice);
                button.setText("Hearing...");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                Button button = (Button)findViewById(R.id.voice);
                button.setText("VOICE");
            }

            @Override
            public void onError(int error) {
                Log.d(TAG, "ERROR:" + String.valueOf(error));
            }

            @Override
            public void onResults(Bundle results) {
                Button button = (Button)findViewById(R.id.voice);

                ArrayList<String> stringArrayList = results.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                );

                String str = "";

                try {
                    str = stringArrayList.get(0);
                } catch (NullPointerException e) {
                    str = "";
                }
                button.setText(str);

                Log.d("SpeechRecognizer", str);

                tankController.setCompleteSend();
                recorder.startRecognizer(str);
                recorder.startPlay();

                final Handler h = new Handler();

                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!recorder.isRecorder()) {
                            Log.d("MoveStateContext", "stop recognizer");
                            Button record = (Button) findViewById(R.id.voice);
                            record.setText("VOICE");
                            recorder.endRecord();
                            return;
                        }

                        if (recorder.isPlayCommand()) {
                            Log.d("MoveStateContext", "running recognizer");
                            sendMessage(recorder.play());
                        }
                        h.postDelayed(this, 1);
                    }
                }, 1);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }

        });
    }
    
    private Parameter getChangeParameter(CommandFactory factory) {
    	Switch turboSwitch = (Switch) findViewById(R.id.turbo);
    	
    	if (turboSwitch.isChecked()) {
    		return factory.createCommand(CommandFactory.TURBO);
    	}
    	
    	return factory.createCommand(CommandFactory.NORMAL);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Get seekBar ID
        SeekBar leftSeekBar = (SeekBar) findViewById(R.id.leftControl);
        SeekBar rightSeekBar = (SeekBar) findViewById(R.id.rightControl);
        
        // Left SeekBar (control cw/ccw)
        leftSeekBar.setOnSeekBarChangeListener(new LeftControlListener());
        // Right SeekBar (control cw/ccw)
        rightSeekBar.setOnSeekBarChangeListener(new RightControlListener());

        // Stop button (set level to 256)
        Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(buttonClicked(stopParameter, stopParameter));

        // Brake button (set level to 0)
        Button brake = (Button) findViewById(R.id.brake);
        brake.setOnClickListener(buttonClicked(brakeParameter, brakeParameter));

        Button record = (Button) findViewById(R.id.record);
        record.setOnClickListener(startRecord());

        Button voice = (Button) findViewById(R.id.voice);
        voice.setOnClickListener(startVoice());
    }

    private View.OnClickListener startVoice() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

                recognizer.startListening(intent);
            }
        };
    }
    
    // Brake and stop button push
    private View.OnClickListener buttonClicked(final Parameter leftParameter, final Parameter rightParameter) {
    	return new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
		        seekControl(leftParameter, rightParameter);
		        sendMessage(tankController.getSendParam());
			}
		};
    }

    // Start Record
    private View.OnClickListener startRecord() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recorder.isRecorder()) {
                    Button record = (Button) findViewById(R.id.record);
                    record.setText("Recording...");
                    recorder.startRecord();
                    return;
                } else {
                    Button record = (Button) findViewById(R.id.record);
                    record.setText("Play");
                    tankController.setCompleteSend();
                    recorder.startPlay();
                }
                final Handler h = new Handler();

                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!recorder.isRecorder()) {
                            Log.d("MoveStateContext", "stop record");
                            Button record = (Button) findViewById(R.id.record);
                            record.setText("Record");
                            recorder.endRecord();
                            return;
                        }

                        if (recorder.isPlayCommand()) {
                            Log.d("MoveStateContext", "run");
                            sendMessage(recorder.play());
                        }
                        h.postDelayed(this, 1);
                    }
                }, 1);
            }
        };
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            recorder.record(message);
            byte[] send = message.getBytes();
            mChatService.write(send);
        }
    }

    private final void setStatus(int resId) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(resId);
    }

    private final void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(subTitle);
    }
    
    // Set parameter from SeekBar
    private void seekControl(Parameter leftParameter, Parameter rightParameter) {
        SeekBar left = (SeekBar) findViewById(R.id.leftControl);
        SeekBar right = (SeekBar) findViewById(R.id.rightControl);

        tankController.setCommandParameter(TankControllerFormat.LEFT, leftParameter);
        tankController.setCommandParameter(TankControllerFormat.RIGHT, rightParameter);

        tankController.control(left.getProgress(), right.getProgress());    	
        
        left.setProgress(tankController.getPureParameterLevel(TankControllerFormat.LEFT));
        right.setProgress(tankController.getPureParameterLevel(TankControllerFormat.RIGHT));
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                    tankController.setCompleteSend(); // reset if re-connect

                    mConversationArrayAdapter.clear();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    setStatus(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    setStatus(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                
                // If you tried new request while sending request, tank controller is resends from value of seekBar.
                // (自信がないから日本語で: 送信中に新たな送信が発生していた場合、シークバーの値を送信し直す)
                boolean isResendParameter = tankController.isResendParameter();
                tankController.setCompleteSend();                
                
                if (isResendParameter) {
                	seekControl(getChangeParameter(leftPureFactory), getChangeParameter(rightPureFactory));
                	BluetoothChat.this.sendMessage(tankController.getSendParam());
                }
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, true);
            }
            break;
        case REQUEST_CONNECT_DEVICE_INSECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, false);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
        case R.id.secure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            return true;
        case R.id.insecure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        return false;
    }

    // Set parameter for Arduino tank
    private abstract class ControlListener implements OnSeekBarChangeListener {
    	protected boolean isControl(boolean fromUser) {
    		return fromUser && tankController.isControl();
    	}
    	
    	protected void setSendParameter(Parameter leftParameter, Parameter rightParameter) {
    		seekControl(leftParameter, rightParameter);
    	}
    	
    	protected abstract Parameter LeftProgressParameter();
    	protected abstract Parameter RightProgressParameter();
    	
    	protected abstract Parameter LeftChangeParameter();
    	protected abstract Parameter RightChangeParameter();
    	
    	@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    		boolean isControl = isControl(fromUser);
            setSendParameter(LeftProgressParameter(), RightProgressParameter());
            
            // 
            if (isControl) {
            	sendMessage(tankController.getSendParam());
            }
        }
    	
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
    	
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			boolean isControl = isControl(true);
			setSendParameter(LeftChangeParameter(), RightChangeParameter());
			
			if (isControl) {
				sendMessage(tankController.getSendParam());
			}
        }
    }
    
    // Left seekBar
    private class LeftControlListener extends ControlListener {

    	@Override
		protected Parameter LeftProgressParameter() {
			// TODO Auto-generated method stub
			return getChangeParameter(leftAccelFactory);
		}

		@Override
		protected Parameter RightProgressParameter() {
			// TODO Auto-generated method stub
			return getChangeParameter(rightPureFactory);
		}

		@Override
		protected Parameter LeftChangeParameter() {
			// TODO Auto-generated method stub
			return stopParameter;
		}

		@Override
		protected Parameter RightChangeParameter() {
			// TODO Auto-generated method stub
			return getChangeParameter(rightPureFactory);
		}
    	
    }
    
    // Right seekBar
    private class RightControlListener extends ControlListener {

		@Override
		protected Parameter LeftProgressParameter() {
			// TODO Auto-generated method stub
			return getChangeParameter(leftPureFactory);
		}

		@Override
		protected Parameter RightProgressParameter() {
			// TODO Auto-generated method stub
			return getChangeParameter(rightAccelFactory);
		}

		@Override
		protected Parameter LeftChangeParameter() {
			// TODO Auto-generated method stub
			return getChangeParameter(leftPureFactory);
		}

		@Override
		protected Parameter RightChangeParameter() {
			// TODO Auto-generated method stub
			return stopParameter;
		}
    	
    }

}
