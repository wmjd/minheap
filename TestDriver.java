import data_structures.PriorityQueue;
//import data_structures.UnorderedLinkedListPriorityQueue;
//import data_structures.OrderedLinkedListPriorityQueue;
import data_structures.BinaryHeapPriorityQueue;
import java.util.Scanner;
import java.util.Iterator;

public class TestDriver {
	public static void main(String args[]){
				

		PriorityQueue<String> PQ = new BinaryHeapPriorityQueue<String>();	

		PQ.insert("2");
		//System.out.println(PQ.size());
		PQ.insert("3");
		PQ.insert("2");
		PQ.insert("-4");
		PQ.delete("2");
		System.out.println(PQ.peek());
		System.out.println(PQ.remove());
		System.out.println(PQ.remove());
		System.out.println(PQ.remove());
		System.out.println(PQ.remove());
		System.out.println(PQ.remove());
		System.out.println(PQ.peek());
		PQ.insert("3");
		PQ.clear();
		System.out.println(PQ.contains("3"));
		
/*		System.out.println(PQ.remove());
		Iterator it2 = PQ.iterator();
		while(it2.hasNext())
			System.out.println(it2.next());
		System.out.println(PQ.remove());
		System.out.println(PQ.size());

		}	*/
	}
}

