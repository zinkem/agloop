package com.newapp;

import android.view.*;
import android.graphics.*;
import android.content.Context;

import com.newapp.GameLoop;
import com.newapp.GameData;

//view
public class GameCanvas extends SurfaceView {
				
		GameData gdata;
		public GameData getData(){ return gdata; }
		public void setData(GameData gd){ gdata = gd; }

		public GameCanvas(Context c){
				super(c);
				System.out.println("GameCanvas Created");
		}

		@Override
				protected void onDraw(Canvas canvas){
				canvas.drawColor(Color.BLACK);
				gdata.draw(canvas);
		}
		@Override
				public boolean onTouchEvent(MotionEvent event){
				int act = event.getAction();

				
				float press = event.getPressure();
				gdata.mag = press*50;
				String message = "Pressure: " + press;
				
				if(act == MotionEvent.ACTION_UP){
						message += " Action Up";
				}

				if(act == MotionEvent.ACTION_DOWN){
						message += " Action DOWN";
				}

				gdata.setMessage(message);
				return true;
		}
		
}
