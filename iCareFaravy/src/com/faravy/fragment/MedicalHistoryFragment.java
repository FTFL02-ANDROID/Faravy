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

import com.faravy.adapter.MedicalHistoryAdapter;
import com.faravy.database.MedicalHistoryDataSource;
import com.faravy.icare.AddMedicalHistoryActivity;
import com.faravy.icare.R;
import com.faravy.icare.ViewHistoryActivity;
import com.faravy.modelclass.MedicalHistory;

public class MedicalHistoryFragment extends Fragment {
	ImageButton mFab;
	String mImage = null;
	ListView mList = null;
	MedicalHistoryDataSource mDataSource = null;
	MedicalHistoryAdapter mAdapter = null;
	ArrayList<MedicalHistory> mHistorylist = null;
	Integer mPosition = 0;
	ActionMode mActionMode;

	public MedicalHistoryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_medical_history,
				container, false);
		mFab = (ImageButton) rootView.findViewById(R.id.fab);
		mList = (ListView) rootView.findViewById(R.id.historyList);
		mDataSource = new MedicalHistoryDataSource(getActivity());
		mHistorylist = mDataSource.getAllHistory();
		mAdapter = new MedicalHistoryAdapter(getActivity(), mHistorylist);

		// Set each row in list using custom adapter
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPosition = position;
				Intent mIntent = new Intent(getActivity(), ViewHistoryActivity.class);
				String sId = mHistorylist.get(mPosition).getmId();
				mIntent.putExtra("mId", sId);
				startActivity(mIntent);

			}
		});
		mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent,
					View view, int position, long id) {
				mPosition = position;
				if (mActionMode != null) {
					return false;
				}
				mActionMode = getActivity().startActionMode(
						mActionModeCallback);

				view.setSelected(true);
				return true;
			}
		});
		mFab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),
						AddMedicalHistoryActivity.class));

				/*
				 * AddHistoryFragment secFrag = new AddHistoryFragment();
				 * FragmentTransaction fragTransaction = getFragmentManager()
				 * .beginTransaction();
				 * fragTransaction.replace(R.id.frame_container, secFrag);
				 * fragTransaction.commit();
				 */
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

				mode.finish(); // Action picked, so close the CAB
				return true;

			case R.id.delete:
				delete();
				
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

	public void delete() {

		mDataSource = new MedicalHistoryDataSource(getActivity());
		String id = mHistorylist.get(mPosition).getmId();
		mDataSource.deleteData(id);
		Toast.makeText(getActivity(), "Successfully Deleted",
				Toast.LENGTH_SHORT).show();
		MedicalHistoryFragment a = new MedicalHistoryFragment();
		FragmentTransaction fragTransaction = getFragmentManager()
				.beginTransaction();
		fragTransaction.replace(R.id.frame_container, a);

		fragTransaction.commit();
	}
}
