package com.ied.projet.aqua;

import android.os.Bundle;

public class PreferenceActivity extends android.preference.PreferenceActivity {
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.pref_layout);
		addPreferencesFromResource(R.xml.preferences);
	}

}
