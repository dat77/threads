package com.gmail.threads;

import java.math.BigInteger;

public class MathFunctions {

	public static BigInteger factorial(int n) {
		BigInteger result = BigInteger.valueOf(1);
		for (int i = 1; i <= n; i++) {
			result = result.multiply(BigInteger.valueOf(i));
		}
		return result;
	}

	public static long arraySum(int[] arrayInt, int startIndex, int offset) {
		long result = 0;
		for (int i = startIndex; i < startIndex+offset; i++) {
			result +=arrayInt[i];
		}
		return result;
	}

}
