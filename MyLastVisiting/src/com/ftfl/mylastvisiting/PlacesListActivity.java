package com.ftfl.mylastvisiting;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PlacesListActivity extends Activity {
	PlacesDataSource dataSource = null;
	List<Places> placesList = new ArrayList<Places>();
	List<String> mNamesList = new ArrayList<String>();
	List<String> mIdList = new ArrayList<String>();
	ListView mListView = null;
	Integer mPosition = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_list);
		
		final String[] option = new String[] { "View Data","Edit Data","Google Map", "Delete Data" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Select Option");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.e("Selected Item", String.valueOf(which));
				if (which == 0) {
					viewData(mPosition);
				}
				if (which == 1) {
					editData(mPosition);
				}
				/*if (which == 2) {
					googleMap(mPosition);
				}*/
				if (which == 3) {
					deleteData(mPosition);
				}
			}

		});
		final AlertDialog dialog = builder.create();

		
		setListData();
		mListView = (ListView) findViewById(R.id.placesList);
		
		ArrayAdapter<String> placesAdapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, mNamesList);
		
		mListView.setAdapter(placesAdapter);
		
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mPosition = position;
				dialog.show();
			}
		});
	}

	private void setListData() {
		PlacesDataSource dataSource = new PlacesDataSource(this);
		placesList = dataSource.placesData();
		for (int i = 0; i < placesList.size(); i++) {
			Places places = placesList.get(i);
			mIdList.add(places.getmId());
			mNamesList.add(places.getmName());
		}
		
	}
	
	
	public void viewData(Integer ePosition)
	{
		Intent mEditIntent = new Intent(getApplicationContext(),
				PlacesViewActivity.class);
		Long id = Long.parseLong(mIdList.get(ePosition));
		mEditIntent.putExtra("mId", id.toString());
		startActivity(mEditIntent);
	}
	
	public void editData(Integer ePosition)
	{
		Intent mEditIntent = new Intent(getApplicationContext(),
				AddPlacesActivity.class);
		Long id = Long.parseLong(mIdList.get(ePosition));
		mEditIntent.putExtra("mId", id.toString());
		startActivity(mEditIntent);
		//startActivityForResult(mEditIntent, 2);
	}
	
	/*public void googleMap(Integer ePosition)
	{
		Intent mEditIntent = new Intent(getApplicationContext(),
				GoogleMapActivity.class);
		Long mId = Long.parseLong(mIdList.get(ePosition));
		mEditIntent.putExtra("mId", mId.toString());
		startActivity(mEditIntent);
	}*/
	
	public void deleteData(Integer ePosition)
	{
		dataSource = new PlacesDataSource(this);
		Long id = Long.parseLong(mIdList.get(ePosition));
		dataSource.deleteData(id);
		Toast.makeText(getApplicationContext(), "Successfully Deleted",Toast.LENGTH_SHORT ).show();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.places_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
        case R.id.addShoppingComplex:
        	startActivity(new Intent(PlacesListActivity.this, AddPlacesActivity.class));
            return true;


        
        default:
            return super.onOptionsItemSelected(item);
    }
	}
}
