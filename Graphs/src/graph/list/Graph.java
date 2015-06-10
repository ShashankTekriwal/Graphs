package graph.list;

class Graph {
	private char nodes[];
	private List list[];
	private Label label[];
	public Graph(char arr[]){
		nodes = new char[arr.length];
		list = new List[arr.length];
		label = new Label[arr.length];
		for(int x=0;x<arr.length;x++){
			nodes[x]=arr[x];
			label[x]=new Label();
		}
	}
	//b=false for directed, true for undirected.
	public void addEdge(int x,int y,int wt,boolean b){
		List temp;
		temp = list[x];
		if(temp==null){
			list[x] = new List();
			list[x].value=y;
			list[x].weight=wt;
		}else{
			while(temp.next!=null){
				temp=temp.next;
			}
			temp.next=new List();
			temp = temp.next;
			temp.value=y;
			temp.weight=wt;
		}
		if(!b)
			return;
		addEdge(y,x,wt,false);
	}
	public void showAdjList(){
		for(int x=0;x<nodes.length;x++){
			List temp = list[x];
			System.out.print(nodes[x]);
			while(temp!=null){
				System.out.println("\t->\t"+nodes[temp.value]+"["+temp.weight+"]");
				temp=temp.next;
			}
			System.out.println(".......................");
		}
	}
	public void dijkstra(int x,int y){
		label[x].perm=true;
		label[x].vertex=x;
		label[x].dist=0;
		labelNodes(x,y);
		showRoute(x,y);
	}
	private int showRoute(int x,int y) {
		String s=nodes[y]+"";
		int cost = label[y].dist;
		do{
			s=nodes[label[y].vertex]+"->"+s;
			y=label[y].vertex;
		}while(y!=x);
		System.out.println(s);
		System.out.println("Cost is" + "\t"+cost);
		return cost;
	}
	private void labelNodes(int x, int y) {
		List temp = list[x];
		int d = label[x].dist;
		while(temp!=null){
			if(!label[temp.value].perm){
				if(d+temp.weight<label[temp.value].dist){
					label[temp.value].dist=d+temp.weight;
					label[temp.value].vertex=x;
				}
			}
			temp=temp.next;
		}
		int min=9999;
		int index=0;
		for(int i=0;i<label.length;i++){
			if(!label[i].perm){
				if(label[i].dist<min){
					min=label[i].dist;
					index=i;
				}
			}
		}
		label[index].perm=true;
		if(label[y].perm)
			return;
		labelNodes(index,y);
	}
	public void showLabels(){
		for(int x=0;x<label.length;x++){
			System.out.println(nodes[x]+"\t"+nodes[label[x].vertex]+"\t"+label[x].dist+"\t"+label[x].perm);
		}
	}
	public void topologicalSort(){
		int len = nodes.length;
		Queue ts = new Queue(len);
		Queue s = new Queue(len);
		int a[] = new int[len];
		for(int i = 0; i<a.length;i++)
			a[i]=0;
		List temp;
		for(int x = 0;x < len; x++){
			temp = list[x];
			while(temp!=null){
				a[temp.value]++;
				temp=temp.next;
			}
		}
		for(int x=0;x<len;x++){
			if(a[x]==0)
				s.insert(x);
		}
		while(!s.isEmpty()){
			int vertex=s.remove();
			ts.insert(vertex);
			temp = list[vertex];
			while(temp!=null){
				a[temp.value]--;
				if(a[temp.value]==0)
					s.insert(temp.value);
				temp = temp.next;
			}
		}
		if(ts.isFull())
			ts.display(nodes);
		else
			System.out.println("Graph contains a cycle!");
	}
	void breadthFirstSearch(int x){
		int len = nodes.length;
		int colour[] = new int[len];
		Queue q = new Queue(len);
		Label label[]=new Label[len];
		for(int i=0;i<len;i++)
			colour[i]=0;
		colour[x]=1;
		q.insert(x);
		label[x] = new Label(x,0);
		while(!q.isEmpty()){
			int vertex = q.remove();
			colour[vertex] = 2;
			List temp = list[vertex];
			while(temp!=null){
				if(colour[temp.value]==0){
					colour[temp.value]=1;
					q.insert(temp.value);
					label[temp.value] = new Label(vertex,label[vertex].dist+1);
					System.out.print(nodes[temp.value]+","+label[temp.value].dist+"\t");
				}
				temp=temp.next;
			}
		}
	}
	void depthFirstSearch(){
		
	}
	void FloydWarshall(){
		int inf = 9999;
		int vertexCount = nodes.length;
		int dist[][] = new int[vertexCount][vertexCount];
		for(int i=0;i<vertexCount;i++){
			for(int j=0;j<vertexCount;j++){
				if(i==j){
					dist[i][j]=0;
				}else{
					dist[i][j]=inf;
				}
			}
		}
		for(int i = 0; i < vertexCount; i++){
			List temp = list[i];
			while(temp!=null){
				dist[i][temp.value] = temp.weight;
				temp = temp.next;
			}
		}
		System.out.println("All pairs shortest paths :\n ");
		System.out.print("\t");
		for (int intermediate = 0; intermediate < vertexCount; intermediate++)
        {
            for (int source = 0; source < vertexCount; source++)
            {
                for (int destination = 0; destination < vertexCount; destination++)
                {
                    if (dist[source][intermediate] + dist[intermediate][destination]
                         < dist[source][destination]){
                        	dist[source][destination] = dist[source][intermediate] 
                            + dist[intermediate][destination];
                    }
                }
            }
            System.out.print(nodes[intermediate]+"\t");
        }
		System.out.println();
		for(int i = 0; i < vertexCount; i++){
			System.out.print(nodes[i]+"\t");
			for(int j=0; j< vertexCount; j++){
				if(dist[i][j]==inf){
					System.out.print("inf\t");
				}else{
					System.out.print(dist[i][j] + "\t");
				}
			}
			System.out.println();
		}
	}
	void MST_PRIM(){
		Graph mst = new Graph(nodes);
		int root = 0;
		Label label[] = new Label[nodes.length];
		for(int i = 0; i < nodes.length;i++){
			label[i] = new Label();
		}
		label[root].perm=true;
		Queue q = new Queue(nodes.length);
		q.insert(root);
		while(!q.isFull()){
			List temp = list[root];
			while(temp!=null){
				if(!label[temp.value].perm){
					if(temp.weight<label[temp.value].dist){
						label[temp.value].dist=temp.weight;
						label[temp.value].vertex=root;
					}
				}
				temp=temp.next;
			}
			int min=9999;
			int index=0;
			for(int i=0;i<label.length;i++){
				if(!label[i].perm){
					if(label[i].dist<min){
						min=label[i].dist;
						index=i;
					}
				}
			}
			label[index].perm=true;
			q.insert(index);
			mst.addEdge(label[index].vertex, index, label[index].dist, true);
			root = index;
		}
		mst.showAdjList();
	}
	public boolean BellmanFord(int s ,Label label[]){
		//Label label[] = new Label[nodes.length];
		for(int i = 0; i < nodes.length; i++){
			label[i] = new Label();
		}
		label[s].dist = 0;
		for(int i = 0; i < nodes.length-1;i++){
			for(int x = 0; x < nodes.length; x++){
				List temp = list[x];
				while(temp!=null){
					if(temp.weight+label[x].dist<label[temp.value].dist){
						label[temp.value].dist = label[x].dist+temp.weight;
						label[temp.value].vertex = x;
					}
					temp=temp.next;
				}
			}
		}
		/*for(int i = 0; i < nodes.length; i++){
			System.out.println(nodes[i]+"\t"+label[i].dist+"\t"+nodes[label[i].vertex]);
		}*/
		for(int i = 0; i < nodes.length; i++){
			List temp = list[i];
			while(temp!=null){
				if(label[temp.value].dist > temp.weight + label[i].dist){
					return false;
				}
				temp=temp.next;
			}
		}
		return true;
	}
}