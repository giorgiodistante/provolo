package mining;
import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

public class ClusterSet {

	private Cluster [] C;
	private int i = 0;
	
	//creo l'oggetto array riferito da C
	ClusterSet(int k){
		C = new Cluster[k];
	}
	
	//assegna c a C[i ] e incrementa i.
	void add(Cluster c) {
		C[i] = c;
		i++;
	}
	
	Cluster get(int i) {
		return C[i];
	}
	
	
	
	// sceglie i centroidi, crea un cluster per ogni centroide e lo memorizza in C
	void initializeCentroids(Data data) throws OutOfRangeSampleSize {
		int centroidIndexes[]=data.sampling(C.length);
		for(int i=0;i<centroidIndexes.length;i++){
			Tuple centroidI=data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroidI));
		}
	}
	
	/*
	 * Calcola la distanza tra la tupla riferita ed il centroide di ciascun cluster in C
	 * restituisce il cluster più vicino
	 */
	Cluster nearestCluster(Tuple tuple){
		Cluster nearest = null;
		double minDistance = Double.MAX_VALUE;
		for (Cluster cluster : C) {
			double distance = tuple.getDistance(cluster.getCentroid());
			if (distance < minDistance) {
				minDistance = distance;
				nearest = cluster;
			}
		}
		
		return nearest;
			
		}
		
	
	
	/*
	 * Identifica e restituisce il cluster a cui la tupla rappresentate l'esempio identificato da id.
	 * Se la tupla non è inclusa in nessun cluster restituisce null
	 * (fare uso del metodo contain() della classe Cluster).
	 */
	Cluster currentCluster(int id) {
	    for (Cluster cluster : C) {
	        if (cluster.contain(id)) {
	            return cluster;
	        }
	    }
	    return null;
	}

	/*
	 * Calcola il nuovo centroide per ciascun cluster in C
	 */
	void updateCentroids(Data data) {
		for(Cluster cluster : C) {
			cluster.computeCentroid(data);
		}
	}
	
	/*
	 * Restituisce una stringa fatta da ciascun centroide dell’insieme dei cluster.
	 */
	public String toString() {
	    String str = "";
	    for (int i = 0; i < C.length; i++) {
	        str += "Cluster " + (i+1) + " centroid: " + C[i].getCentroid().toString() + "\n";
	    }
	    return str;
	}
	
	
	/*
	 * Restituisce una stringa che descriva lo stato di ciascun cluster in C.
	 */
	public String toString(Data data){
		String str="";
		for(int i=0;i<C.length;i++){ 
			if (C[i]!=null){
				str+=i+":"+C[i].toString(data)+"\n";
				}
			}
		return str;
	}
	
}
