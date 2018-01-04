package project156;

public class Node<T> {
	
	private Node<T> next;
	private T item;
	
	public Node(T item){
		this.item = item;
		this.next = null;
	}
	
	public T getItem(){
		return this.item;
	}
	
	public Node<T> getNext(){
		return this.next;
	}
	
	public void setNext(Node<T> next){
		this.next = next;
	}
	
	public boolean hasNext() {
		return (this.next == null) ? false : true;
	}

	@Override
	public String toString() {
		return "Node [next=" + next + ", item=" + item + "]";
	}
}
