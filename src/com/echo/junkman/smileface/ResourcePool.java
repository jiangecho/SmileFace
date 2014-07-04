package com.echo.junkman.smileface;

import java.util.HashMap;



import com.echo.tmp.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ResourcePool {
	
	private static HashMap<String, Bitmap> bitmaps;
	private static HashMap<String, String> descriptions;
	private static Context sContext;
	private static ResourcePool resourcePool;
	
	private static final int MAX_VALUABLE_IMAGE_INDEX = 10;
	
	private ResourcePool(Context context){
		sContext = context;
	}
	
	public static ResourcePool getResourcePool(Context context){
		if (resourcePool == null) {
			resourcePool = new ResourcePool(context);
			init();
		}
		
		return resourcePool;
	}

	private static void init(){
		if (bitmaps == null) {
			bitmaps = new HashMap<String, Bitmap>();
			descriptions = new HashMap<String, String>();
		}
		
		int imageStartIndex = 0;
		Bitmap bitmap;
		ItemType type;
		
		Resources res = sContext.getResources();
		while (true) {
			int resID = res.getIdentifier("image_" + imageStartIndex, "drawable", "com.echo.tmp");
			if (resID == 0) {
				break;
			}
			Log.e("TMP", "load image_" + imageStartIndex + " " + resID);
			
			bitmap = BitmapFactory.decodeResource(res, resID);
			
			//TODO
			switch (imageStartIndex) {
			case 0:
				type = ItemType.TYPE_BOMB;
				break;
			case 1:
				type = ItemType.TYPE_BOTTLE;
				break;
			default:
				type = ItemType.TYPE_UNKNOWN;
				break;
			}

			bitmaps.put(type.name(), bitmap);

			//TODO
			descriptions.put(type.name(), sContext.getResources().getString(R.string.app_name));
			
			imageStartIndex ++;
		}
		

	}
	
	public Bitmap getItemBitmap(ItemType type){
		
		return bitmaps.get(type.name());
	}
	
	public String getDescription(ItemType type){
		return descriptions.get(type.name());
	}
	


}
