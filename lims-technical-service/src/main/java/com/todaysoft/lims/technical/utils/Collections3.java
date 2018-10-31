/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.todaysoft.lims.technical.utils;

import com.google.common.collect.Lists;



import java.util.*;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Collections工具集.
 * 在JDK的Collections和Guava的Collections2后, 命名为Collections3.
 * @author calvin
 * @version 2013-01-15
 */
@SuppressWarnings("rawtypes")
public class Collections3 {

	/**
	 * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
	 * 
	 * @param collection 来源集合.
	 * @param keyPropertyName 要提取为Map中的Key值的属性名.
	 * @param valuePropertyName 要提取为Map中的Value值的属性名.
	 */
	@SuppressWarnings("unchecked")
	public static Map extractToMap(final Collection collection, final String keyPropertyName,
			final String valuePropertyName) {
		Map map = new HashMap(collection.size());

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName),
						PropertyUtils.getProperty(obj, valuePropertyName));
			}
		} catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return map;
	}

	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	@SuppressWarnings("unchecked")
	public static List extractToList(final Collection collection, final String propertyName) {
		List list = new ArrayList(collection.size());

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String extractToString(final Collection collection, final String propertyName, final String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
	 */
	public static String convertToString(final Collection collection, final String separator) {
		return StringUtils.join(collection, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
	 */
	public static String convertToString(final Collection collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Collection collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * 取得Collection的第一个元素，如果collection为空返回null.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 获取Collection的最后一个元素 ，如果collection为空返回null.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		//当类型为List时，直接取得最后一个元素 。
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		//其他类型通过iterator滚动到最后一个元素.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 返回a+b的新List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}

	/**
	 * 判断collectin是否为空
	 * @param collection
	 * @return
     */
	public static boolean isNotEmpty(Collection collection) {
		if (collection == null || collection.size() ==0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断多个collection是否为空
	 * @param collection
	 * @return
     */
	public static boolean isNotEmpty(Collection... collection) {
		boolean isNotEp = false;
		for (Collection collection1 : collection) {
			if (isNotEmpty(collection1)) {
				isNotEp = true;
				break;
			}
		}
		return isNotEp;
	}


	/**
	 * 判断多个数组是否为空
	 * @param collection
	 * @param <E>
	 * @return
     */
	public static <E> boolean isNotEmpty(E[]... collection) {
		boolean isNotEp = false;
		for (E[] collection1 : collection) {
			if (isNotEmpty(collection1)) {
				isNotEp = true;
				break;
			}
		}
		return isNotEp;
	}

	/**
	 * 判断数组是否为空
	 * @param t
	 * @param <E>
     * @return
     */
	public  static <E> boolean isNotEmpty(E[] t) {
		if (t == null || t.length == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 数组转list
	 * @param t
	 * @param <T>
     * @return
     */
	public static <T> List<T> arrayToList(T[] t){
		if (isNotEmpty(t)) {
			return Lists.newArrayList(t);
		}
		return Lists.newArrayList();
	}

	public  static <T> boolean isNotEmptyContent(T[] t) {
		if (t == null || t.length == 0) {
			return false;
		}
		for (T t1: t){
			if (t1 == null) {
				return false;
			}
			if (StringUtils.isEmpty(t1.toString())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断里面值是不是为空的
	 * @param collection
	 * @param <T>
     * @return
     */
	public  static <T> boolean isNotEmptyContent(Collection collection) {
		if (isNotEmpty(collection)) {

			if(collection.stream().filter(x -> x != null).count() > 0){
				return  true;
			}
		}
		return false;
	}

	public static <T> T getOne(Collection<T> collection) {
		if (collection.stream().findFirst().isPresent()) {
			return collection.stream().findFirst().get();
		}
		return null;
	}


}
