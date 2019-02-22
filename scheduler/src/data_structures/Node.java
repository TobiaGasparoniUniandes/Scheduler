package data_structures;
public class Node<T>
{
	private Node<T> next;
	
	public Node<T> previous;

	private T element;

	public Node(T pElement)
	{
		element = pElement;
		next = null;
		previous = null;
	}

	public Node<T> getNext()
	{
		return next;
	}
	
	public Node<T> getPrevious()
	{
		return previous;
	}

	public T getElement()
	{
		return element;
	}

	public void setNext(Node<T> pNode)
	{
		next = pNode;
	}
	
	public void setPrevious(Node<T> pNode)
	{
		previous = pNode;
	}

	public void setElement(T pElement)
	{
		element = pElement;
	}
}
