package com.arcesium.cache;


/**
 * @author gullapal
 *
 */
public interface ArcReaderCache<T, R> {
	
	/**
	 * Associates value with key in this cache. If the cache previously contained a 
	 * value associated with key, the old value is replaced by value. 
	 * 
	 * @param key
	 * @param value
	 */
	void put(T key, R value);
	
	/**
	 * If the specified key is not already associated with a value, associate it 
	 * with the given value. Otherwise return the value that already exists.
     * 
	 * @param key
	 * @param value
	 * @return
	 */
	R putIfAbsent(T key, R value);
	
	/**
	 * Returns the value associated with key in this cache, or null if there is no cached value for key.
	 * 
	 * @param key
	 * @return
	 */
	R get(T key);
	
	/**
	 * Removes the cached data, if present, for the given key
	 * 
	 * @param key
	 */
	boolean remove(T key);
	

	/**
	 * Removes the entry for a key only if currently mapped to a given value.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	boolean remove(T key, R value);
    
	/**
	 * Clears all the entries in the cache
	 */
	void clear();

}
