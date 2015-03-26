package com.purpleberry.dineranimation;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils
{

	int[] mPhotos = { R.drawable.dish1, R.drawable.dish2

	};

	static HashMap<Integer, Bitmap> sBitmapResourceMap = new HashMap<Integer, Bitmap>();

	public ArrayList<PictureData> loadPhotos(Resources resources)
	{
		ArrayList<PictureData> pictures = new ArrayList<PictureData>();
		for (int i = 0; i < 10; ++i)
		{
			int resourceId = mPhotos[(int) (Math.random() * mPhotos.length)];
			Bitmap bitmap = getBitmap(resources, resourceId);
			Bitmap thumbnail = bitmap;
			pictures.add(new PictureData(resourceId, thumbnail));
		}
		return pictures;
	}

	/**
	 * Utility method to get bitmap from cache or, if not there, load it from its resource.
	 */
	static Bitmap getBitmap(Resources resources, int resourceId)
	{
		Bitmap bitmap = sBitmapResourceMap.get(resourceId);
		if (bitmap == null)
		{
			bitmap = BitmapFactory.decodeResource(resources, resourceId);
			sBitmapResourceMap.put(resourceId, bitmap);
		}
		return bitmap;
	}

}
