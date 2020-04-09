package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Scanner;

public class BinaryHeapPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E> {
	
	protected class Wrapper<E> implements Comparable<Wrapper<E>> {
		int id;
		E data;

		public Wrapper (E d) {
			id = serial++;
			data = d; 
		}
		public int compareTo(Wrapper<E> o){
			if(((Comparable<E>) data).compareTo(o.data) == 0)
				return id-o.id;
			return ((Comparable<E>) data).compareTo(o.data);
		}
	}

	protected Wrapper<E> a[];
	public int ac;
	protected int serial;


 // constructor
	public BinaryHeapPriorityQueue(){
		serial = 0;
		ac = 0;
		a = (Wrapper <E> []) new Wrapper[DEFAULT_MAX_CAPACITY];
	}


	 public static final int DEFAULT_MAX_CAPACITY = 4;

 // Inserts a new object into the priority queue. Returns true if
 // the insertion is successful. If the PQ is full, the insertion
 // is aborted, and the method returns false.
	public boolean insert(E object){
		if(isFull())
			return false;
		a[ac] = new Wrapper<E>(object);
		ascend(ac);
		ac++;
		return true;
	}

	public void ascend(int from){
		if(from == 0)
			return;
		Wrapper<E> child = a[from];
		int p = (from-1) / 2;
		Wrapper<E> parent = a[p];
		if(parent.compareTo(child) > 0){
			swap(p,from);
			ascend(p);
		}
	}

	public void swap(int i, int j){
		Wrapper<E> temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

 // Removes the object of highest priority that has been in the
 // PQ the longest, and returns it. Returns null if the PQ is empty.
	public E remove(){
		return removeAt(0);
	}

	public E removeAt(int i){
		if(ac <= i)
			return null;
		E subroot = a[i].data;
		a[i] = a[--ac];
		descend(i);
		return subroot;
	}

	public void descend(int p){
		int r = 2*p + 2;
		int l = 2*p + 1;
		if(r > ac) return;
		if(r == ac){
			if(a[l].compareTo(a[p]) < 0)
				swap(l,p);
			return;
		} else {
			Wrapper<E> parent = a[p];
			Wrapper<E> leftChild = a[l];
			Wrapper<E> rightChild = a[r];
			Wrapper<E> smallChild;
			int s;
			if(leftChild.compareTo(rightChild) < 0){
				smallChild = leftChild;
				s = l;
			} else {
				smallChild = rightChild;
				s = r;
			}
			if(smallChild.compareTo(parent) < 0){
				swap(p, s);
				descend(s);
			} 
		}
	}



 // Deletes all instances of the parameter obj from the PQ if found, and
 // returns true. Returns false if no match to the parameter obj is found.
	 public boolean delete(E obj){
		return true;
	}

 // Returns the object of highest priority that has been in the
 // PQ the longest, but does NOT remove it.
 // Returns null if the PQ is empty.
	public E peek(){
		return null;
	}




 // Returns true if the priority queue contains the specified element
 // false otherwise.
	 public boolean contains(E obj){
		return false;
	}

 // Returns the number of objects currently in the PQ.
 	public int size(){
		return ac;
	}

 // Returns the PQ to an empty state.
	 public void clear(){
		;
	}

 // Returns true if the PQ is empty, otherwise false
 	public boolean isEmpty(){
		return (ac <= 0);
	}

 // implementations should always return false.
	 public boolean isFull(){
		return (ac >= DEFAULT_MAX_CAPACITY); 
	}

	private class Iter implements Iterator<E> {
		int i = 0;
		public boolean hasNext(){
			return (i < ac) ? false : true;
		}
		public E next(){
			if (!hasNext())
                throw new NoSuchElementException();
			else{
				return a[i++].data ; 
			}	
		}
	}

 // Returns an iterator of the objects in the PQ, in no particular
 // order.
	public Iterator<E> iterator(){
		return new Iter();
	}
}
