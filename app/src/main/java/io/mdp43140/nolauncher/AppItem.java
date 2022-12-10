package io.mdp43140.nolauncher;
import android.graphics.drawable.Drawable;

public class AppItem implements Comparable<AppItem> {
	String name, packageName;
	Drawable icon;
	@Override public int compareTo(AppItem another) {
		return name.compareToIgnoreCase(another.name);
	}
}