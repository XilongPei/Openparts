package com.cnpc.framework.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSonHelper {
	static final Logger log = LoggerFactory.getLogger(com.cnpc.framework.utils.JSonHelper.class);
	private static final Map PRIMITIVE_MAP;
	private static final Set NUMBER_MAP;

	static {
		PRIMITIVE_MAP = new HashMap();
		PRIMITIVE_MAP.put(Float.TYPE, java.lang.Float.class);
		PRIMITIVE_MAP.put(Double.TYPE, java.lang.Double.class);
		PRIMITIVE_MAP.put(Integer.TYPE, java.lang.Integer.class);
		PRIMITIVE_MAP.put(Long.TYPE, java.lang.Long.class);
		PRIMITIVE_MAP.put(Byte.TYPE, java.lang.Byte.class);
		PRIMITIVE_MAP.put(Short.TYPE, java.lang.Short.class);
		PRIMITIVE_MAP.put(Character.TYPE, java.lang.Character.class);
		PRIMITIVE_MAP.put(Boolean.TYPE, java.lang.Boolean.class);
		NUMBER_MAP = new HashSet(PRIMITIVE_MAP.values());
		NUMBER_MAP.add(java.math.BigDecimal.class);
		NUMBER_MAP.add(java.math.BigInteger.class);
	}

	public JSonHelper() {
	}

	public static Class getClass(String className)
			throws ClassNotFoundException {
		if (className.indexOf('.') == -1)
			if (className.equalsIgnoreCase("date"))
				className = (new StringBuilder()).append("java.util.").append(
						StrUtil.capitalize(className)).toString();
			else
				className = (new StringBuilder()).append("java.lang.").append(
						StrUtil.capitalize(className)).toString();
		Class clazz = Class.forName(className);
		return clazz;
	}

	public static Object getValue(String type, Object rawValue)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, ParseException {
		return getValue(getClass(type), rawValue);
	}

	public static Object getValue(Class type, Object rawValue)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException, ParseException {
		int flag = 0;
		Object value;
		if (java.util.Date.class.equals(type) || java.util.Date.class.equals(type.getSuperclass())) {
			value = getDiversedDateValue(rawValue, type);
			flag = 1;
		} else if (java.lang.String.class.equals(type)) {
			value = getString(rawValue);
			flag = 2;
		} else if (type.isPrimitive()) {
			value = getNumberValue(rawValue, getWrappedClass(type));
			flag = 3;
		} else if (NUMBER_MAP.contains(type)) {
			value = getNumberValue(rawValue, type);
			flag = 4;
		} else {
			value = rawValue;
		}
		log.debug("Set [{},{}] with value {}", new Object[] { type,
				Integer.valueOf(flag), value });
		return value;
	}

	private static Object getDiversedDateValue(Object raw, Class clazz)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException, ParseException {
		Object tmp;		
		if (raw.toString().indexOf(':') > 0)
			tmp = DateUtil.parse(raw.toString(), DateUtil.formatStr_yyyyMMddHHmmss);
		else
			tmp = DateUtil.parse(raw.toString(), DateUtil.formatStr_yyyyMMdd);
		long time = ((Date) tmp).getTime();
		Constructor constructor = clazz
				.getConstructor(new Class[] { Long.TYPE });
		Object value = constructor.newInstance(new Object[] { Long
				.valueOf(time) });
		return value;
	}

	private static Class getWrappedClass(Class clazz) {
		Class wrappedClass = (Class) PRIMITIVE_MAP.get(clazz);
		log
				.debug("original class [{}], wrapped with [{}]", clazz,
						wrappedClass);
		return wrappedClass;
	}

	private static Object getNumberValue(Object raw, Class clazz)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		String tmp = getString(raw);
		if (tmp.length() == 0) {
			return null;
		} else {
			Constructor constructor = clazz.getConstructor(new Class[] { java.lang.String.class });
			Object value = constructor.newInstance(new Object[] { tmp });
			return value;
		}
	}

	private static String getString(Object o) {
		return String.valueOf(o).trim();
	}

	
}