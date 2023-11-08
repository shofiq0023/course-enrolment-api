package com.shofiqul.utils;

import org.springframework.beans.BeanUtils;

public class Utility {
	public static <T> T copyProperties(Object source, Class<T> clazz) {
		T classInstance = null;
		
		try {
			classInstance = clazz.getDeclaredConstructor().newInstance();
			
			BeanUtils.copyProperties(source, classInstance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return classInstance;
	}
}
