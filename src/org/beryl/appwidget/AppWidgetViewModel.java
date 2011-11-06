package org.beryl.appwidget;

import android.content.Context;
import android.widget.RemoteViews;

public interface AppWidgetViewModel {
	RemoteViews createViews(final Context context);
}
