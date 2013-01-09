package com.tinhat.starshiprunner;

import java.util.ArrayList;
import java.util.List;

import com.tinhat.android.Pool;


public class PoolBufferList<T> extends Pool<T> {

	public List<T> buffer = new ArrayList<T>();
	public List<T> objects = new ArrayList<T>();
	
	public PoolBufferList(com.tinhat.android.Pool.PoolObjectFactory<T> factory,
			int maxSize) {
		super(factory, maxSize);
	}

}
