package io.mdp43140.nolauncher;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppChangeReceiver extends BroadcastReceiver {
	@SuppressLint("UnsafeProtectedBroadcastReceiver")
	@Override public void onReceive(Context c, Intent intent) {
		if (MainActivity.self != null){
			MainActivity.self.loadApps();
		} else {
			MainActivity.apps = null;
		}
	}
}