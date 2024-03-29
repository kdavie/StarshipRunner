package com.tinhat.starshiprunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.util.Log;

import com.tinhat.framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public static int coins = 0;
	public static int weapon = 0;
	public final static int[] highscores = new int[] { 100, 80, 50, 30, 10 };
	
	public final static String file = ".starshiprunner";
	
	public static void load(FileIO files){
		BufferedReader stream = null;
		try {
			stream = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(stream.readLine());
			for(int i = 0; i < 5; i++){
				highscores[i] = Integer.parseInt(stream.readLine());
			}
			coins = Integer.parseInt(stream.readLine());
			weapon = Integer.parseInt(stream.readLine());
			
		} catch (IOException ex){
			Log.e("Settings", "IOException - Unable to load settings");
			Log.e("Settings", ex.getMessage());
		} catch (NumberFormatException ex){
			//use defaults
		} finally {
			try {
				if(stream != null){
					stream.close();
				}
			} catch (IOException ex){
				//do nothing
			}
		}
	}
	
	public static void save(FileIO files){
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));
			writer.write(Boolean.toString(soundEnabled) + '\n');
			for(int i = 0; i < 5; i++){
				writer.write(Integer.toString(highscores[i]) + '\n');
 			}
			writer.write(Integer.toString(coins) + '\n');
			writer.write(Integer.toString(weapon) + '\n');
		} catch (IOException e){
			//Use defaults
		}  finally {
			try {
				if(writer != null){
					writer.close();
				} 
			} catch (IOException e){
			}
		}
	}
	
	public static void addScore(int score){
		for(int i = 0; i < 5; i++){
			if(highscores[i] < score) {
				for(int j = 4; j > i; j--){
					highscores[j] = highscores[j-1];
				}
				highscores[i] = score;
				break;
			}
		}
	}
	
	public static void addCoins(int amount){
		coins += amount;
	}
}
