package com.newapp;

import java.io.Serializable;
import java.util.*;

import android.graphics.*;

//model
public class GameData implements Serializable {
				
		public static final double VELOCITY = 10;

		long gameTime;
		String hud;
		String message;
		public void setMessage(String m){ message = m; }

		public Point sq;
		public float mag;
		
		public Point topleft;
		public Point dimensions;

		ArrayList<Plane> planes;

		public void setDimensions(Rect d){ 
				topleft = new Point(d.left, d.top);
				dimensions = new Point(d.right, d.bottom);
		}

		public void setPoint(float x, float y){
				sq = new Point(x, y);
		}

		public GameData(){
				gameTime = 0;
				hud = "";
				message = "";
				sq = new Point(550, 300);
				mag = 0.0f;

				planes = new ArrayList<Plane>();
				newPlane(.5f);
				newPlane(.67f);
				newPlane(.75f);
				newPlane(1.0f);

		}


		public void newPlane(float mr){
				Plane pl = new Plane(mr);
				for(int i = 0; i <= 20; i++){
						pl.add(new Point(Math.random()*2000, Math.random()*600));
				}
				planes.add(pl);
		}

		public void update(long dt){

				topleft.x += VELOCITY;

				if(topleft.x >= 5000){
						topleft.x = -2500;
				}
				
				gameTime += dt;
				hud = "GameTime: " + gameTime + ", dt: " + dt;
				hud += "         Ccanvas Size: " + dimensions.x + " by "+ dimensions.y; 

				mag *= .99;
		}
		
		public void draw(Canvas canvas){
				Paint p = new Paint();
				p.setColor(Color.GREEN);
				p.setTextSize(24);

				//draw layers
				for(Plane pl : planes){
						pl.draw(canvas, topleft);
				}
				
				//draw 'player'
				canvas.drawCircle(sq.x, sq.y, mag, p);

				//draw hud
				canvas.setDensity(2);
				canvas.drawText(hud, 10, 20, p);
				canvas.drawText(message, 10, 40, p);

		}


		//a point on a plane
		public static class Point implements Serializable {
				float x;
				float y;
				public Point(float a, float b){
						x = a;
						y = b;
				}

				public Point(double a, double b){
						x = (float)a;
						y = (float)b;
				}
		}

		//a plane of objects
		public static class Plane implements Serializable { 
				ArrayList<Point> points;
				
				//defines how much this layer moves compared
				//to others.... a ratio of '0' is static
				float movementratio;

				public void add(Point p){
						points.add(p);
				}

				public Plane(float mr){
						points = new ArrayList<Point>();
						movementratio = mr;
				}

				public void draw(Canvas canvas, Point pos){
						Paint p = new Paint();
						p.setColor(Color.WHITE);
						for(Point pt : points){
								canvas.drawCircle(pt.x - pos.x*movementratio, 
																	pt.y - pos.y*movementratio,
																	4.0f, p);
						}
				}
		}
}
