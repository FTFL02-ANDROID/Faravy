package com.faravy.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.faravy.adapter.ImageAdapter;
import com.faravy.icare.AddMedicalHistoryActivity;
import com.faravy.icare.R;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class GalleryFragment extends Fragment{
	
	List<String> mImagePathList = new ArrayList<String>();

	public GalleryFragment() {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				AddMedicalHistoryActivity.IMAGE_DIRECTORY_NAME);
		
		for (File imageFile : mediaStorageDir.listFiles()) {
			if (imageFile.isFile()) {
				mImagePathList.add(imageFile.getAbsolutePath());
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_gallery,
				container, false);

		GridView gridview = (GridView) view.findViewById(R.id.gridView);
		gridview.setAdapter(new ImageAdapter(getActivity(), mImagePathList));

		return view;
	}

}
