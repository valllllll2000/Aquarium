package com.ied.projet.aqua;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GLSurfaceView view = new GLSurfaceView(this);
		view.setRenderer(new FishRenderer(getApplicationContext()));
        view.getHolder().setFormat(PixelFormat.RGBA_8888); 
		setContentView(view);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;		
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch (item.getItemId()) {
		case R.id.menu_settings:
			 Intent settingsActivity = new Intent(getApplicationContext(),
                     PreferenceActivity.class);
			 startActivity(settingsActivity);
			return true;
		}
		return false;
		
	}
}
