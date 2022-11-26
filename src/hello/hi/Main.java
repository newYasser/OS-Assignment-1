package hello.hi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Main implements ActionListener {
	private static JLabel nLabel;
	private static JTextField nInput;
	private static JLabel bufferSizeLabel;
	private static JTextField bufferSizeInput;
	private static JLabel fileNameLabel;
	private static JTextField fileNameInput;
	private static PrimeNumbers prime;
	private static JLabel timeLabel;
	private static JLabel maxNumber;
	private static JLabel numberOfPrimes;
	public static void main(String[] args) throws InterruptedException, IOException {
			
		    JFrame frame = new JFrame(); 
		    JPanel panel = new JPanel();
		    frame.setSize(500,500);
		    frame.setTitle("Produce-Consume Simulator");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.add(panel);
		    panel.setLayout(null);
		    
		    nLabel = new JLabel("N"); 
		    nLabel.setBounds(10,20,80,25);
		    panel.add(nLabel);
		    
		    nInput = new JTextField();
		    nInput.setBounds(100,20,165,25);
		    panel.add(nInput);
		    
		    bufferSizeLabel = new JLabel("Buffer Size"); 
		    bufferSizeLabel.setBounds(10,50,80,25);
		    panel.add(bufferSizeLabel);
		    
		    bufferSizeInput = new JTextField();
		    bufferSizeInput.setBounds(100,50,165,25);
		    panel.add(bufferSizeInput);
		    
		    fileNameLabel = new JLabel("File Name"); 
		    fileNameLabel.setBounds(10,80,80,25);
		    panel.add(fileNameLabel);
		    
		    fileNameInput = new JTextField();
		    fileNameInput.setBounds(100,80,165,25);
		    panel.add(fileNameInput);
		    
		    timeLabel = new JLabel("");
		    timeLabel.setBounds(10,150,120,25);
		    panel.add(timeLabel);
		    
		    maxNumber = new JLabel("");
		    maxNumber.setBounds(10,180,120,25);
		    panel.add(maxNumber);
		    
		    numberOfPrimes = new JLabel("");
		    numberOfPrimes.setBounds(10,210,150,25);
		    panel.add(numberOfPrimes);
		    
		    JButton button = new JButton("Click Me");
		    button.setBounds(100,120,90,25);
		    button.addActionListener(new Main());
		    panel.add(button);
		    
		    frame.setVisible(true);
		   
		    		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int n = Integer.parseInt(nInput.getText());
		int bufferSize = Integer.parseInt(bufferSizeInput.getText());
		String fileName = fileNameInput.getText();
		long startTime = System.currentTimeMillis();
		try {
			prime = new PrimeNumbers(fileName,bufferSize,n);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 Thread t1 = new Thread(new Runnable() {
		        @Override
		        public void run()
		        {
		            try {
		                prime.produce();
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
		 
		 try {
			t1.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 try {
			t2.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 long endTime= System.currentTimeMillis();
		 
		 timeLabel.setText("Time: " + Long.toString(endTime - startTime) + " ms.");
		 maxNumber.setText("Max Number: " + String.valueOf(prime.maxNum()));
		 numberOfPrimes.setText("# of primes generated: " + String.valueOf(prime.getNumberOfPrimes()));
		 
		
	}
}



 