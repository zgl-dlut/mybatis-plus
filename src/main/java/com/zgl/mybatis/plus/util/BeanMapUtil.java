package com.zgl.mybatis.plus.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zgl
 * @date 2019/10/12 下午11:09
 */
public class BeanMapUtil {
	/**
	 * 对象转为Map
	 * @param source
	 * @param ignoreProperties
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object source, String... ignoreProperties) {
		Class clazz = source.getClass();
		Map<String, Object> resultMap = new HashMap<>();

		try {
			Method[] methods = clazz.getMethods();
			List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
			for (Method readMethod : methods) {
				String propertyName = readMethod.getName();
				if(propertyName == null
						|| !propertyName.startsWith("get")
						|| propertyName.equals("getClass")){
					continue;
				}

				propertyName = propertyName.substring(propertyName.indexOf("get") + 3);
				propertyName = StringsUtil.toLowerCaseFirstOne(propertyName);
				if (ignoreList == null || !ignoreList.contains(propertyName)) {
					if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
						readMethod.setAccessible(true);
					}
					Object value = readMethod.invoke(source);
					if (value != null && StringUtils.isNotEmpty(value.toString())) {
						resultMap.put(propertyName, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not convert to map");
		}
		return resultMap;
	}
}
