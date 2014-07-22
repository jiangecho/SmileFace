package com.echo.junkman.smileface;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelFragment extends Fragment{
	
	private View rootView;
	private TextView levelTV;
	private TextView levelPassConditionTV;
	private ImageView[] imageViews;
	
	private int currentLevel;
	
	private Resources resources;
	private ResourcePool resourcePool;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resources = getResources();
		resourcePool = ResourcePool.getResourcePool(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.level_info, container, false);
			levelTV = (TextView) rootView.findViewById(R.id.levelTV);
			levelPassConditionTV = (TextView) rootView.findViewById(R.id.levelPassConditionTV);
			
			imageViews = new ImageView[5];
			imageViews[0] = (ImageView) rootView.findViewById(R.id.imageView1);
			imageViews[1] = (ImageView) rootView.findViewById(R.id.imageView2);
			imageViews[2] = (ImageView) rootView.findViewById(R.id.imageView3);
			imageViews[3] = (ImageView) rootView.findViewById(R.id.imageView4);
			imageViews[4] = (ImageView) rootView.findViewById(R.id.imageView5);
		}else {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}
		
		
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void setCurrentLevel(int level)
	{
		this.currentLevel = level;
		String str = resources.getString(R.string.level, currentLevel);
		levelTV.setText(str);
	}

	
	public void setCurrentLevelImages(ArrayList<Integer> indexList){
		int len = indexList.size();
		int condition = 60 / len;

		String str = resources.getString(R.string.level_pass_condition_descriptin, condition);
		levelPassConditionTV.setText(str);
		
		//TODO
		switch (len) {
		case 1:
			imageViews[0].setVisibility(View.INVISIBLE);
			imageViews[1].setVisibility(View.INVISIBLE);
			imageViews[2].setImageBitmap(resourcePool.getItemBitmap(indexList.get(0)));
			imageViews[3].setVisibility(View.INVISIBLE);
			imageViews[4].setVisibility(View.INVISIBLE);

			imageViews[3].setVisibility(View.VISIBLE);
			break;
		case 2:
			imageViews[0].setVisibility(View.INVISIBLE);
			imageViews[1].setImageBitmap(resourcePool.getItemBitmap(indexList.get(0)));
			imageViews[2].setVisibility(View.INVISIBLE);
			imageViews[3].setImageBitmap(resourcePool.getItemBitmap(indexList.get(1)));
			imageViews[4].setVisibility(View.INVISIBLE);
			
			imageViews[1].setVisibility(View.VISIBLE);
			imageViews[3].setVisibility(View.VISIBLE);
			break;
		case 3:
			imageViews[0].setVisibility(View.INVISIBLE);
			imageViews[1].setImageBitmap(resourcePool.getItemBitmap(indexList.get(0)));
			imageViews[2].setImageBitmap(resourcePool.getItemBitmap(indexList.get(1)));
			imageViews[3].setImageBitmap(resourcePool.getItemBitmap(indexList.get(2)));
			imageViews[4].setVisibility(View.INVISIBLE);
			
			imageViews[1].setVisibility(View.VISIBLE);
			imageViews[2].setVisibility(View.VISIBLE);
			imageViews[3].setVisibility(View.VISIBLE);
			break;
		case 4:
			imageViews[0].setImageBitmap(resourcePool.getItemBitmap(indexList.get(0)));
			imageViews[1].setImageBitmap(resourcePool.getItemBitmap(indexList.get(1)));
			imageViews[2].setImageBitmap(resourcePool.getItemBitmap(indexList.get(2)));
			imageViews[3].setImageBitmap(resourcePool.getItemBitmap(indexList.get(3)));
			imageViews[4].setVisibility(View.INVISIBLE);
			
			imageViews[0].setVisibility(View.VISIBLE);
			imageViews[1].setVisibility(View.VISIBLE);
			imageViews[2].setVisibility(View.VISIBLE);
			imageViews[3].setVisibility(View.VISIBLE);
			break;
		case 5:
			imageViews[0].setImageBitmap(resourcePool.getItemBitmap(indexList.get(0)));
			imageViews[1].setImageBitmap(resourcePool.getItemBitmap(indexList.get(1)));
			imageViews[2].setImageBitmap(resourcePool.getItemBitmap(indexList.get(2)));
			imageViews[3].setImageBitmap(resourcePool.getItemBitmap(indexList.get(3)));
			imageViews[4].setImageBitmap(resourcePool.getItemBitmap(indexList.get(4)));
			
			imageViews[0].setVisibility(View.VISIBLE);
			imageViews[1].setVisibility(View.VISIBLE);
			imageViews[2].setVisibility(View.VISIBLE);
			imageViews[3].setVisibility(View.VISIBLE);
			imageViews[4].setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
		
		rootView.invalidate();
	}
	

}
