package graph.list;

class Label {
	int vertex;
	int dist;
	boolean perm;
	Label(){
		perm = false;
		vertex=0;
		dist=99999;
	}
	Label(int v, int d){
		vertex = v;
		dist = d;
		perm = false;
	}
}
