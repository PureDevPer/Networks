import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class mainAvg {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String inputFileName1 =  args[0];
		String inputFileName2 = args[1];
		String inputFileName3 = args[2];
		String inputFileName4 = args[3];
		String inputFileName5 = args[4];
	
		String outputFileName = args[5];
		
		Scanner scan1 = new Scanner(new File(inputFileName1));
		Scanner scan2 = new Scanner(new File(inputFileName2));
		Scanner scan3 = new Scanner(new File(inputFileName3));
		Scanner scan4 = new Scanner(new File(inputFileName4));
		Scanner scan5 = new Scanner(new File(inputFileName5));
		
		int cnt=0;
		
		double[] avgRTT = new double[3];

		String[] temp_node0 = new String[5];
		String[] temp_node1 = new String[5];
		String[] temp_node2 = new String[5];
		String[] temp_node3 = new String[5];
		String[] temp_node4 = new String[5];
		
		
		while(scan1.hasNextLine()) {
			temp_node0[cnt] = scan1.nextLine();
			++cnt;
		}
		
		cnt = 0;
		while(scan2.hasNextLine()) {
			temp_node1[cnt] = scan2.nextLine();
			++cnt;
		}
		
		cnt = 0;
		while(scan3.hasNextLine()) {
			temp_node2[cnt] = scan3.nextLine();
			++cnt;
		}
		
		cnt = 0;
		while(scan4.hasNextLine()) {
			temp_node3[cnt] = scan4.nextLine();
			++cnt;
		}
		
		cnt = 0;
		while(scan5.hasNextLine()) {
			temp_node4[cnt] = scan5.nextLine();
			++cnt;
		}
		
		for(int i = 0 ; i < temp_node0.length ; ++i) {
			System.out.println("NODE0: " + temp_node0[i]);
			System.out.println("NODE1: " + temp_node1[i]);
			System.out.println("NODE2: " + temp_node2[i]);
			System.out.println("NODE3: " + temp_node3[i]);
			System.out.println("NODE4: " + temp_node4[i]);
		}

		for(int i = 0 ; i < 3 ; ++i) {
			avgRTT[i] = (Double.parseDouble(temp_node0[i].substring(5)) + Double.parseDouble(temp_node1[i].substring(5)) +
					Double.parseDouble(temp_node2[i].substring(5)) + Double.parseDouble(temp_node3[i].substring(5)) + 
					Double.parseDouble(temp_node4[i].substring(5))) / 5.0;
			
			System.out.println(avgRTT[i]);
		}
		
		printOutput po = new printOutput(avgRTT, outputFileName);
		po.printOutput1();
		
		scan1.close(); scan2.close(); scan3.close(); scan4.close(); scan5.close();

	}

}
