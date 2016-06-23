package com.example.weather.connection.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {
	public static Class<?> getClassWithNoError(String className) {
		Class<?> ret  = null;
		try {
			ret = Class.forName(className);
		} catch (Exception ex) {
			// keep empty!
		}
		return ret;
	}



	  
	public static DeclareObjectBean packageADeclareObject(Object o) {
		return DeclareObjectBean.packageADeclareObject(o);
	}
	
	static Class<?>[] BasicType  = new Class<?>[] {
		Character.class,
		Boolean.class,
		Integer.class,
		Long.class,
		Short.class,
		Byte.class,
		String.class,
		Double.class,
		Float.class
		
	};
	
	public static boolean isBasicType(Class<?> cls) {
		boolean ret = false;
		if (cls != null) {
			if (cls.isPrimitive()) {
				ret = true;
			} else {
				for (Class<?> item : BasicType) {
					if (cls.equals(item)) {
						ret = true;
						break;
					}
				}
			}
		}
		return ret;
	}
	
	public static Field getDeclaredFieldContainSuperClass(Class<?> cls, String name) {
		Field ret = null;
		if (cls != null && !Object.class.equals(cls)) {
			try {ret = cls.getDeclaredField(name);} catch (NoSuchFieldException e) {}
			if (ret == null) {
				ret = getDeclaredFieldContainSuperClass(cls.getSuperclass(), name);
			}
		}
		return ret;
	}




	public static List<Method> getMethods(Class<?> cls, String methodName) {
		List<Method> ret =  null;
		// TODO Auto-generated method stub
		
		if (cls != null && methodName != null) {
			ret = new ArrayList<Method>();
			for (Method m : cls.getMethods()) {
				if (m.getName().equals(methodName)) {
					ret.add(m);
				}
			}
		}
			
		return ret ;
	}

}

