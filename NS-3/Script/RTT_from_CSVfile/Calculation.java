import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Calculation {
	private String inputFileName;
	private String outputFileName;

	private String[] date;
	private String[] src_ip, dst_ip, icmp, request, reply;
	
	private double[] rtt;
	private double avgRtt;

	public Calculation(String inputFileName, String outputFileName) {
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;

		this.date = new String[100];
		this.src_ip = new String[100];
		this.dst_ip = new String[100];
		this.icmp = new String[100];
		this.reply = new String[100];
		this.request = new String[100];
		this.rtt = new double[100];
		this.avgRtt = 0.0;
	}

	public void parseFile() {

		try {
			BufferedReader br = new BufferedReader(new FileReader(this.inputFileName));
			String line;
			int count = 0;

			while ((line = br.readLine()) != null) {
				String[] splitLine = line.split(",");

				this.date[count] = splitLine[2];
				this.src_ip[count] = splitLine[3];
				this.dst_ip[count] = splitLine[4];
				this.icmp[count] = splitLine[5];
				
				if(icmp[count].equals("8")) {
					request[count] = date[count];
				}
				
				else if(icmp[count].equals("0")) {
					reply[count] = date[count];
				}
				
				++count;
				
			}
			
			
			br.close();
			
			timeCal();

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.err.println("File not found");
			System.exit(0);
		} catch (IOException e) {
			System.err.println("File not found1");
			System.exit(0);
		}
	}
	
	public void timeCal() {
		int cnt=0, cnt1=0;
		String tmp;
		String timeDiff[] = new String[100];
		String timeDiff1[] = new String[100];
		double replyTime, requestTime;
		
		for(int i = 0 ; i < this.request.length ; ++i) {
			if(request[i] != null) {
				timeDiff[cnt] = request[i];
				//System.out.println("R: "+timeDiff[cnt]);
				++cnt;
				
			}
			else if(reply[i] != null) {
				timeDiff1[cnt1] = reply[i];
				//System.out.println("RE: " + timeDiff1[cnt1]);
				++cnt1;
				
			}
				
			
		}
		
		for(int i = 0 ; i < this.request.length ; ++i) {
			
			
			if(timeDiff[i] != null && timeDiff1[i] != null) {
				tmp = timeDiff[i].substring(12, 24);
				requestTime = Double.parseDouble(tmp);
				tmp = timeDiff1[i].substring(12, 24);
				replyTime = Double.parseDouble(tmp);
				this.rtt[i] = replyTime - requestTime;
				System.out.println("RTT: " + rtt[i]);
				
			}
		}
		
		cnt = 0;
		for(int i = 0 ; i < this.rtt.length; ++i) {
			if(rtt[i] > 0) {
				avgRtt = avgRtt + rtt[i];
				++cnt;
			}
		}
		
		avgRtt = avgRtt/cnt;
		
	}
	
	
	public void printOutput() {
		FileWriter fw;
		PrintWriter bw;
		
		try {
			fw = new FileWriter(this.outputFileName + ".txt");
			bw = new PrintWriter(fw);
			
			for(int i = 0 ; i < this.rtt.length ; ++i) {
				if(this.rtt[i] > 0)
					bw.write(String.valueOf("RTT: " + this.rtt[i] +"\n"));
			}
			
			bw.write(String.valueOf("\nAverage RTT: " + this.avgRtt +"\n"));
			
			bw.close();
			
		} catch (IOException e) {
			System.err.println("Cannot open output file");
		}
	}
	
	
}
