
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class FindString{


	public static void main(String[] args){
		Scanner myScanner = new Scanner(System.in);
		System.out.println("Lower case: Enter s1 the small string: ");

		String s1 = myScanner.nextLine();
		String s2 = "";
		System.out.println("Parsing Text");
		//buffered reader
		String fileName="LargeText.txt";
	    try{

	    //Create object of FileReader
	    FileReader inputFile = new FileReader(fileName);

	    //Instantiate the BufferedReader Class
	    BufferedReader bufferReader = new BufferedReader(inputFile);

	    //Variable to hold the one line data
	    String line;

	    // Read file line by line and print on the console
	    while ((line = bufferReader.readLine()) != null)   {
	            s2 = s2 + line;
	    } 

	    //Close the buffer reader
	    bufferReader.close();

		} catch (Exception e){
            System.out.println("Error while reading file line by line:" 
            + e.getMessage());
        }

		//System.out.println(s2);

		int n = s1.length();
		
		
		//System.out.println("Text found? "+ hasCheated(s1, s2, n, debug));
		try {
			System.out.println("Beginning search");
    		Thread.sleep(2000);

		} catch(InterruptedException ex) {
    	Thread.currentThread().interrupt();
		}
		long startTime = System.nanoTime();
		int answer = hasCheated(s1, s2, n);
		
		if (answer == -1) {
			System.out.println("====================No TEXT FOUND===============");
		}else{System.out.println("===================TEXT FOUND SUCCESSFULLY at LINE: " + answer+"======================");
		}

		System.out.println("Search completed in : " + (System.nanoTime() - startTime) + " ns (nano seconds)");


	}

	private static int hasCheated(String s1, String s2, int n){
		//assume s2 >> s1
		

		Queue<Integer> queue = new LinkedList<Integer>();
		int currentHash = 1;
		
		PrimeGen myGen = new PrimeGen();
		ArrayList<Integer> primes = myGen.getPrimesAlp(127); //for lower case and 27th will the be space

		
		//now compute the s1
		for(int i=0; i<s1.length(); i++){
			int tempChar = getHPrime(primes, s1.charAt(i));
			currentHash = currentHash*tempChar;
		}
		
		System.out.println("Target hash is " + currentHash);


		//now that we have the hashvalue of s1 we can start with s1.length chars.
		int masterHash = 1;
		int currentLength = 0;
		
		for(int i=0; i<s1.length(); i++){

			int tempChar = getHPrime(primes, s2.charAt(i));
			queue.add(tempChar);
			currentLength++;
			masterHash = masterHash*tempChar;
			System.out.println("computing s2 init hash......" + masterHash);
		}
		

		System.out.println("s2 hash before going in: " + masterHash);
		
		boolean found = false;
		for(int i = s1.length(); i<s2.length(); i++){
			//System.out.println(s2.charAt(i));
			if(currentLength<n){

				int tempChar = getHPrime(primes, s2.charAt(i));

				queue.add(tempChar);
				currentLength++;
				masterHash = masterHash*tempChar;
			} else{
				//dequeue divide and add in the new one;
				int dequeued = queue.poll();
				//System.out.println("dequeing........" + (char)primes.indexOf(dequeued));
				masterHash = masterHash/dequeued;
				int tempChar = getHPrime(primes, s2.charAt(i));

				queue.add(tempChar);
				masterHash = masterHash*tempChar;
				//System.out.println("current master hash is " + masterHash);	

			}
			if(masterHash == currentHash) {
				System.out.println("Possible Match Found!");
				int orig =0;
				for(int backCounter = i - n+1; backCounter<=i; backCounter++){
					
					if(s2.charAt(backCounter) != s1.charAt(orig)){
						break;
					} else {
						found = true;
					}
				}
				if(found) {
					return (i-n+1);

				}
			}
		}
		

		return -1;

	}

	private static int getHPrime(ArrayList<Integer> primes, char inC){
		int res = ((primes.get((int)inC) % 1000)% 450) %100;
		//System.out.println("Char is " + inC + "modded is: "+ res);
		return res;
	}


 
	


}
