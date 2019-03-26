
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//String inputFileName = "csma-ping-3.csv";
		String inputFileName =  args[0];
		//String outputFileName = "output";
		String outputFileName = args[1];
		
		Calculation cal = new Calculation(inputFileName, outputFileName);
		
		cal.parseFile();
		cal.printOutput();
	}

}
