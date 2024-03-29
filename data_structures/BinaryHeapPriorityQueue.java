package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

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
	int modificationCounter;
	int maxSize;
	public static final int DEFAULT_MAX_CAPACITY = 1000;

 // constructors
	public BinaryHeapPriorityQueue(){
		modificationCounter = 0;
		serial = 0;
		ac = 0;
		maxSize = DEFAULT_MAX_CAPACITY;
		a = (Wrapper <E> []) new Wrapper[maxSize];
	}
	public BinaryHeapPriorityQueue(int size){
		modificationCounter = 0;
		serial = 0;
		ac = 0;
		maxSize = size;
		a = (Wrapper <E> []) new Wrapper[maxSize];
	}

 // Inserts a new object into the priority queue. Returns true if
 // the insertion is successful. If the PQ is full, the insertion
 // is aborted, and the method returns false.
	public boolean insert(E object){
		modificationCounter++;
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
		modificationCounter++;
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
	E delObj;
	public boolean delete(E obj){
		delObj = obj;
		return deleteAux(0);
	}
	public boolean deleteAux(int i){
		if(i >= ac) return false;	
		int compareRes = a[i].data.compareTo(delObj);
		if(compareRes > 0) return false;
		if(compareRes == 0){
			removeAt(i);
			return deleteAux(i) || true;
		} else {
			boolean l = deleteAux(2*i + 1);
			boolean r = deleteAux(2*i + 2);
			return l || r;
		}
	}

 // Returns the object of highest priority that has been in the
 // PQ the longest, but does NOT remove it.
 // Returns null if the PQ is empty.
	public E peek(){
		if(isEmpty()) return null;
		else return a[0].data;
	}

 // Returns true if the priority queue contains the specified element
 // false otherwise.
	E contObj;
	public boolean contains(E obj){
		contObj = obj;
		return contAux(0);
	}
	public boolean contAux(int i){
		if(i >= ac) return false;
		int compareRes = a[i].data.compareTo(contObj);
		if(compareRes > 0) return false;
		if(compareRes == 0) return true;
		return contAux(2*i + 1) || contAux(2*i + 2);
	}

 // Returns the number of objects currently in the PQ.
 	public int size(){return ac;}

 // Returns the PQ to an empty state.
	public void clear(){ac = 0;}

 // Returns true if the PQ is empty, otherwise false
 	public boolean isEmpty(){return (ac <= 0);}

 // implementations should always return false.
	public boolean isFull(){
		return (ac >= maxSize); 
	}

	private class Iter implements Iterator<E> {
		int i;
		int state;

		public Iter(){
			i = 0;
			state = modificationCounter;
		}
		public boolean hasNext(){
			if(state != modificationCounter)
				throw new ConcurrentModificationException();
			return i < ac;
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
