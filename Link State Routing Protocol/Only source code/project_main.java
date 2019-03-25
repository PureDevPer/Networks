// project_main.java
// CS 542 Computer Networks1 
// Spring 2014
// Final project, Link-State Routing Protocol using a Dijkstra's algorithm
// Name : Wooseok Kim

import javax.swing.*;


public class project_main {
	public static void main(String[] args){
		
		int menu = 0;
		int source = 0;
		int dest = 0;
		String readtxtFile = null;
		readFile num1 = new readFile();
		
		for(;;){
			String cmd = JOptionPane.showInputDialog("CS542 Link State Routing Simulator\n\n"
					+ "(1) Input Network Topology File\n(2) Build a Connection Table\n"
					+ "(3) Shortest Path to Destination Router\n"
					+ "(4) Exit\n\n"
					+ "Command : ");
			menu = Integer.parseInt(cmd);
			
			
			// select input
			if(menu == 1){
				String readtxtFile1 = JOptionPane.showInputDialog("Command - " + menu + 
						"\nInput original network topology matrix data file \n"
						+ "Input text file");
				readtxtFile = readtxtFile1;

				num1.output_txtFile(readtxtFile1);
			}
			
			
			// Select a source router
			else if(menu == 2){
				String cmd2 = JOptionPane.showInputDialog("Command : " + menu + "\nPrompt : Select a source router \nSource router");
				source = Integer.parseInt(cmd2);

				if(source < 1)
					JOptionPane.showMessageDialog(null, "Wrong answer");
				
				else if(readtxtFile == null)
					JOptionPane.showMessageDialog(null, "You should write input network topology file");
				
				else{
					JOptionPane.showMessageDialog(null, "Prompt\n\n Router " + source);
					System.out.println("Destination        Interface");
					System.out.println("=============================================");
					num1.output_inter(menu, source, readtxtFile);
				}
			}
			
			
			// Destination router
			else if(menu == 3){
				String cmd3 = JOptionPane.showInputDialog("Command : " + menu + 
						"\nPrompt : Select the destination router\n" + "Command : ");

				dest = Integer.parseInt(cmd3);
				if(dest < 1)
					JOptionPane.showMessageDialog(null, "Wrong answer(destination)");
				
				else if(source < 1)
					JOptionPane.showMessageDialog(null, "You must write source value");
				
				else if(readtxtFile == null)
					JOptionPane.showMessageDialog(null, "You should write input network topology file");
				
				else if(source == dest)
					JOptionPane.showMessageDialog(null, "Source router is destination router. Therefore, the total cost is 0.");
				
				else
				{
					System.out.print("Prompt : The shortest path from router " + source + " to router " + dest + " is ");
					num1.output_dest(menu, source, dest, readtxtFile);
				}
			}
			
			
			// Exit
			else if(menu == 4){
				JOptionPane.showMessageDialog(null, "Command : " + menu + "\nPrompt : Exit CS542 project. Good Bye!");
				break;
			}
			
			else{
				JOptionPane.showMessageDialog(null, "Wrong answer");
			}
			
		}
		
		
	}
}
