package com.futonredemption.mylocation.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.R;
import com.futonredemption.mylocation.StartActivityButtonAction;
import com.futonredemption.mylocation.StartServiceButtonAction;

public class LocationCardActivity extends Activity {

	public static Intent getActivityIntent(final Context context, final MyLocationBundle bundle) {
		Intent intent = new Intent(context, LocationCardActivity.class);
		intent.putExtra("locationBundle", bundle);
		return intent;
	}

	private MyLocationBundle locationBundle;
	
	private ImageView StaticMapImageView;
	private TextView TitleTextView;
	private TextView DescriptionTextView;
	private ImageButton RefreshLocationImageButton;
	private ImageButton MyLocationActivityImageButton;
	private ImageButton SettingsImageButton;
	private ImageButton ShareImageButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationcard);
        setViews();
        handleIntent(getIntent());
    }
	
	private void handleIntent(Intent intent) {
		if(intent != null) {
			MyLocationBundle bundle = intent.getParcelableExtra("locationBundle");
			setLocationBundle(bundle);
		}
	}
	
	
	private void setLocationBundle(MyLocationBundle bundle) {
		locationBundle = bundle;
		updateViews();
	}

	private void setViews() {
		StaticMapImageView = (ImageView)findViewById(R.id.StaticMapImageView);
		TitleTextView = (TextView)findViewById(R.id.TitleTextView);
		DescriptionTextView = (TextView)findViewById(R.id.DescriptionTextView);
		RefreshLocationImageButton = (ImageButton)findViewById(R.id.RefreshLocationImageButton);
		MyLocationActivityImageButton = (ImageButton)findViewById(R.id.MyLocationActivityImageButton);
		SettingsImageButton = (ImageButton)findViewById(R.id.SettingsImageButton);
		ShareImageButton = (ImageButton)findViewById(R.id.ShareImageButton);
	}
	
	private void updateViews() {
		
		// TODO: This is being lazy.
		MyLocationRetrievalState state = new MyLocationRetrievalState(locationBundle);
		state.seal();
		DataToViewModelAdapter adapter = new DataToViewModelAdapter(this, state);
		
		// TODO: Load bitmap in a separate thread.
		StaticMapImageView.setImageBitmap(adapter.getSmallStaticMap());
		TitleTextView.setText(adapter.getTitle());
		DescriptionTextView.setText(adapter.getDescription());
		// TODO: Some of these intents aren't proper for the button presses.
		RefreshLocationImageButton.setOnClickListener(new StartServiceButtonAction(adapter.getRefreshAction()));
		MyLocationActivityImageButton.setOnClickListener(new StartServiceButtonAction(adapter.getOpenLocationSettingsAction()));
		SettingsImageButton.setOnClickListener(new StartServiceButtonAction(adapter.getOpenLocationSettingsAction()));
		ShareImageButton.setOnClickListener(new StartActivityButtonAction(adapter.getShareLocationAction()));
	}
}
