package com.purpleberry.dineranimation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DetailedActivity extends Activity
{
	// Extra name for the ID parameter
	public static final String EXTRA_PARAM_ID = "detail:_id";

	// View name of the header image. Used for activity scene transitions
	public static final String VIEW_NAME_IMAGE = "detail:header:image";

	private GridView mGridView;

	private ImageView mImageView;

	private RelativeLayout mTopLevelLayout;

	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		setContentView(R.layout.activity_detailed);
		bitmap = BitmapUtils.getBitmap(getResources(), getIntent().getIntExtra(EXTRA_PARAM_ID, 0));
		initialiseViews();

		/**
		 * Set the name of the view's which will be transition to, using the static values above. This could be done in
		 * the layout XML, but exposing it via static variables allows easy querying from other Activities
		 */
		ViewCompat.setTransitionName(mImageView, VIEW_NAME_IMAGE);

		loadItem();

	}

	private void loadItem()
	{

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && addTransitionListener())
		{
			// If we're running on Lollipop and we have added a listener to the shared element
			// transition, load the thumbnail. The listener will load the full-size image when
			// the transition is complete.
			loadThumbnail();
		}
		else
		{
			// If all other cases we should just load the full-size image now
			loadFullSizeImage();
		}

	}

	private void loadFullSizeImage()
	{
		mImageView.setImageBitmap(bitmap);
		
	}

	private void loadThumbnail()
	{
		mImageView.setImageBitmap(bitmap);
		
	}

	private boolean addTransitionListener()
	{
		final Transition transition = getWindow().getSharedElementEnterTransition();
		 
        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage();
 
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }
 
                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                }
 
                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }
 
                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }
 
                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
            return true;
        }
 
        // If we reach here then we have not added a listener
        return false;
		
	}

	private void initialiseViews()
	{
		mGridView = (GridView) findViewById(R.id.vertical_grid_view);
		mImageView = (ImageView) findViewById(R.id.fullImageView);
		mTopLevelLayout = (RelativeLayout) findViewById(R.id.topLevelLayout);

	}

}
