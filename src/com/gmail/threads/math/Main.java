package com.gmail.threads.math;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

	public static void main(String[] args) {

		/**
		 * 1) Создайте �?то потоков, которые будут вычи�?л�?ть факториал чи�?ла, равного
		 * номеру �?того потока, и выводить результат на �?кран.
		 */
		
		Thread[] threadI = new Thread[100];

		for (int i = 1; i <= threadI.length; i++) {
			threadI[i-1] = new Thread(new FactorialThread(i), "Tread #"+i);
			threadI[i-1].start();
		}
		for (int i = 0; i < threadI.length; i++) {
			try {
				threadI[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("---------------------------------------------");
		
		/**
		 * 2) �?апи�?ать код дл�? многопоточного под�?чета �?уммы �?лементов ма�?�?ива целых
		 * чи�?ел. Сравнить �?коро�?ть под�?чета �? про�?тым алгоритмом.
		 */

		int[] arrayInt = new int[1000000];
		MathFunctions.arrayRandomValues(arrayInt, Integer.MAX_VALUE);

		long threadStart = System.nanoTime();
		System.out.println("Array sum is: " + MathFunctions.arraySum(arrayInt, 0, arrayInt.length));
		long threadTerminated = System.nanoTime();
		System.out.println("for single thread it has taken " + (threadTerminated - threadStart) + " ns");

		threadStart = System.nanoTime();
		Random rn = new Random();
		int threadCount = rn.nextInt(32) + 1;
		int offset = arrayInt.length / (threadCount - 1);
		int lastOffset = arrayInt.length % (threadCount - 1);
		// System.out.println("length " + arrayInt.length + " offset " + offset + "
		// lastOffset " + lastOffset);
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
				e.printStackTrace();
			}
		}
		long arraySum = 0;
		for (int i = 0; i < threadCount; i++) {
			arraySum += arrayThreads[i].getResult();
		}
		threadTerminated = System.nanoTime();
		System.out.println("Array sum is: " + arraySum);
		System.out.println("for " + threadCount + " threads it has taken " + (threadTerminated - threadStart) + " ns");
		System.out.println("---------------------------------------------");

		/**
		 * 3) �?апишите многопоточный вариант �?ортировки ма�?�?ива методом Шелла.
		 */

		arrayInt = new int[10000];
		MathFunctions.arrayRandomValues(arrayInt, 10000);
		int[] arrayIntClone = arrayInt.clone();

		threadStart = System.nanoTime();
		MathFunctions.arraySortInsertion(arrayInt, 0, 1);
		threadTerminated = System.nanoTime();
		//System.out.println(Arrays.toString(arrayInt));
		System.out.println("["+arrayInt[0] + "..." + arrayInt[arrayInt.length/2] + "..." + arrayInt[arrayInt.length-1] + "]");
		System.out.println("for single thread it has taken " + (threadTerminated - threadStart) + " ns");

		threadStart = System.nanoTime();
		offset = arrayInt.length / 2;
		threadCount = rn.nextInt(offset);
		ExecutorService executorForSort = Executors.newFixedThreadPool(threadCount);
		Future<?>[] shellThreads = new Future<?>[offset];
		while (offset >= 1) {
			for (int startIndex = 0; startIndex < offset; startIndex++) {
				shellThreads[startIndex] = executorForSort
						.submit(new ArraySortShellThread(arrayIntClone, startIndex, offset));
			}
			for (int startIndex = 0; startIndex < offset; startIndex++) {
				try {
					shellThreads[startIndex].get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			offset = offset / 2;
		}
		executorForSort.shutdown();
		threadTerminated = System.nanoTime();
		//System.out.println(Arrays.toString(arrayIntClone));
		System.out.println("["+arrayIntClone[0] + "..." + arrayIntClone[arrayIntClone.length/2] + "..." + arrayIntClone[arrayIntClone.length-1] + "]");
		System.out.println("for " + threadCount + " fixed concurrent threads it has taken "
				+ (threadTerminated - threadStart) + " ns");
		System.out.println("---------------------------------------------");
		
		

	}

}
