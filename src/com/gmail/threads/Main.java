package com.gmail.threads;

import java.util.Random;

public class Main {

	public static void main(String[] args) {

		/**
		 * 1) Создайте сто потоков, которые будут вычислять факториал числа, равного
		 * номеру этого потока, и выводить результат на экран.
		 */

//		for (int i = 1; i <= 100; i++) {
//			Thread threadI = new Thread(new FactorialThread(i), "Tread #"+i);
//			threadI.start();
//		}

		/**
		 * 2) Написать код для многопоточного подсчета суммы элементов массива целых
		 * чисел. Сравнить скорость подсчета с простым алгоритмом.
		 */

//		int[] arrayInt = {0,6,4,-9,6,5,5,4,43,223,0,56,-99,
//				-567,34,555,221,-6666,8900776,34,31,-43,
//				-4678,997,-5672,-5673,9665,34422,45564,
//				44,31,5,0,45,776,443,2222,55567,2321,
//				56667,7654443,556677889,34434,54432,43326,
//				77654,5433,432,113445,44567,85424,34467,-15};

		int[] arrayInt = new int[1000000];
		Random rn = new Random();
		for (int i = 0; i < arrayInt.length; i++) {
			arrayInt[i] = rn.nextInt();
		}

		long threadStart = System.nanoTime();
		System.out.println("Array sum is: " + MathFunctions.arraySum(arrayInt, 0, arrayInt.length));
		long threadTerminated = System.nanoTime();
		System.out.println("for single thread it took " + (threadTerminated - threadStart) + " ns");

		threadStart = System.nanoTime();
		int threadCount = rn.nextInt(32) + 1;
		int offset = arrayInt.length / (threadCount - 1);
		int lastOffset = arrayInt.length % (threadCount - 1);
		System.out.println("length " + arrayInt.length + " offset " + offset + " lastOffset " + lastOffset);
		ArraySumThread[] arrayThreads = new ArraySumThread[threadCount];
		for (int i = 0; i < threadCount - 1; i++) {
			arrayThreads[i] = new ArraySumThread(arrayInt, i * offset, offset, "Tread #" + i);
			arrayThreads[i].start();
		}
		arrayThreads[threadCount - 1] = new ArraySumThread(arrayInt, (threadCount - 1) * offset, lastOffset,
				"Tread #" + (threadCount - 1));
		arrayThreads[threadCount - 1].start();
		for (int i = 0; i < threadCount; i++) {
			try {
				arrayThreads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long arraySum = 0;
		for (int i = 0; i < threadCount; i++) {
			arraySum += arrayThreads[i].getResult();
		}
		threadTerminated = System.nanoTime();
		System.out.println("Array sum is: " + arraySum);
		System.out.println("for " + threadCount + " threads it took " + (threadTerminated - threadStart) + " ns");

	}

}
