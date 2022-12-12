package io.mdp43140.nolauncher;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class MainActivity extends Activity {
	@SuppressLint("StaticFieldLeak")
	public static MainActivity self;
	public static ArrayList<AppItem> apps;
	private EditText activity_home_search;
	private ListView activity_home_list;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity_home_search = findViewById(R.id.activity_home_search);
		activity_home_search.addTextChangedListener(new TextWatcher() {
			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {search(s.toString());}
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override public void afterTextChanged(Editable s) {}
		});
		activity_home_list = findViewById(R.id.activity_home_list);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
		intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		intentFilter.addDataScheme("package");
		registerReceiver(new AppChangeReceiver(), intentFilter);
	}
	@Override protected void onResume() {
		super.onResume();
		self = this;
		if (apps == null) loadApps();
	}
	@Override protected void onStop() {
		super.onStop();
		activity_home_search.setText("");
		self = null;
	}
	@Override public void onBackPressed() {
		activity_home_search.setText("");
	}

	public void loadApps() {
		apps = new ArrayList<>();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);
		for (ResolveInfo info: pkgAppsList) {
			AppItem app = new AppItem();
			app.name = info.activityInfo.applicationInfo.loadLabel(getPackageManager()).toString();
			app.packageName = info.activityInfo.applicationInfo.packageName;
			app.icon = info.activityInfo.applicationInfo.loadIcon(getPackageManager());
			apps.add(app);
		}
		Collections.sort(apps);
		AppAdapter adapter = new AppAdapter(MainActivity.this, apps);
		activity_home_list.setAdapter(adapter);
	}
	private void search(String text) {
		ArrayList<AppItem> resultApps = new ArrayList<>();
		for (AppItem app: apps) {
			if (app.name.toLowerCase().contains(text.toLowerCase())) {
				resultApps.add(app);
			}
		}
		AppAdapter adapter = new AppAdapter(MainActivity.this, resultApps);
		activity_home_list.setAdapter(adapter);
	}
}