package data_structures;

import java.util.Iterator;

import data_structures.Stack;

public class HashTableSeparateChaining<K extends Comparable<K>, V>
{
	public static final double MAX_AVERAGE_LIST_SIZE = 5.0;
	
	public static final int MAX_SIZE = 127;
	
	private LinkedList<KeyValuePair<K, V>>[] array;
	
	private LinkedList<K> keys;
	
	private int length;
	
	public HashTableSeparateChaining()
	{
		this(MAX_SIZE);
	}
	
	public HashTableSeparateChaining(int m)
	{
		array = (LinkedList<KeyValuePair<K, V>>[]) new LinkedList[primeVersionOf(m)];
		for(int i = 0; i < array.length; i++)
		{
			array[i] = new LinkedList<KeyValuePair<K, V>>();
		}
		keys = new LinkedList<K>();
		length = array.length;
	}
	
	public LinkedList<KeyValuePair<K, V>>[] getArray()
	{
		return array;
	}
	
	public LinkedList<K> getKeys()
	{
		return keys;
	}
	
	public int getLength()
	{
		return length;
	}
	
	private int hash(K x)
	{
		return (x.hashCode() & 0x7fffffff) % length;
	}
	
	public void put(K key, V value)
	{
		LinkedList<KeyValuePair<K, V>> listaSeleccionada = array[hash(key)];
		if(listaSeleccionada.getHead() == null)
		{
			listaSeleccionada.addAtHead(new KeyValuePair(key, value));
			keys.addAtHead(key);
			if(keys.size()/length > MAX_AVERAGE_LIST_SIZE)
			{
				rehash();
			}
		}
		else
		{
			Node<KeyValuePair<K, V>> actual = listaSeleccionada.getHead();
			boolean added = false;
			while(actual != null && !added)
			{
				if(actual.getElement().getKey().equals(key))
				{
					actual.getElement().setValue(value);
					added = true;
				}
				actual = actual.getNext();
			}
			if(actual == null)
			{
				listaSeleccionada.addAtHead(new KeyValuePair(key, value));
				keys.addAtHead(key);
				if(keys.size()/length > MAX_AVERAGE_LIST_SIZE)
				{
					rehash();
				}
			}
		}
	}
	
	public V get(K key)
	{
		LinkedList<KeyValuePair<K, V>> listaSeleccionada = array[hash(key)];
		if(listaSeleccionada.getHead() == null)
			return null;
		Node<KeyValuePair<K, V>> actual = listaSeleccionada.getHead();
		while(actual != null)
		{
			if(actual.getElement().getKey().equals(key))
				return actual.getElement().getValue();
			actual = actual.getNext();
		}
		return null;
	}
	
	public boolean isEmpty()
	{
		boolean isEmpty = true;
		
		for(int i = 0; i < length; i++)
			if(array[i].size() != 0)
				isEmpty = false;
		
		return isEmpty;
	}
	
	public V delete(K key)
	{
		LinkedList<KeyValuePair<K, V>> listaSeleccionada = array[hash(key)];
		if(listaSeleccionada.getHead() == null)
			return null;
		Node<KeyValuePair<K, V>> actual = listaSeleccionada.getHead();
		while(actual != null)
		{
			if(actual.getElement().getKey().equals(key))
			{
				if(actual.getPrevious() != null && actual.getNext() != null)
				{
					actual.getPrevious().setNext(actual.getNext());
					actual.getNext().setPrevious(actual.getPrevious());
				}
				if(actual.getPrevious() == null)
				{
					listaSeleccionada.setHead(actual.getNext());
					if(actual.getNext() != null)
						actual.getNext().setPrevious(null);
				}
				if(actual.getNext() == null)
				{
					listaSeleccionada.setlast(actual.getPrevious());
					if(actual.getPrevious() != null)
						actual.getPrevious().setNext(null);
				}
				return actual.getElement().getValue();
			}
			actual = actual.getNext();
		}
		return null;
	}
	
	public Iterator<K> keys()
	{
		return keys.iterator();
	}
	
	public void rehash()
	{
		HashTableSeparateChaining<K, V> newHashTable = new HashTableSeparateChaining<K, V>(primeVersionOf(length * 2));
		
		for(int i = 0; i < length; i++)
		{
			Node<KeyValuePair<K, V>> actual = array[i].getHead();
			while(actual != null)
			{
				newHashTable.put(actual.getElement().getKey(), actual.getElement().getValue());
				actual = actual.getNext();
			}
		}
		
		array = newHashTable.getArray();
		keys = newHashTable.getKeys();
		length = newHashTable.getLength();
	}
	
	public boolean contains(K pKey)
	{
		return get(pKey) != null;
	}
	
	private boolean isPrime(int num)
	{
		boolean isPrime = true;
		
		for(int i = 2; i < num && isPrime; i++ )
		{
			if(num % i == 0)
				isPrime = false;
		}
		
		return isPrime;
	}
	
	private int primeVersionOf(int num)
	{
		if(isPrime(num))
			return num;
		else
			return primeVersionOf(num + 1);
	}
	
	public Iterator<KeyValuePair<K, V>> iterator()
	{
		Stack<KeyValuePair<K, V>> stack = new Stack<KeyValuePair<K, V>>();
		
		for(int i = 0; i < array.length; i++)
		{
			Iterator<KeyValuePair<K, V>> it = array[i].iterator();
			while(it.hasNext())
			{
				stack.push(it.next());
			}
		}
		
		return stack.iterator();
	}
	
//	public static void main(String[] args)
//	{
//		HashTableSeparateChaining<Integer, String> table = new HashTableSeparateChaining<Integer, String>();
//		table.put(1, "value: 1");
//		table.put(2, "value: 2");
//		table.put(3, "value: 3");
//		table.put(4, "value: 4");
//		table.put(5, "value: 5");
//		Iterator<KeyValuePair<Integer, String>> it = table.iterator();
//		while(it.hasNext())
//			System.out.println(it.next().getValue());
//		
//		System.out.println("\n\n");
//		table.delete(1);
//		table.delete(3);
//		table.delete(5);
//		it = table.iterator();
//		while(it.hasNext())
//			System.out.println(it.next().getValue());
//	}
}