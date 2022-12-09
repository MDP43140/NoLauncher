/**
 * NoLauncher
 * Created by Saranomy on 2020-04-29.
 * Under Apache License Version 2.0, http://www.apache.org/licenses/
 */
package io.mdp43140.nolauncher;

import android.graphics.drawable.Drawable;

public class AppItem implements Comparable<AppItem> {
    String name, packageName;
    Drawable icon;

    @Override
    public int compareTo(AppItem another) {
        return name.compareToIgnoreCase(another.name);
    }
}