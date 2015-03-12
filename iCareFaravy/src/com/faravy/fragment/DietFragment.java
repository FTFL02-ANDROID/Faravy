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

import com.faravy.adapter.DietAdapter;
import com.faravy.database.DietDataSource;
import com.faravy.icare.AddDietActivity;
import com.faravy.icare.R;
import com.faravy.modelclass.Diet;

public class DietFragment extends Fragment {

	ListView mList = null;
	ListView mList2 = null;
	DietDataSource mDataSource = null;
	DietAdapter mAdapter = null;
	DietAdapter mAdapter1 = null;
	ArrayList<Diet> mDietlist = null;
	ArrayList<Diet> mUpcominglist = null;
	ImageButton mFab;

	ActionMode mActionMode;
	Integer mPosition = 0;
	Integer mSelected = 0;

	public DietFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_diet, container,
				false);

		mFab = (ImageButton) rootView.findViewById(R.id.fab);
		mList = (ListView) rootView.findViewById(R.id.list1);
		mList2 = (ListView) rootView.findViewById(R.id.list2);
		mDataSource = new DietDataSource(getActivity());

		// Getting All Diet list from database
		mDietlist = mDataSource.getAllDiet();

		mAdapter = new DietAdapter(getActivity(), mDietlist);

		// Set each row in list using custom adapter
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelected = position;
				if (mActionMode != null) {
					// return false;
				}
				mActionMode = getActivity()
						.startActionMode(mActionModeCallback);

				view.setSelected(true);
				// return true;
			}
		});
		mUpcominglist = mDataSource.getUpcomingDiet();

		mAdapter1 = new DietAdapter(getActivity(), mUpcominglist);

		// Set each row in list using custom adapter
		mList2.setAdapter(mAdapter1);

		mFab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AddDietActivity.class));

			}
		});
		/*
		 * List<String> upcomingDates = mDataSource.upcomingDates();
		 * 
		 * ArrayAdapter<String> mDatesAdapter = new ArrayAdapter<String>(
		 * getActivity(), android.R.layout.simple_list_item_1, upcomingDates);
		 * mList2.setAdapter(mDatesAdapter);
		 */

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
				// mList.invalidateViews();
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

		mDataSource = new DietDataSource(getActivity());

		String id = mDietlist.get(mSelected).getmId();

		mDataSource.deleteData(id);
		Toast.makeText(getActivity(), "Successfully Deleted",
				Toast.LENGTH_SHORT).show();
		DietFragment a = new DietFragment();
		FragmentTransaction fragTransaction = getFragmentManager()
				.beginTransaction();
		fragTransaction.replace(R.id.frame_container, a);

		fragTransaction.commit();
	}

	protected void edit() {
		/*
		 * mDataSource = new DietDataSource(getActivity()); String id =
		 * mDietlist.get(mSelected).getmId();
		 * 
		 * mDataSource.updateDiet(id);
		 */
		startActivity(new Intent(getActivity(), AddDietActivity.class));

	}
}
