package data;
public class Tuple {
	
	private Item [ ] tuple;
	
	Tuple(int size){
		tuple=new Item[size];
	}
	
	public int getLength() {
		return tuple.length;
	}
	
	public Item get(int i) {
		return tuple[i];
	}
	
	void add(Item c,int i) {
		tuple[i]=c;
	
	}
	
	public double getDistance(Tuple obj) {
		double distance = 0;

		for (int i=0; i<this.tuple.length; i++) {

		distance = distance + tuple[i].distance(obj.get(i).getValue());

		}

		return distance;
		
	}
	
	public double avgDistance(Data data, int clusteredData[]){
		double p=0.0,sumD=0.0;
		for(int i=0;i<clusteredData.length;i++){
			double d= getDistance(data.getItemSet(clusteredData[i])); sumD+=d;
		} 
		p=sumD/clusteredData.length; 
		return p;
	}	
	
	
}
