package hello.hi;

import java.util.LinkedList;
import java.util.Queue;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PrimeNumbers {
	
	private int  bufferSize;
	private BufferedWriter file;
	private boolean finished;
	private int maxNum;
	private Queue <Integer> queue = new LinkedList<>();

	
	public PrimeNumbers(String outputFile, int  bufferSize) throws IOException {
	       this.file = new BufferedWriter(new FileWriter(outputFile));
			this.bufferSize = bufferSize;
	       this.finished = false; 
	}
	public void produce(int n) throws InterruptedException {
	synchronized(this) {
		for (int num = 2; num <= n; num++){	
           // System.out.println(queue.size());
            //System.out.println("buffer size: " + bufferSize);
            if(queue.size() == bufferSize) {
        		wait();
        		notify();
        		//System.out.println("righthere");
        	}
			
            boolean isPrime = true;
            for (int i=2; i <= num/2; i++)
            {
                if ( num % i == 0)
                {
                    isPrime = false;
                    break;
                }
            }
            
            if (isPrime) {
            	//System.out.println(num);
               queue.add(num);
            }
            Thread.sleep(500);

	    }
		tracker();
		wait();
		
	}
	
		
  }
  
	
	private boolean tracker() { this.finished = true; return finished;}
	public  int  maxNum() {return this.maxNum;}
	private void setMaxNum(int num) { this.maxNum = num;}
	
	public synchronized void consume() throws InterruptedException, IOException{
		while(true) {
		if(queue.isEmpty()) 
			wait();
		notify();
			
	        try { 
	        	file.write(Integer.toString(queue.peek()) + ", ");
	        }
	        catch(Exception e) {
	        	  if(this.finished && queue.isEmpty()) {
	        	       	 this.file.close();
	        	       	 break;
	            }else {
	        	wait();
	        	notify();
	           }
	        }
	        // System.out.println(queue.peek());
	         setMaxNum(queue.peek());
	         queue.remove();
	         Thread.sleep(500);
	   
        if(this.finished && queue.isEmpty()) {
       	 this.file.close();
       	 break;
       }
	}
  }
}
