package com.echo.junkman.smileface;

import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ItemN extends ImageView{

	private ItemType type;
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
	
	public void setType(ItemType type){
		this.type = type;
		
		Bitmap bitmap = resourcePool.getItemBitmap(type);
		setImageBitmap(bitmap);
	}
	
	public ItemType getType(){
		return this.type;
	}
	
	public int getValue(){
		int value;
		switch (this.type) {
		case TYPE_BOMB:
			value = -100;
			break;
		case TYPE_MONEY_100:
			value = 100;
			break;

		default:
			value = 0;
			break;
		}
		
		return value;
	}
	
	public void setRandomType(){
		
		//TODO random type
		ItemType tp;
		switch (random.nextInt(2)) {
		case 0:
			tp = ItemType.TYPE_BOMB;
			break;
		case 1:
			tp = ItemType.TYPE_BOTTLE;
			break;

		default:
			tp = ItemType.TYPE_UNKNOWN;
			break;
		}
		this.type = tp;
		Bitmap bitmap = resourcePool.getItemBitmap(tp);
		Matrix matrix = new Matrix();
		matrix.setRotate(random.nextInt(360));
		
		Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		
		setImageBitmap(bm);
		
	}
	
	


}
