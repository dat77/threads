package com.gmail.threads.math;

public class FactorialThread implements Runnable {
	
	private int number;

	public FactorialThread(int number) {
		this.number = number;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" result: "+MathFunctions.factorial(number));
	}

}
