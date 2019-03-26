import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class printOutput {
	private String outputFileName;
	private double[] output;
	
	public printOutput(double[] output1, String outputFile) {
		this.outputFileName = outputFile;
		this.output = output1;
	}
	
	
	public void printOutput1() {
		FileWriter fw;
		PrintWriter bw;
		
		try {
			fw = new FileWriter(this.outputFileName + ".txt");
			bw = new PrintWriter(fw);
			
			for(int i = 0 ; i < 3 ; ++i) {
				
					bw.write(String.valueOf("AVG RTT: " + this.output[i] +"\n"));
			}
			
			
			
			bw.close();
			
		} catch (IOException e) {
			System.err.println("Cannot open output file");
		}
	}
}
