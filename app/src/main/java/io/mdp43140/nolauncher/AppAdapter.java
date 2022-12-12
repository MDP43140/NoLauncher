package io.mdp43140.nolauncher;
import java.util.ArrayList;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<AppItem> apps;
	AppAdapter(Activity activity, ArrayList<AppItem> apps){
		this.activity = activity;
		this.apps = apps;
	}
	@Override public int getCount(){return apps.size();}
	@Override public Object getItem(int position){return apps.get(position);}
	@Override public long getItemId(int position){return position;}
	@Override public View getView(int position, View view, ViewGroup parent){
		final ViewHolder holder;
		if (view == null){
			view = activity.getLayoutInflater().inflate(R.layout.item_app, null);
			holder = new ViewHolder();
			holder.name = view.findViewById(R.id.item_app_name);
			holder.icon = view.findViewById(R.id.item_app_icon);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		final AppItem app = (AppItem) getItem(position);
		holder.icon.setImageDrawable(app.icon);
		view.setOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				activity.startActivity(activity.getPackageManager().getLaunchIntentForPackage(app.packageName));
			}
		});
		holder.name.setText(app.name);
		return view;
	}
	public class ViewHolder {
		ImageView icon;
		TextView name;
	}
}