package com.wangwenchao.framework.util;

import java.util.Random;

public class RandomNumberGenerator {

	private static Random rand = new Random();
	
	public static int getRandIntBetween(int lowerBond, int upperBond){
		return rand.nextInt(upperBond - lowerBond) + lowerBond;
	}
	
	public static int GetRandInt(int upperBond) {
		return rand.nextInt(upperBond);
	}
}
