package com.tinhat.starshiprunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tinhat.android.Pool;


public class PooledBufferedList<T> extends Pool<T> {

	public List<T> buffer = new ArrayList<T>();
	public List<T> objects = new ArrayList<T>();
	
	int firstEndOrdinal;
	Iterator<T> freeing;
	T object;
	
	public PooledBufferedList(com.tinhat.android.Pool.PoolObjectFactory<T> factory,
			int maxSize) {
		super(factory, maxSize);
	}
	
	public void setFirstEndOrdinal(int index){
		firstEndOrdinal = index;
	}
	
	public void swapBuffer(){
		int i=0;
		
		freeing = objects.iterator();
		 
		while(freeing.hasNext()){
			object = freeing.next();
			free(object);
			freeing.remove();
			i++;
			
			if(i==firstEndOrdinal)
				break;
		}

		firstEndOrdinal = objects.size();
		objects.addAll(buffer);
		buffer.clear();
		
	}
	
	public void decrementFirstEndOrdnial(int index){
		if(index - 1 < firstEndOrdinal){
			firstEndOrdinal--;
		}
	}

}
