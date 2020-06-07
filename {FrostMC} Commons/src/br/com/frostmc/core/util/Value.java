package br.com.frostmc.core.util;

import java.lang.reflect.Field;

public class Value {
	
	public static Object getValue(String field, Class<?> clazz, Object instance) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			return f.get(instance);
		} catch (Exception exception) {

		}
		return null;
	}

	public static void setValue(String field, Object instance, Object value) {
		try {
			Field f = instance.getClass().getDeclaredField(field);
			f.setAccessible(true);
			f.set(instance, value);
		} catch (Exception exception) {

		}
	}

	public static Object getValue(String field, Object instance) {
		try {
			Field f = instance.getClass().getDeclaredField(field);
			f.setAccessible(true);
			return f.get(instance);
		} catch (Exception exception) {

		}
		return null;
	}

}
