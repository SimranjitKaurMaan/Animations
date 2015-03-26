package com.purpleberry.dineranimation;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity
{

	private GridView mGridView;

	HashMap<ImageView, PictureData> mPicturesData = new HashMap<ImageView, PictureData>();

	BitmapUtils mBitmapUtils = new BitmapUtils();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		getWindow().setExitTransition(new Explode());

		setContentView(R.layout.activity_main);
		
		 
		mGridView = (GridView) findViewById(R.id.horizontal_grid_view);
		// load images in gridview
		loadImagesToGridview();
		mGridView.setOnClickListener(thumbnailClickListener);
	}

	private void loadImagesToGridview()
	{
		// add all photo thumbnails to layout
		Resources resources = getResources();
		ArrayList<PictureData> pictures = mBitmapUtils.loadPhotos(resources);
		for (int i = 0; i < pictures.size(); ++i)
		{
			PictureData pictureData = pictures.get(i);
			BitmapDrawable thumbnailDrawable = new BitmapDrawable(resources, pictureData.thumbnail);
			ImageView imageView = new ImageView(this);
			imageView.setOnClickListener(thumbnailClickListener);
			imageView.setImageDrawable(thumbnailDrawable);
			mPicturesData.put(imageView, pictureData);
			mGridView.addView(imageView);

		}
	}

	View.OnClickListener thumbnailClickListener = new View.OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			int[] screenLocation = new int[2];
			v.getLocationOnScreen(screenLocation);
			PictureData info = mPicturesData.get(v);

			Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
			intent.putExtra(DetailedActivity.EXTRA_PARAM_ID, info.resourceId);

			/**
			 * Now creating an {@link android.app.ActivityOptions} instance using the
			 * {@link ActivityOptionsCompat#makeSceneTransitionAnimation(Activity, Pair[])} factory method.
			 */
			
			@SuppressWarnings("unchecked")
			ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
		            MainActivity.this,

		            // Now we provide a list of Pair items which contain the view we can transitioning
		            // from, and the name of the view it is transitioning to, in the launched activity
		            new Pair<View, String>(v,
		                    DetailedActivity.VIEW_NAME_IMAGE));
		            

		    // Now we can start the Activity, providing the activity options as a bundle
		    ActivityCompat.startActivity(MainActivity.this, intent, activityOptions.toBundle());
			
			

		}

	};

}
