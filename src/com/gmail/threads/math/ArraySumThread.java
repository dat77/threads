package com.gmail.threads.math;

public class ArraySumThread extends Thread {
	
	private int[] arrayInt;
	private int startIndex;
	private int offset;
	private long result;
	
	public ArraySumThread(int[] arrayInt, int startIndex, int offset, String name) {
		super(name);
		this.arrayInt = arrayInt;
		this.startIndex = startIndex;
		this.offset = offset;
	}
	
	public long getResult() {
		return result;
	}

	@Override
	public void run() {
		result = MathFunctions.arraySum(arrayInt, startIndex, offset);
//		System.out.println(Thread.currentThread().getName()
//				+ " from "+startIndex + " to "+(startIndex+offset-1) + " result " + result);
	}

}
