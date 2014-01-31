package com.newapp;

import android.app.Activity;
import android.os.Bundle;

import android.view.*;
import android.graphics.*;
import android.content.Context;

import com.newapp.GameCanvas;
import com.newapp.GameLoop;
import com.newapp.GameData;

public class NewApp extends Activity implements SurfaceHolder.Callback
{
		GameLoop process;
		GameCanvas stage;
		GameData gamedata;

		boolean surface_exists;

    /** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState){
				super.onCreate(savedInstanceState);
				System.out.println("NEWAPP::onCreate");

				//create viewport to see game
				stage = new GameCanvas(this);
				stage.getHolder().addCallback(this);

				//try to recover saved data if it exists
				if(savedInstanceState != null){
						System.out.println("NEWAPP::Resuming stored gamedata");
						gamedata = (GameData)savedInstanceState.getSerializable("gamedata");
				}
				
				//if no saved data found, create new data
				if(gamedata == null){
						System.out.println("NEWAPP::No gamedata found, creating new data");
						gamedata = new GameData();
				}
				
				//place canvas and data into a process
				process = new GameLoop(stage, gamedata);
				setContentView(stage);
				
    }

		@Override public void onResume(){
				super.onResume();
				System.out.println("NEWAPP::OnResume");

				if (surface_exists)
						if(process != null) process.start();
						else System.out.println("NEWAPP::Error, process is null. Cannot start");
		}

		@Override public void onRestart(){
				super.onRestart();
				System.out.println("NEWAPP::OnRestart");
		}
		
		@Override public void onStop(){
				super.onStop();
				System.out.println("NEWAPP::OnStop");
				process.setRunning(false);
		}

		@Override protected void onSaveInstanceState(Bundle outState){
				System.out.println("NEWAPP::Saving Instance State");
				if(process != null) process.setRunning(false);
				else System.out.println("NEWAPP::Error, process is null. Cannot stop");

				outState.putSerializable("gamedata", gamedata);
		}

		@Override public void onBackPressed(){
				//overrides back button presses, keeps player from accidentally exitting
		}

		//Surfaceholder.Callback Methods
		@Override
				public void surfaceCreated(SurfaceHolder holder){
				System.out.println("NEWAPP::Surface created.");
				surface_exists = true;
				if (process != null) process.start();
				else System.out.println("NEWAPP::Error, process is null. Cannot start");
		}
		
		@Override	
				public void surfaceChanged(SurfaceHolder holder, 
																	 int format, int width, int height){}
		
		@Override
				public void surfaceDestroyed(SurfaceHolder holder){
				System.out.println("NEWAPP::Surface destroyed.");
				surface_exists = false;
				process.stop();
		}
		//end surfaceholder Callback methods

}
