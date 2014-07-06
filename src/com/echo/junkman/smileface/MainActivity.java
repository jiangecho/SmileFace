package com.echo.junkman.smileface;

import com.echo.tmp.R;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("NewApi")
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
	
	
	//TODO init
	private int totalClickCount;
	private int smileFaceClickCount;
	private int elapseSeconds;
	
	private static final int GAME_TIME_LENGHT = 5;
	private static final String BEST_SCORE = "bestScore";
	
	private SharedPreferences sharedPreferences;
	
	@Override
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
		
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		startGame();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	//TODO
	@Override
	public void onClick(View view) {
		totalClickCount ++;
		if (view instanceof ItemN) {
			
			((ItemN) view).setRandomType();
			ItemType type = ((ItemN) view).getType();
			
			//TODO
			if (type == ItemType.TYPE_BOMB) {
				smileFaceClickCount ++;
				currentScore += ((ItemN) view).getValue();
			}else if (type == ItemType.TYPE_BOTTLE) {
				
				gameCountDownTimer.cancel();
				gameCountDownTimer.start();
				progressBar.setProgress(60);
			}
			
			currentScoreView.setText(getResources().getString(R.string.score) + " " + currentScore);
			if (currentScore > bestScore) {
				bestScore = currentScore;
				bestScoreView.setText(getResources().getString(R.string.best_score) + " " + bestScore);
			}
			
		}
		
	}
	
	private void startGame(){
		totalClickCount = 0;
		smileFaceClickCount = 0;
		elapseSeconds = 0;
		
		currentScore = 0;
		elapseSeconds = 0;
		
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
			int clickSpeed;
			int rewardScore;
			gameView.randomResetAllItems();
			progressBar.setProgress(60);
			
			AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
			
			LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
			View view = layoutInflater.inflate(R.layout.alert_layout, null);
			TextView totalClickCountTV = (TextView) view.findViewById(R.id.totalClickCountTV);
			TextView smileFaceClickCountTV = (TextView) view.findViewById(R.id.smileFaceClickCountTV);
			TextView clickSpeedTV = (TextView) view.findViewById(R.id.clickSpeedTV);
			TextView rewardScoreTV = (TextView) view.findViewById(R.id.rewardScoreTV);
			TextView totalScoreTV = (TextView) view.findViewById(R.id.totalScoreTV);
			
			Resources resources = MainActivity.this.getResources();
			
			totalClickCountTV.setText(resources.getString(R.string.total_click_count) + " " + totalClickCount);
			smileFaceClickCountTV.setText(resources.getString(R.string.smile_face_click_count) + " " + smileFaceClickCount);
			clickSpeed = totalClickCount / elapseSeconds;
			clickSpeedTV.setText(resources.getString(R.string.click_speed) + " " + clickSpeed);
			
			rewardScore = clickSpeed * 100;
			rewardScoreTV.setText(resources.getString(R.string.reward_score) + " " + rewardScore);
			
			totalScoreTV.setText(resources.getString(R.string.total_score) + " " + (currentScore + rewardScore));

			alertDialog.setView(view);
			alertDialog.setTitle("Game Over");
			
			
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.restart), MainActivity.this);
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.quit), MainActivity.this);
			alertDialog.show();
			
		}

		@Override
		public void onTick(long millisUntilFinished) {
			
			elapseSeconds += (GAME_TIME_LENGHT * 1000 - millisUntilFinished) / 1000;
			progressBar.setProgress((int) (millisUntilFinished / 1000));
			gameView.randomRestParItems();
		}
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
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
