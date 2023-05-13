package data;
import utility.ArraySet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;


class DiscreteAttribute extends Attribute implements Iterable<String> {
	
	private TreeSet<String> values;// array di oggetti String, uno per ciascun valore del dominio discreto. I valori del dominio sono memorizzati in values seguendo un ordine lessicografico.
	
	DiscreteAttribute(String name, int index, String[] valuesString){
	    super(name, index);
	    values = new TreeSet<>();
	    for (String value : valuesString) {
	        values.add(value);
	    }
    
	}

	
	int getNumberOfDistinctValues() {
		return values.size();
	}


	int frequency(Data data, ArraySet idList, String v) {
		int count = 0;
        int[] idArray = idList.toArray();
        for(int i : idArray){
            if(data.getAttributeValue(i, getIndex()).equals(v))
            	count++;
        }
        return count;
	}
	
	
	@Override
	public Iterator<String> iterator() {
	    return values.iterator();
	}

	
}
