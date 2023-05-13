package data;
import java.util.Random;
import java.util.TreeSet;

import utility.ArraySet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Data {

	// una matrice nXm di tipo Object dove ogni riga modella una transazioni
	private List<Example> data;

	
	// cardinalità dell’insieme di transazioni (numero di righe in data)
	private int numberOfExamples;
	
	// un vettore degli attributi in ciascuna tupla (schema della tabella di dati)
	private List<Attribute> attributeSet;
	
	private int distinctTuples;
	
	

	public Data() {
		
	    // inizializza il dataset di esempi
	    TreeSet<Example> tempData = new TreeSet<Example>();

	    Example ex0 = new Example();
	    ex0.add(new String("sunny"));
	    ex0.add(new String("warm"));
	    ex0.add(new String("normal"));
	    ex0.add(new String("strong"));

	    tempData.add(ex0);
	    
	    data = new ArrayList<Example>(tempData);
	        // numberOfExamples
	        numberOfExamples = data.size();

	        //explanatory Set
	      attributeSet = new LinkedList<Attribute>();
	        
	        String[] outLookValues = new String[3], temperatureValues = new String[3], humidityValues = new String[2], windValues = new String[2], playValues = new String[2];
	        outLookValues[0] = "overcast";
	        outLookValues[1] = "rain";
	        outLookValues[2] = "sunny";
	       // Arrays.sort(outLookValues);
	        attributeSet.add(new DiscreteAttribute("Outlook", 0, outLookValues));

	        temperatureValues[0] = "cool";
	        temperatureValues[1] = "hot";
	        temperatureValues[2] = "mild";
	     //   Arrays.sort(temperatureValues);
	        attributeSet.add(new DiscreteAttribute("Temperature", 1, temperatureValues));
	       
	        humidityValues[0] = "high";
	        humidityValues[1] = "normal";
	        Arrays.sort(humidityValues);
	        attributeSet.add(new DiscreteAttribute("Humidity", 2, humidityValues));

	        windValues[0] = "strong";
	        windValues[1] = "weak";
	        Arrays.sort(windValues);
	        attributeSet.add(new DiscreteAttribute("Wind", 3, windValues));

	        playValues[0] = "no";
	        playValues[1] = "yes";
	        Arrays.sort(playValues);
	        attributeSet.add(new DiscreteAttribute("Play", 4, playValues));
	    }



	
    public int getNumberOfExamples() {
    return numberOfExamples;
	}
	
	public int getNumberOfAttributes(){
		return attributeSet.size();
	}
	
	
	List<Attribute> getAttributeSchema() {
		return attributeSet;
	}
	
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data.get(exampleIndex).get(attributeIndex);
	}
	
	
	public String toString() {
        String s = "";
        for (Attribute val : getAttributeSchema()) {
            s += val.getName() + ",";
        }
        s += "\n";

        for (int i = 0; i < getNumberOfExamples(); i++) {
            s += (i + 1) + ":";
            for (int j = 0; j < attributeSet.size(); j++) {
                s += getAttributeValue(i, j) + ",";
            }
            s += "\n";
        }
        return s;
    }


	
	
	public Tuple getItemSet(int index) {
		Tuple tuple=new Tuple(attributeSet.size());
		for(int i=0;i<attributeSet.size();i++)
			tuple.add(new DiscreteItem((DiscreteAttribute)attributeSet.get(i), (String)data.get(index).get(i)),i);
		return tuple;
		
	}

	
	public int[] sampling(int k) throws OutOfRangeSampleSize {
		 if (k <= 0 || k > getNumberOfExamples()) {
		        throw new OutOfRangeSampleSize("Il numero di cluster inserito non è valido.");
		    }
    	int centroidIndexes[]=new int[k];
    	//choose k random different centroids in data.
    	Random rand=new Random();
    	rand.setSeed(System.currentTimeMillis());
    	for (int i=0;i<k;i++){
    		boolean found=false; 
    		int c;
    		do
    		{
    			found=false;
    			c=rand.nextInt(getNumberOfExamples());
    			// verify that centroid[c] is not equal to a centroide already stored in CentroidIndexes
    			for(int j=0;j<i;j++)
    				if(compare(centroidIndexes[j],c)){
    					found=true;
    					break;
    				}
    		}
    		while(found);
    		centroidIndexes[i]=c;
    		}
    	return centroidIndexes;
    	}
    
	
    private boolean compare(int i, int j){
        Tuple t1 = getItemSet(i);
        Tuple t2 = getItemSet(j);
        for(int k = 0; k < t1.getLength(); k++)
            if(!(t1.get(k).equals(t2.get(k))))
                return false;
        return true;
    }
    

    
    Object computePrototype(ArraySet idList, Attribute attribute) {
    	return computePrototype(idList, (DiscreteAttribute)attribute);
    }
    
    
    
    String computePrototype(ArraySet idList, DiscreteAttribute attribute) {
    	  Map<Object, Integer> counterMap = new HashMap<>();
    	   for (String attrVal : attribute) {
               counterMap.put(attrVal, attribute.frequency(this, idList, attrVal));
           }
          Object proto = null;
          int maxVal = Integer.MIN_VALUE;
          for (Map.Entry<Object, Integer> entry : counterMap.entrySet())
              if (entry.getValue() > maxVal) {
                  maxVal = entry.getValue();
                  proto = entry.getKey();
              }
          return (String) proto;
    }

    
	
	public static void main(String args[]){
		Data trainingSet=new Data();
		  System.out.println(trainingSet.getAttributeValue(0, 0));
		System.out.println(trainingSet);	
	}
	
	
	class Example  implements Comparable<Example>{
		
		//array di Object che rappresentano la singola transazione (o riga di una tabella)
		private List<Object> example=new ArrayList<Object>(); 
		
		//aggiunge o in coda ad example
		void add(Object o){
			example.add(o);	
		}
		
		
		//restituisce lo i-esimo riferimento collezionato in example
		Object get(int i) {
			return example.get(i);		
	
		}
		
		
		/*
		 * restituisce 0, -1, 1 sulla base del risultato del confronto.
		 * 0 se i due esempi includono gli stessi valori.
		 * Altrimenti il risultato del compareTo(...) invocato sulla prima coppia di valori in disaccordo.
		 */
		@Override
		public int compareTo(Example ex) {
			for(int i = 0; i<example.size(); i++) {
                if (!example.get(i).equals(ex.get(i))) {
                    return example.get(i).toString().compareTo(ex.get(i).toString());
                }
            }
            return 0;		
			
		}
		
		//restutuisce una stringa che rappresenta lo stato di example 
		public String toString() {
		    String result = "";
		    for (Object o : example) {
		        result += o.toString() + ", ";
		    }
		   result += "";
		    return result;
		}
				
	}
}





