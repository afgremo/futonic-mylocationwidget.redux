package com.futonredemption.mlcomp;

import com.futonredemption.mylocation.Constants;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

public class ApplicationBackupAgent extends BackupAgentHelper {

	@Override
    public void onCreate() {
		super.onCreate();
		SharedPreferencesBackupHelper backupHelper = new SharedPreferencesBackupHelper(this, Constants.Preferences.ApplicationKey);
		addHelper(Constants.Preferences.BackupAgentKey, backupHelper);
		// http://developer.android.com/guide/topics/data/backup.html#BackupAgentHelper
	}
}
