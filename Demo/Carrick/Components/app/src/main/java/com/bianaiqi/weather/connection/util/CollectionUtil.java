package com.bianaiqi.weather.connection.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionUtil {

	public static boolean isCollectionEmpty(Collection<?> c) {
		return c == null || c.size() == 0;
	}
	
	public static <T> T getFirstElemFromCollection(Collection<T> c) {
		T ret = null;
		if (!isCollectionEmpty(c)) {
			if (c instanceof List) {
				ret  = ((List<T>) c).get(0);
			} else {
				Iterator<T> it = c.iterator();
				if (it.hasNext()) {
					ret =it.next();
				}
			}
		}
		return ret;
	}
}


