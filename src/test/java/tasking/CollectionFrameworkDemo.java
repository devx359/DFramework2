package tasking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import org.testng.annotations.Test;


public class CollectionFrameworkDemo {
	
	
	@Test
	public void fun() throws InterruptedException
	{
		System.out.println("-------Array List--------");
		ArrayList<String> arr= new ArrayList<String>(); 
		arr.add("c");
		arr.add("b");		
		arr.add("a");
		arr.add("a");
		arr.add("a");
		
		for(int i=0;i<5;i++)
		{
			System.out.println(arr.get(i));
		}
		System.out.println("-------Linked List--------");
		LinkedList<String> ll = new LinkedList<String>();
		ll.add("c1");
		ll.add("a1");
		ll.add("b1");		
		ll.add("d1");
		ll.add("a1");
		ll.add("a1");
		
		Iterator<String> llit = ll.iterator();
		
		while(llit.hasNext())
		{
			System.out.println(llit.next());
		}
		System.out.println("--------HashSet------------");
		/*1)doesnot maintains order of insertion
		2)Doesnt allow duplicates*/
		HashSet<String> hs= new HashSet<String>();
		hs.add("c1");
		hs.add("a1");
		hs.add("b1");		
		hs.add("d1");
		hs.add("a1");
		hs.add("a1");
		
		Iterator<String> hsit = hs.iterator();
		while(hsit.hasNext())
		{
			System.out.println(hsit.next());
		}
		System.out.println("--------LinkedHashSet------------");
		/*1) maintains order of insertion
		2)Doesnt allow duplicates*/
		LinkedHashSet<String> lhs= new LinkedHashSet<String>();
		lhs.add("c1");
		lhs.add("a1");
		lhs.add("b1");		
		lhs.add("d1");
		lhs.add("a1");
		lhs.add("a1");
		
		Iterator<String> lhsit = lhs.iterator();
		
		while(lhsit.hasNext())
		{
			System.out.println(lhsit.next());
		}
		System.out.println("--------TreeSet------------");
		/*1)Sorted
		2)Doesnt allow duplicates */
		
		TreeSet<String> ts= new TreeSet<String>() ;
		ts.add("c1");
		ts.add("a1");
		ts.add("b1");		
		ts.add("d1");
		ts.add("a1");
		ts.add("a1");
		
		Iterator<String> tsit = ts.iterator();
		
		while(tsit.hasNext())
		{
			System.out.println(tsit.next());
		}
		
		System.out.println("--------TreeMap------------");
		TreeMap<String,String> tmp= new TreeMap<String,String>(); 
		tmp.put("b1", "aaaaa");
		tmp.put("a2", "bbbb");		
		tmp.put("c1", "caaa");
		
		
		
		
	}

}
