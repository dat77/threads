package com.gmail.threads.math;

public class ArraySortShellThread implements Runnable {

	private int[] arrayInt;
	private int startIndex;
	private int offset;

	public ArraySortShellThread(int[] arrayInt, int startIndex, int offset) {
		this.arrayInt = arrayInt;
		this.startIndex = startIndex;
		this.offset = offset;
	}

	@Override
	public void run() {
		MathFunctions.arraySortInsertion(arrayInt, startIndex, offset);
		//System.out.println(Thread.currentThread().getName() + " start " + startIndex + " offset " + offset);
	}

}
