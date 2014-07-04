package com.echo.junkman.smileface;

import com.echo.tmp.R;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener{

	private GameView gameView;
	private TextView currentScoreView;
	private TextView bestScoreView;
	private int currentScore = 0;
	private int bestScore = 0;
	
	private CountDownTimer gameCountDownTimer;
	private CountDownTimer countDownTimer;
	private ProgressBar progressBar;
	private TextView countDownView;
	
	private OnClickListener onClickListener;
	
	private int clickTotalCount = 0;
	private int clickSmileFaceCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gameView = (GameView) findViewById(R.id.gameView);
		
		currentScoreView = (TextView) findViewById(R.id.currentScoreView);
		bestScoreView = (TextView) findViewById(R.id.bestScoreView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		
		gameCountDownTimer = new GameCountDownTimer(60 * 1000, 2000);
		gameView.setOnClickListener(this);
		
		//onClickListener = this;
		
		countDownView = (TextView) findViewById(R.id.countDownView);
		countDownTimer = new CountDownTimer(3 * 1000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				countDownView.setText("" + millisUntilFinished / 1000);
				
			}
			
			@Override
			public void onFinish() {
				countDownView.setText("Go");
				countDownView.setVisibility(View.GONE);

				//gameView.setOnClickListener(onClickListener);
				gameCountDownTimer.start();
				
			}
		};
		countDownTimer.start();
		
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
		clickTotalCount ++;
		if (view instanceof ItemN) {
			
			((ItemN) view).setRandomType();
			ItemType type = ((ItemN) view).getType();
			
			//TODO
			if (type == ItemType.TYPE_BOMB) {
				clickSmileFaceCount ++;
				currentScore += ((ItemN) view).getValue();
			}else if (type == ItemType.TYPE_BOTTLE) {
				
				gameCountDownTimer.cancel();
				gameCountDownTimer.start();
				progressBar.setProgress(60);
			}
			
			currentScoreView.setText("Score: " + currentScore);
			
		}
		
	}
	
	private class GameCountDownTimer extends CountDownTimer{

		public GameCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			
		}

		@Override
		public void onFinish() {
			gameView.randomResetAllItems();
			progressBar.setProgress(60);
			
			Toast.makeText(MainActivity.this, "game over", Toast.LENGTH_LONG).show();
			
		}

		@Override
		public void onTick(long millisUntilFinished) {
			
			progressBar.setProgress((int) (millisUntilFinished / 1000));
			gameView.randomRestParItems();
		}
		
	}

}
