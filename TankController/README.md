## Tank Controller

Arduinoを使って、ラジコン作りました。
このプログラムはそのラジコンをAndroidから制御するためのプログラムです。

このソースコードは、SDKサンプルのBluetoothChatを使用しています
BluetoothChat.javaに操縦用のシークバーや送信のタイミングを支持するための処理を追加しているのと、
BluetoothChatService.javaにBluetooth SPP用のUUIDに変更と、受信時の変更を加えています。

詳しくはこちらまで。<http://tekitoh-memdhoi.info/views/609>