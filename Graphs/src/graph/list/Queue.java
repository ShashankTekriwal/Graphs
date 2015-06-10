package graph.list;

class Queue {
	private int array[];
	private int front;
	private int rear;
	Queue(int x){
		array = new int[x];
		front = -1;
		rear = -1;
	}
	boolean isEmpty(){
		if(front==-1 || front>rear)
			return true;
		return false;
	}
	boolean isFull(){
		if(rear==array.length-1)
			return true;
		return false;
	}
	void insert(int x){
		if(isEmpty()){
			front=0;
			rear = 0;
		}else{
			rear++;
		}
		array[rear]=x;
	}
	int remove(){
		if(!isEmpty()){
			int val = array[front];
			front++;
			return val;
		}
		return -1;
	}
	void display() {
		if(isEmpty()){
			System.out.println("List is empty!");
			return;
		}
		for(int x=front;x<=rear;x++){
			System.out.print(array[x]+",\t");
		}
	}
	void display(char ch[]){
		if(isEmpty()){
			System.out.println("List is empty!");
			return;
		}
		for(int x=front;x<=rear;x++){
			System.out.print(ch[array[x]]+",\t");
		}
	}
}
