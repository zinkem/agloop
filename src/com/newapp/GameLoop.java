package com.newapp;

import android.view.*;
import android.graphics.*;

import com.newapp.GameData;
import com.newapp.GameCanvas;

//controller
public class GameLoop implements Runnable {

		GameCanvas surf;
		GameData gdata;
		boolean running;

		public GameLoop(GameCanvas s, GameData d){
				running = false;
				surf = s;
				gdata = d;
				surf.setData(gdata);
		}

		public void start(){
				setRunning(true);
		}
		
		public void stop(){
				setRunning(false);
		}

		public void setRunning(boolean b){
				running = b;
				if(b) new Thread(this).start();
		}

		public boolean isRunning(){ return running; }

		public void run(){
				Canvas c;
				long dt;
				
				SurfaceHolder sh = surf.getHolder();
				gdata.setDimensions(sh.getSurfaceFrame());

				long lastTime = System.currentTimeMillis();
				long thisTime = lastTime;
				running = true;
				while(running){
								
						//set 16 ms frames
						dt = 0;
						while(dt < 16){
								thisTime = System.currentTimeMillis();
								dt = thisTime - lastTime;
						}
						lastTime = thisTime;
						//end frame timer

						//update game data
						gdata.update(dt);

						//update screen (draw gamedata)
						c = null;
						try {
								c = sh.lockCanvas(null);
								synchronized(sh){
										surf.onDraw(c);
								}
						} finally {
								if(c != null){
										sh.unlockCanvasAndPost(c);
								}
						}
						//end main loop
				}
		}
}
