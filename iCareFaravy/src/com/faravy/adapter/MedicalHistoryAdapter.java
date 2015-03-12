package com.faravy.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.faravy.icare.R;
import com.faravy.modelclass.MedicalHistory;

public class MedicalHistoryAdapter extends ArrayAdapter<MedicalHistory> {

	Activity mContext = null;
	MedicalHistory mHistory = null;
	ArrayList<MedicalHistory> mHistoryList = null;
	String mPath = "";
	Bitmap mBitmap = null;

	public MedicalHistoryAdapter(Activity context,
			ArrayList<MedicalHistory> objects) {
		super(context, R.layout.adapter_history, objects);
		this.mContext = context;
		this.mHistoryList = objects;
	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {

		public TextView doctorName = null;
		public TextView detail = null;
		public TextView date = null;
		public ImageView prescription = null;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mHistory = mHistoryList.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.adapter_history, parent, false);
			mVHolder = new ViewHolder();
			mVHolder.doctorName = (TextView) rowView
					.findViewById(R.id.showDoctorName);
			mVHolder.detail = (TextView) rowView.findViewById(R.id.showDetails);
			mVHolder.date = (TextView) rowView.findViewById(R.id.showDate);
			mVHolder.prescription = (ImageView) rowView
					.findViewById(R.id.showPrescription);

			rowView.setTag(mVHolder);

		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.doctorName.setText(mHistory.getmDoctorName().toString());
		mVHolder.detail.setText(mHistory.getmDetails().toString());
		mVHolder.date.setText(mHistory.getmDate().toString());
		mPath = mHistory.getmPhoto();
		previewCapturedImage();
		mVHolder.prescription.setImageBitmap(mBitmap);

		return rowView;
	}

	private void previewCapturedImage() {
		try {
			/*ViewHolder mVHolder = null;
			int targetW =mVHolder.prescription.getWidth();
		    int targetH = mVHolder.prescription.getHeight();*/

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();
			
			/*options.inJustDecodeBounds=true;
			BitmapFactory.decodeFile(mPath,options);
			int photoW = options.outWidth;
		    int photoH = options.outHeight;
		    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
		    
		    options.inJustDecodeBounds = false;
		    options.inSampleSize = scaleFactor;
		    options.inPurgeable = true;*/

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			mBitmap = BitmapFactory.decodeFile(mPath, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}
