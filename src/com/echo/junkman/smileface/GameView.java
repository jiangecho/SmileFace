package com.echo.junkman.smileface;


import java.util.Random;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
//import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

@SuppressLint("NewApi")
public class GameView extends GridLayout {
	
	private static final int COLUMN_COUNT = 4;
	private int with = 100, height = 100;
	
	private boolean initialized = false;
	private Context context;
	
	private ItemN[] items;
	private OnClickListener onClickListener;

	public GameView(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	
	private void init(){
		setColumnCount(COLUMN_COUNT);
		items = new ItemN[COLUMN_COUNT * COLUMN_COUNT];
	}
	
	public void setOnClickListener(OnClickListener listener){
		this.onClickListener = listener;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh); 
		
		if (!initialized) {
			initialized = true;
			//height = with = (w - 50) / 4 ;
			height = with = w / COLUMN_COUNT;
			
			for (int i = 0; i < 16; i++) {
				ItemN item = new ItemN(this.context);
				addView(item, with, height);
				
				items[i] = item;
				item.setOnClickListener(this.onClickListener);
				
			}
		}
	}
	
	public void start(){
		
	}
	
	public void randomRestParItems(){
		
		if (!initialized) {
			return;
		}
		
		Random random = new Random();
		
		int n = random.nextInt(COLUMN_COUNT * COLUMN_COUNT);
		int index;
		for (int i = 0; i < n; i++) {
			index = random.nextInt(COLUMN_COUNT * COLUMN_COUNT);
//			Log.e("TMP", "index " + index + "items " + items.length);
//			ItemN itemN = items[index];
//			
//			Log.e("TMP", "is null " + (itemN == null ? true : false));
			
			items[index].setRandomType();
		}
	}
	
	public void randomResetAllItems(){
		if (!initialized) {
			return;
		}
		
		ItemN itemN;
		for (int i = 0; i < items.length; i++) {
			itemN = items[i];
			itemN.setRandomType();
			
		}
	}

	
}
