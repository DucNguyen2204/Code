package project156;

import java.util.Comparator;
import java.util.Iterator;

public class myList<T> implements Iterable<T>{
	private Node<T> head = null;
	private Comparator<T> c =null;

	public myList(Comparator<T> comp) {
		this.head = null;
		this.c = comp;
	}
	
	public void clear(){
		this.head = null;
	}

	public int getSize(){
		Node<T> temp = this.head;
		int count = 0;
		while(temp != null){
			temp = temp.getNext();
			count++;
		}

		return count;
	}

	public void addElement(T p){
		if(this.head == null){//LIST IS EMPTY
			Node<T> newNode = new Node<T>(p);//NEW NODE TO HOLD THE ITEM THAT IS BEING PAST IN
			this.head = newNode;
		}else{//LIST IS NOT EMPTY
			Node<T> temp = this.head;
			Node<T> newNode = new Node<T>(p);////NEW NODE TO HOLD THE ITEM THAT IS BEING PAST IN
			if(this.c.compare(temp.getItem(), p)==1){//THE HEAD IS HOLDING LARGER ITEM => THE NODE THAT HOLDS ITEM COME FIRST
				this.head = newNode;//SET THE NEWNODE AS THE HEAD
				this.head.setNext(temp);//PUT THE PREVIOUS HEAD AFTER
			}else if(this.c.compare(temp.getItem(), p)==0){//HEAD AND THE NEW NODE ARE HOLDING THE SAME ITEM, I CHOOSE TO PUT THE NEW NODE IN FRONT
				/**
				 * SAME CODE AS WHEN THE NEW NODE COME BEFORE THE HEAD
				 */
				this.head = newNode;
				this.head.setNext(temp);
			}else if (this.c.compare(temp.getItem(), p) == -1){//THE NEW NODE IS HOLDING LARGER ITEM => IT HAS TO COME AFTER THE HEAD
				int size = this.getSize();//SIZE OF THE LIST
				if(this.head.getNext() != null){//THERE ARE MORE 1 ELEMENT IN THE LIST
					for(int i = 1; i < size;i++){//LOOP THROUGH EVERY ELEMENT IN THE LIST. FOR LOOP HELPS GET THE NODE AT INDEX
						Node<T> theNode = this.getNodeAtIndex(i);//SINCE I ALREADY COMPARED TO THE HEAD, I START WITH i=1 WHICH MEANS START WITH THE NODE AFTER THE HEAD
						if(this.c.compare(theNode.getItem(), p) == 1){//NEW NODE COMES BEFORE
							Node<T> preNode = this.getNodeAtIndex(i-1);//GET THE NODE BEFORE THE ONE I'M COMPARING TO
							preNode.setNext(newNode);//LINK IT TO THE NEW NODE
							newNode.setNext(theNode);//LINK THE NEW NODE TO THE ONE I'M COMPARING
							break;//I'VE FOUND THE POSITION THAT I NEED TO INSERT THE NEW NODE => NO NEED TO COMPARE WITH OTHER NODES
						}else if (this.c.compare(theNode.getItem(), p) == - 1){//COME AFTER THE NODE IM COMPARING TO
							if(theNode.getNext() == null){//IF IT'S THE LAST NODE
								theNode.setNext(newNode);
							}else{//IF IT'S NOT THE LAST NODE, MOVE ON AND COMPARE TO THE NEXT ONE
								continue;
							}
						}else if(this.c.compare(theNode.getItem(), p) == 0){//EQUALS TO THE ONE I'M COMPARING
							if(theNode.getNext() == null){//THE LIST HAS ONLY ONE ELEMENT
								theNode.setNext(newNode);
								break;
							}else{//IF NOT, PUT IT IN BETWEEN THE ONE I'M COMPARING AND THE ONE BEFORE
								Node<T> preNode = this.getNodeAtIndex(i-1);
								preNode.setNext(newNode);
								newNode.setNext(theNode);
								break;
							}
						}
					}
				}else{
					this.head.setNext(newNode);
				}
			}
		}
	}



	public void remove(T item){
		if(this.head == null){
			return;
		}else if (head.getItem().equals(item)){
			head = head.getNext();
		}else {
			Node<T> current = head;
			Node<T> prev = null;
			while(current.hasNext() && !current.getItem().equals(item)){
				prev = current;
				current = current.getNext();
			}
			
			if(current.getItem().equals(item)){
				prev.setNext(current.getNext());
			}
		}
	}

	public Node<T> getNodeAtIndex(int position){
		Node<T> temp = this.head;

		if(position < 0){
			throw new IndexOutOfBoundsException();
		}else if (position > this.getSize()){
			throw new IndexOutOfBoundsException();
		}

		if(position == 0){
			return this.head;
		}else if(position > 0) {
			for(int i = 0; i < position; i++){
				temp = temp.getNext();

			}
		}
		return temp;
	}

	public T getElementAtIndex(int index) {

		Node<T> theNode = this.getNodeAtIndex(index);
		return theNode.getItem();
	}

	public String toString() {
		if(this.head == null) {
			return "[empty]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Node<T> curr = head;
		while(curr.hasNext()) {
			sb.append(curr.getItem());
			sb.append("\n");
			curr = curr.getNext();
		}
		sb.append(curr.getItem());
		sb.append("]");
		return sb.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>(){
			Node<T> temp = head;
			@Override
			public boolean hasNext() {

				if(temp == null){
					return false;
				}else 
					return true;
			}

			@Override
			public T next() {
				T item = temp.getItem();
				temp = temp.getNext();
				return item;
			}

		};
	}
}
