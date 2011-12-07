package com.futonredemption.mylocation.fragments;

import com.futonredemption.mylocation.R;
import com.futonredemption.mylocation.provider.LocationHistoryContentProvider;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class LocationHistoryFragment extends ListFragment 
	implements LoaderManager.LoaderCallbacks<Cursor>{

    SimpleCursorAdapter adapter;

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(getText(R.string.empty_location_history));

        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2, null,
                new String[] { LocationHistoryContentProvider.LATITUDE, LocationHistoryContentProvider.LONGITUDE },
                new int[] { android.R.id.text1, android.R.id.text2 }, 0);
        setListAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
    }

    // These are the Contacts rows that we will retrieve.
    static final String[] PROJECTION = new String[] {
    	LocationHistoryContentProvider._ID,
    	LocationHistoryContentProvider.LATITUDE,
    	LocationHistoryContentProvider.LONGITUDE
    };
    
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        Uri baseUri = LocationHistoryContentProvider.CONTENT_URI;

        CursorLoader loader = new CursorLoader(getActivity(), baseUri, PROJECTION, 
        		null, null,
        		LocationHistoryContentProvider._ID + " COLLATE LOCALIZED DESC");
        return loader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in. (The framework will take care of closing the
        // old cursor once we return.)
        adapter.swapCursor(data);

        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
