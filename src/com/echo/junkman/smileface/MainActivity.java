package com.echo.junkman.smileface;

import java.text.DecimalFormat;

import com.echo.junkman.smileface.R;
import com.wandoujia.ads.sdk.Ads;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, DialogInterface.OnClickListener{

	private GameView gameView;
	private TextView currentScoreView;
	private TextView bestScoreView;
	private int currentScore;
	private int bestScore;
	
	private CountDownTimer gameCountDownTimer;
	private CountDownTimer countDownTimer;
	private ProgressBar progressBar;
	private TextView countDownView;
	private Context context;
	
	
	//TODO init
	private int totalClickCount;
	private int smileFaceClickCount;
	private int elapseMillis;
	
	private static final int GAME_TIME_LENGHT = 60;
	private static final String BEST_SCORE = "bestScore";
	
	private SharedPreferences sharedPreferences;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gameView = (GameView) findViewById(R.id.gameView);
		
		currentScoreView = (TextView) findViewById(R.id.currentScoreView);
		bestScoreView = (TextView) findViewById(R.id.bestScoreView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		
		gameCountDownTimer = new GameCountDownTimer(GAME_TIME_LENGHT * 1000, 2000);
		gameView.setOnClickListener(this);
		
		sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		
		countDownView = (TextView) findViewById(R.id.countDownView);
		//block touch event when the count down view is visible
		countDownView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		countDownTimer = new CountDownTimer(3 * 1000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				countDownView.setText("" + millisUntilFinished / 1000);
				
			}
			
			@Override
			public void onFinish() {
				countDownView.setText("Go");
				countDownView.setVisibility(View.GONE);

				gameCountDownTimer.start();
				
			}
		};
		// Init AdsSdk.
		 try {
		   Ads.init(this, "100009149", "595b980284394c43347219baff32b6f8");
		 } catch (Exception e) {
		   e.printStackTrace();
		 }
		 this.context = this;
		
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		startGame();
		try {
			Ads.showBannerAd(this, (ViewGroup) findViewById(R.id.banner_ad_container), "5a98ec742b7f5e20c0717c677c8433d9");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		countDownTimer.cancel();
		gameCountDownTimer.cancel();
	}



	@Override
	public void onClick(View view) {
		totalClickCount ++;
		if (view instanceof ItemN) {
			
			int type = ((ItemN) view).getType();
			
			switch (type) {
			case 0:
			case 1:
			case 2:
				smileFaceClickCount ++;
				break;
			case 3:
//				gameCountDownTimer.cancel();
//				gameCountDownTimer.start();
//				progressBar.setProgress(GAME_TIME_LENGHT);
//				
//				Toast.makeText(this, getResources().getString(R.string.show_msg), Toast.LENGTH_SHORT).show();
				
				break;
			default:
				break;
			}
			currentScore += ((ItemN) view).getValue();
			currentScoreView.setText(getResources().getString(R.string.score) + " " + currentScore);
			if (currentScore > bestScore) {
				bestScore = currentScore;
				bestScoreView.setText(getResources().getString(R.string.best_score) + " " + bestScore);
			}
			
			((ItemN) view).setRandomType();
		}
		
	}
	
	private void startGame(){
		totalClickCount = 0;
		smileFaceClickCount = 0;
		
		currentScore = 0;
		elapseMillis = 0;
		
		currentScoreView.setText(getString(R.string.score) + " 0");
		
		bestScore = sharedPreferences.getInt(BEST_SCORE, 0);
		bestScoreView.setText(getString(R.string.best_score) + " " + bestScore);

		countDownTimer.start();
	}
	
	private class GameCountDownTimer extends CountDownTimer{

		public GameCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			
		}

		@Override
		public void onFinish() {
			float clickSpeed;
			int rewardScore;
			gameView.randomResetAllItems();
			progressBar.setProgress(60);
			
			AlertDialog alertDialog = new AlertDialog.Builder(context).create();
			
			LayoutInflater layoutInflater = ((MainActivity)context).getLayoutInflater();
			View view = layoutInflater.inflate(R.layout.alert_layout, null);
			TextView totalClickCountTV = (TextView) view.findViewById(R.id.totalClickCountTV);
			TextView smileFaceClickCountTV = (TextView) view.findViewById(R.id.smileFaceClickCountTV);
			TextView clickSpeedTV = (TextView) view.findViewById(R.id.clickSpeedTV);
			TextView rewardScoreTV = (TextView) view.findViewById(R.id.rewardScoreTV);
			TextView totalScoreTV = (TextView) view.findViewById(R.id.totalScoreTV);
			
			Resources resources = context.getResources();
			
			totalClickCountTV.setText(resources.getString(R.string.total_click_count) + " " + totalClickCount);
			smileFaceClickCountTV.setText(resources.getString(R.string.smile_face_click_count) + " " + smileFaceClickCount);
			clickSpeed = (float)totalClickCount / GAME_TIME_LENGHT;
			
			DecimalFormat fnum = new DecimalFormat("##0.00");
			String clickSpeedString = fnum.format(clickSpeed);
			
			clickSpeedTV.setText(resources.getString(R.string.click_speed) + " " + clickSpeedString);
			
			rewardScore = (int) (clickSpeed * 20);
			rewardScoreTV.setText(resources.getString(R.string.reward_score) + " " + rewardScore);
			
			totalScoreTV.setText(resources.getString(R.string.total_score) + " " + (currentScore + rewardScore));

			alertDialog.setView(view);
			alertDialog.setTitle("Game Over");
			
			
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.restart), (MainActivity)context);
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.quit), (MainActivity)context);
			alertDialog.show();
			
		}

		@Override
		public void onTick(long millisUntilFinished) {
			
			elapseMillis += GAME_TIME_LENGHT * 1000 - millisUntilFinished;
			progressBar.setProgress((int) (millisUntilFinished / 1000));
			gameView.randomRestParItems();
		}
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		sharedPreferences.edit().putInt(BEST_SCORE, bestScore).commit();

		dialog.dismiss();

		switch (which) {
		case AlertDialog.BUTTON_POSITIVE:
			startGame();
			break;
		case AlertDialog.BUTTON_NEGATIVE:
			finish();
			break;

		default:
			break;
		}
	}
	

}
