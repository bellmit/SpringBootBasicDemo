package com.laison.erp.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;

public class CollectionUtils extends CollUtil {

	/**
	 * 两个集合去掉重复<br>
	 * 针对一个集合中存在多个相同元素的情况，计算两个集合中此元素的个数，保留两个集合中此元素个数差的个数<br>
	 * 例如：集合1：[a, b, c, c, c]，集合2：[a, b, c, c]<br>
	 * 结果：[c]，此结果中只保留了一个<br>
	 * 任意一个集合为空，返回另一个集合<br>
	 * 两个集合无交集则返回两个集合的组合
	 * 
	 * @param <T>
	 *            集合元素类型
	 * @param coll1
	 *            集合1
	 * @param coll2
	 *            集合2
	 * @return 结果的集合，返回 {@link ArrayList}
	 */
	public static <T> Collection<T> disjunction(final Collection<T> coll1, final Collection<T> coll2) {
		if (isEmpty(coll1)) {
			return coll2;
		}
		if (isEmpty(coll2)) {
			return coll1;
		}

		final ArrayList<T> result = new ArrayList<>();
		final Map<T, Integer> map1 = countMap(coll1);
		final Map<T, Integer> map2 = countMap(coll2);
		final Set<T> elts = newHashSet(coll2);
		elts.addAll(coll1);
		int m;
		for (T t : elts) {
			m = Math.abs(Convert.toInt(map1.get(t), 0) - Convert.toInt(map2.get(t), 0));
			for (int i = 0; i < m; i++) {
				result.add(t);
			}
		}
		return result;
	}

	/**
	 * 2个集合的差值
	 * [1,2,5,9]-[2,3,4,5]=[1,9]
	 * @param a
	 * @param b
	 * @return
	 */
	public static <O> Collection<O> subtract(final Iterable<? extends O> a, final Iterable<? extends O> b) {
		return org.apache.commons.collections4.CollectionUtils.subtract(a, b);
	}


}
