package com.wechat.utils;

import java.util.concurrent.TimeUnit;

public class TestThreadInner {

	public void printStr(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				int iiii = 0;
				while (true){
					try {
						iiii++;
						System.err.println("现在的I是: " + iiii + ";");
						TimeUnit.MILLISECONDS.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
