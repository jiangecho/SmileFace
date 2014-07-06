package com.echo.junkman.smileface;

import java.util.HashMap;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ResourcePool {
	
	private static HashMap<String, Bitmap> bitmaps;
	private static HashMap<String, String> descriptions;
	private static Context sContext;
	private static ResourcePool resourcePool;
	
	
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
		
		Resources res = sContext.getResources();
		while (true) {
			int resID = res.getIdentifier("image_" + imageStartIndex, "drawable", sContext.getPackageName());
			if (resID == 0) {
				break;
			}
			//Log.e("TMP", "load image_" + imageStartIndex + " " + resID);
			
			bitmap = BitmapFactory.decodeResource(res, resID);

			bitmaps.put("" + imageStartIndex, bitmap);

			//TODO
			descriptions.put("" + imageStartIndex, sContext.getResources().getString(R.string.app_name));
			
			imageStartIndex ++;
		}
		

	}
	
	public Bitmap getItemBitmap(int index){
		
		return bitmaps.get("" + index);
	}
	
	public String getDescription(int index){
		return descriptions.get("" + index);
	}
	


}
