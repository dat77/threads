package com.gmail.threads.math;

import java.math.BigInteger;
import java.util.Random;

public class MathFunctions {

	public static BigInteger factorial(int n) {
		BigInteger result = BigInteger.valueOf(1);
		for (int i = 1; i <= n; i++) {
			result = result.multiply(BigInteger.valueOf(i));
		}
		return result;
	}

	public static void arrayRandomValues(int[] arrayInt, int range) {
		Random rn = new Random();
		for (int i = 0; i < arrayInt.length; i++) {
			arrayInt[i] = (rn.nextInt() > 0) ? rn.nextInt(range) : (-1)*rn.nextInt(range);
		}
	}

	public static long arraySum(int[] arrayInt, int startIndex, int offset) {
		long result = 0;
		for (int i = startIndex; i < startIndex + offset; i++) {
			result += arrayInt[i];
		}
		return result;
	}

	public static void arraySortInsertion(int[] arrayInt, int startIndex, int offset) {
		for (int i = startIndex; i < arrayInt.length - 1; i = i + offset) {
			for (int j = Math.min(i + offset, arrayInt.length - 1); j - offset >= 0; j = j - offset) {
				if (arrayInt[j - offset] > arrayInt[j]) {
					int tmp = arrayInt[j];
					arrayInt[j] = arrayInt[j - offset];
					arrayInt[j - offset] = tmp;
				} else {
					break;
				}
			}
		}
	}

}
