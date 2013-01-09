package com.tinhat.starshiprunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.tinhat.framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
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
		} catch (IOException ex){
			//use defaults
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
}
