package data_structures;

public class KeyValuePair<K extends Comparable<K>, V> implements Comparable<KeyValuePair<K, V>>
{
	private K key;
	
	private V value;
	
	public KeyValuePair(K pKey, V pValue)
	{
		key = pKey;
		value = pValue;
	}
	
	public K getKey()
	{
		return key;
	}
	
	public V getValue()
	{
		return value;
	}
	
	public void setValue(V newValue)
	{
		value = newValue;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int compareTo(KeyValuePair arg0) {
		return getKey().compareTo((K) arg0.getKey());
	}
}
