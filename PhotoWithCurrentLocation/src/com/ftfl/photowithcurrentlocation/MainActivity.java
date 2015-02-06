package com.ftfl.photowithcurrentlocation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{
	static File mMediaFile;
	static String mCurrentPhotoPath = "";
	Button mRegister;
	Button mRetrive;
	// Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;

		// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "Places_List";

	private Uri fileUri; // file url to store image


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        mRegister=(Button) findViewById(R.id.btnImage);
        mRetrive=(Button) findViewById(R.id.btnSave);
        
        
        mRegister.setOnClickListener((OnClickListener) this);
        mRetrive.setOnClickListener((OnClickListener) this);
        
        
    }
    
    public void onClick(View v) {
		
		Toast toast = null;
		switch (v.getId()) {

		case R.id.btnImage:
			
			startActivity(new Intent(MainActivity.this,
					LocationActivity.class));
			/*captureImage();
			mCurrentPhotoPath = mMediaFile.getAbsolutePath();
		    break;*/

		case R.id.btnSave:
    
    
}
    }

	private void captureImage() {
			
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

			// start the image capture Intent
			startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
		}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			 if (resultCode == RESULT_OK) {
	                // successfully captured the image
				 Toast.makeText(getApplicationContext(),
							"image captured", Toast.LENGTH_SHORT)
							.show();
			} else if (resultCode == RESULT_CANCELED) {
				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			} else {
				// failed to capture image
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
	 // ------------ Helper Methods ----------------------

	
	// Creating file uri to store image
	 
	public Uri getOutputMediaFileUri(int type) { 
	 
		Uri mUri= Uri.fromFile(getOutputMediaFile(type));
	    return mUri;			
	}
	
	/**
	 * returning image
	 */
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}
		// Create a media file name
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
						Locale.getDefault()).format(new Date());
				
				if (type == MEDIA_TYPE_IMAGE) {
					mMediaFile = new File(mediaStorageDir.getPath() + File.separator
							+ "IMG_" + timeStamp + ".jpg");
				} else {
					return null;
				}
				

				return mMediaFile;
			}
		
	}



		
		
	

