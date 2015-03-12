package com.faravy.icare;

import java.util.Timer;
import java.util.TimerTask;

import com.faravy.database.ProfileDataSource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {
	ProfileDataSource mDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mDataSource = new ProfileDataSource(this);
		new Timer().schedule(new TimerTask() {
			public void run() {
				SplashActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						if (mDataSource.isEmpty()) {

							startActivity(new Intent(SplashActivity.this,
									AddProfileActivity.class));

						} else {
							startActivity(new Intent(SplashActivity.this,
									DrawerActivity.class));

						}

						finish();
					}
				});
			}
		}, 2000);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
