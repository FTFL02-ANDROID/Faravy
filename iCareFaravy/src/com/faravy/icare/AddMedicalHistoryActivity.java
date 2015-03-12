package com.faravy.icare;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.faravy.database.MedicalHistoryDataSource;
import com.faravy.modelclass.MedicalHistory;

public class AddMedicalHistoryActivity extends Activity {

	EditText mEtDoctorName;
	EditText mEtDetails;
	TextView mTvDate;
	MedicalHistory mHistory;
	MedicalHistoryDataSource mDataSource;

	String mDoctorName;
	String mDetails;
	String mDate;
	String mPhoto="";
	ImageView mPresCription;
	Button mBtnPhoto;

	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	final Calendar mCalendar = Calendar.getInstance();

	// Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;

	// directory name to store captured images and videos
	public static final String IMAGE_DIRECTORY_NAME = "iCare";

	private Uri fileUri = null; // file url to store image
	static File mediaFile = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medical_history);
		
		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#0080FF"));
		ab.setBackgroundDrawable(colorDrawable);

		mEtDoctorName = (EditText) findViewById(R.id.addDoctorName);
		mEtDetails = (EditText) findViewById(R.id.addDetails);
		mTvDate = (TextView) findViewById(R.id.addDate);
		mPresCription = (ImageView) findViewById(R.id.prescription);
		mBtnPhoto = (Button) findViewById(R.id.btnPrescription);

		mDataSource = new MedicalHistoryDataSource(this);

		mTvDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mYear = mCalendar.get(Calendar.YEAR);
				mMonth = mCalendar.get(Calendar.MONTH);
				mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialog = new DatePickerDialog(
						AddMedicalHistoryActivity.this, mDateSetListener,
						mYear, mMonth, mDay);
				dialog.show();

			}
		});

		mBtnPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				captureImage();
				mPhoto = mediaFile.getAbsolutePath();

			}
		});
	
	}

	private void save() {

		mDoctorName = mEtDoctorName.getText().toString();
		mDetails = mEtDetails.getText().toString();
		mDate = mTvDate.getText().toString();

		mHistory = new MedicalHistory(mDoctorName, mDetails, mDate, mPhoto);

		long inserted = mDataSource.insertData(mHistory);
		if (inserted >= 0) {
			Toast.makeText(this, "Data inserted", Toast.LENGTH_LONG).show();

			Intent mIntent = new Intent(this, DrawerActivity.class);
			startActivity(mIntent);
			exit();

		} else {
			Toast.makeText(this, "Insertion failed", Toast.LENGTH_LONG).show();

		}

	}

	private void exit() {
		super.finishAffinity();
		
	}

	private void captureImage() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// start the image capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on scren orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// successfully captured the image
				// display it in image view
				previewCapturedImage();
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

	private void previewCapturedImage() {
		try {

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			mPresCription.setImageBitmap(bitmap);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * returning image
	 */
	private File getOutputMediaFile(int type) {

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
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");

		} else {
			return null;
		}

		return mediaFile;

	}

	// call DateSetListener for set selected date in edittext field
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			mTvDate.setText(new StringBuilder()
					.append(new DecimalFormat("00").format(mDay)).append("-")
					.append(new DecimalFormat("00").format(mMonth + 1))
					.append("-").append(mYear));
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_diet, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save) {
			save();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
