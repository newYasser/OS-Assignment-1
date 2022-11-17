package hello.hi;

import java.io.IOException;
import java.util.Scanner;



public class Main {
	public static void main(String[] args) throws InterruptedException, IOException {
		    
		    Scanner sc = new Scanner(System.in);
			System.out.println("Enter n");
			int n  = sc.nextInt();
			System.out.println("Enter the buffer size");
			int bufferSize = sc.nextInt();
			System.out.println("Enter the File Name");
			String fileName = sc.next();
			
			
			PrimeNumbers prime = new PrimeNumbers(fileName,bufferSize);
			long startTime = System.currentTimeMillis();
			 Thread t1 = new Thread(new Runnable() {
			        @Override
			        public void run()
			        {
			            try {
			                prime.produce(n);
			            }
			            catch (InterruptedException e) {
			                e.printStackTrace();
			            }
			        }
			    });
			 
			 Thread t2 = new Thread(new Runnable() {
			        @Override
			        public void run()
			        {
			            try {
			                prime.consume();
			            }
			            catch (InterruptedException e) {
			                e.printStackTrace();
			            } catch (IOException e) {
							e.printStackTrace();
						}
			        }
			    });
			 
			 
			 t1.start();
			 t2.start();
			 
			 t1.join();
			 t2.join();
	
		long endTime= System.currentTimeMillis();
		System.out.println("Time = " + Long.toString((endTime - startTime) / 1000) + " sec.");
		System.out.println("The Max Number is = " +  String.valueOf(prime.maxNum()));

	}
}



 