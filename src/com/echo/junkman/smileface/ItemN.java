package com.echo.junkman.smileface;

import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ItemN extends ImageView{

	private int type;
	private Context context;
	
	private Random random;
	private ResourcePool resourcePool;

	public ItemN(Context context) {
		super(context);
		init(context);
	}

	public ItemN(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ItemN(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context){
		this.context = context;
		resourcePool = ResourcePool.getResourcePool(this.context);
		random = new Random();
		setRandomType();
	}
	
	
	public int getType(){
		return this.type;
	}
	
	public int getValue(){
		int value;
		switch (this.type) {
		case 0:
		case 1:
		case 2:
			value = 20;
			break;
		case 3:
			value = 100;
			break;

		default:
			value = 1;
			break;
		}
		
		return value;
	}
	
	public void setType(int type){
		this.type = type;
		Bitmap bitmap = resourcePool.getItemBitmap(type);
//		Matrix matrix = new Matrix();
//		matrix.setRotate(random.nextInt(360));
//		
//		Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		
		setImageBitmap(bitmap);
	}
	
	public void setRandomType(){
		
		int type = random.nextInt(27);
		if (type == 3) {
			type = random.nextInt(4);
			setType(type);
		}else {
			setType(type);
		}
	}
	
	


}
