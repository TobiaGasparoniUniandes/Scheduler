package data_structures;
import java.util.Iterator;

public class LinkedList<T>
{
	
	private Node<T> head;

	private Node<T> last;

	private int size;

	public LinkedList()
	{
		last = null;
		head = null;
		size = 0;
	}

	public Integer size() 
	{
		int num = 0;
		
		Iterator<T> it = iterator();
		while(it.hasNext())
		{
			it.next();
			num++;
		}
		
		return num;
	}

	public void addAtHead(T node)
	{
		Node<T> nuevo = new Node<T>(node);
		nuevo.setNext(head);
		head = nuevo;
		size++;
		if(size == 2)
		{
			last = head.getNext();
		}
	}

	public void addLast(T t)
	{
		Node<T> nuevo = new Node<T>(t);
		if(head == null)
			head = nuevo;
		else if(last == null)
		{
			last = nuevo;
			head.setNext(last);
			last.setPrevious(head);
		}
		else
		{
			last.setNext(nuevo);
			nuevo.setPrevious(last);
			last = nuevo;
		}
		size++;
	}

	public Node<T> addAtEnd(T node)
	{
		if(head != null)
		{
			Node<T> actual = head;
			while(actual.getNext() != null)
			{
				actual = actual.getNext();
			}
			try
			{
				Node<T> nuevo = new Node<T>(node);
				actual.setNext(nuevo);
				size++;
				return nuevo;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				Node<T> nuevo = new Node<T>(node);
				head = nuevo;
				size++;
				return nuevo;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public Node<T> getHead()
	{
		return head;
	}
	
	public void setHead(Node<T> node)
	{
		head = node;
	}
	
	public Node<T> getLast()
	{
		return last;
	}
	
	public void setlast(Node<T> node)
	{
		last = node;
	}

	public T deletehead()
	{
		if(head != null)
		{
			T actual = head.getElement();
			head = head.getNext();
			size--;
			return actual;
		}
		return null;
	}

	public T deleteHeadLast()
	{
		Node<T> eliminado = head;
		head = head.getNext();
		if(head == null)
		{
			last = null;
		}
		size--;
		return eliminado.getElement();
	}
	
	public Iterator<T> iterator()
	{
        return new Iterator<T>() {

            Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T data = current.getElement();
                    current = current.getNext();
                    return data;
                }
                return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove not implemented.");
            }

        };

    }
	
	public void printElements()
	{
//		Node<T> actual = head;
//		while(actual != null)
//		{
//			System.out.println("Previous: " + actual.getPrevious());
//			System.out.println("Actual: " + actual);
//			System.out.println("Next: " + actual.getNext());
//			actual = actual.getNext();
//		}
		Iterator<T> it = iterator();
		while(it.hasNext())
			System.out.println(it.next());
	}
	
	public static void main(String[] args)
	{
		LinkedList<Integer> ints = new LinkedList<Integer>();
		ints.addAtHead(1);
		ints.addLast(2);
		ints.addLast(3);
		ints.addLast(4);
		ints.addLast(5);
		ints.printElements();
	}
}