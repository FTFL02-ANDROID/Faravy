package com.faravy.fragment;

import java.util.ArrayList;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.faravy.adapter.DoctorAdapter;
import com.faravy.database.DoctorDataSource;
import com.faravy.icare.AddDoctorActivity;
import com.faravy.icare.R;
import com.faravy.icare.ViewDoctorActivity;
import com.faravy.modelclass.Doctor;

public class DoctorsFragment extends Fragment {

	ListView mList = null;
	DoctorDataSource mDataSource = null;
	DoctorAdapter mAdapter = null;
	ArrayList<Doctor> mDoctorlist = null;
	ImageButton mFab;
	ActionMode mActionMode;
	Integer mPosition = 0;
	Integer mSelected = 0;

	public DoctorsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_doctor, container,
				false);
		mFab = (ImageButton) rootView.findViewById(R.id.fab);
		mList = (ListView) rootView.findViewById(R.id.doctorList);
		mDataSource = new DoctorDataSource(getActivity());

		// Getting All Doctor list from database
		mDoctorlist = mDataSource.getAllDoctor();

		mAdapter = new DoctorAdapter(getActivity(), mDoctorlist);

		// Set each row in list using custom adapter
		mList.setAdapter(mAdapter);

		mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelected = position;
				if (mActionMode != null) {

					return false;
				}
				mActionMode = getActivity()
						.startActionMode(mActionModeCallback);
				
				view.setSelected(true);
				return true;
			}
		});
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPosition = position;
				Intent mIntent = new Intent(getActivity(),
						ViewDoctorActivity.class);
				String sId = mDoctorlist.get(mPosition).getmId();
				mIntent.putExtra("mId", sId);
				startActivity(mIntent);

			}
		});

		mFab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AddDoctorActivity.class));

			}
		});

		return rootView;
	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			// mode.setTitle("Options");
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.list_doctor, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after
		// onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.edit:
				edit();

				mode.finish(); // Action picked, so close the CAB
				return true;

			case R.id.delete:
				delete(mPosition);
				mList.invalidateViews();
				mode.finish();
				return true;

			default:
				return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}
	};

	public void delete(Integer ePosition) {

		mDataSource = new DoctorDataSource(getActivity());
		String id = mDoctorlist.get(mSelected).getmId();
		mDataSource.deleteData(id);
		Toast.makeText(getActivity(), "Successfully Deleted",
				Toast.LENGTH_SHORT).show();
		DoctorsFragment a = new DoctorsFragment();
		FragmentTransaction fragTransaction = getFragmentManager()
				.beginTransaction();
		fragTransaction.replace(R.id.frame_container, a);

		fragTransaction.commit();
	}

	protected void edit() {
		startActivity(new Intent(getActivity(), AddDoctorActivity.class));

	}

}
