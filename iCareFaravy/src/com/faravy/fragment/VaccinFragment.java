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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.faravy.adapter.VaccineAdapter;
import com.faravy.database.VaccineDataSource;
import com.faravy.icare.AddVaccineActivity;
import com.faravy.icare.R;
import com.faravy.modelclass.Vaccine;

public class VaccinFragment extends Fragment {

	ListView mUpcomingListView = null;
	ListView mCompetedListView = null;
	VaccineDataSource mDataSource = null;
	VaccineAdapter mAdapter = null;
	VaccineAdapter mAdapter1 = null;

	ArrayList<Vaccine> mUpcominglist = null;
	ArrayList<Vaccine> mCompletedlist = null;
	ImageButton mFab;
	ActionMode mActionMode;
	Integer mPosition = 0;
	Integer mSelected = 0;

	public VaccinFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_vaccine, container,
				false);

		mFab = (ImageButton) rootView.findViewById(R.id.fab);
		mUpcomingListView = (ListView) rootView
				.findViewById(R.id.upcomingVaccine);
		mCompetedListView = (ListView) rootView
				.findViewById(R.id.competedVaccine);
		mDataSource = new VaccineDataSource(getActivity());

		// Getting All Doctor list from database
		mUpcominglist = mDataSource.getUpcomingVaccine();

		mAdapter = new VaccineAdapter(getActivity(), mUpcominglist);

		// Set each row in list using custom adapter
		mUpcomingListView.setAdapter(mAdapter);

		mUpcomingListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						mSelected = position;
						if (mActionMode != null) {
							return false;
						}
						mActionMode = getActivity().startActionMode(
								mActionModeCallback);

						view.setSelected(true);
						return true;
					}
				});

		// Getting All Doctor list from database
		mCompletedlist = mDataSource.getCompletedVaccine();

		mAdapter1 = new VaccineAdapter(getActivity(), mCompletedlist);

		// Set each row in list using custom adapter
		mCompetedListView.setAdapter(mAdapter1);

		mFab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),
						AddVaccineActivity.class));

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
				delete(mPosition);
				mUpcomingListView.invalidateViews();
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

		mDataSource = new VaccineDataSource(getActivity());
		String id = mUpcominglist.get(mSelected).getmId();
		mDataSource.deleteData(id);
		Toast.makeText(getActivity(), "Successfully Deleted",
				Toast.LENGTH_SHORT).show();
		VaccinFragment a = new VaccinFragment();
		FragmentTransaction fragTransaction = getFragmentManager()
				.beginTransaction();
		fragTransaction.replace(R.id.frame_container, a);

		fragTransaction.commit();
	}
}
