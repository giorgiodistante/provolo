import data.Data;
import data.OutOfRangeSampleSize;
import keyboardinput.Keyboard;
import mining.KMeansMiner;

public class MainTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Data data =new Data();
		System.out.println(data);
		boolean execution;
		do {
			execution = false;
			KMeansMiner kmeans;
			int numIter = 0;
			int k;
			boolean invalidK;
			do {
				invalidK = false;
				System.out.println("Inserisci k: ");
				k = Keyboard.readInt();
				kmeans=new KMeansMiner(k);
				try {
					numIter=kmeans.kmeans(data);
				}catch(OutOfRangeSampleSize exception) {
					System.out.println(exception.getMessage());
					invalidK = true;
				}
			}while(invalidK);
			System.out.println("Numero di Iterazione:"+numIter);
			System.out.println(kmeans.getC().toString(data));
			System.out.println("Vuoi ripetere l'esecuzione? (y/n)");
			String answer = Keyboard.readString();
	        execution = answer.equalsIgnoreCase("y");
		}while(execution);
			
	}
}	

