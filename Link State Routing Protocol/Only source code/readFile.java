// readFile.java
// CS 542 Computer Networks1 
// Spring 2014
// Final project, Link-State Routing Protocol using a Dijkstra's algorithm
// Name : Wooseok Kim

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;

public class readFile {
	
	// To find Router Connection Table
	public void output_inter(int menu, int source, String readtxtFile){
		try
		{
			Scanner file = new Scanner(new File(readtxtFile));
			int count = 0;	// To know the number of destinations
			
			while(file.hasNext())
			{
				String stringRead = file.nextLine();
				count = count + 1;
			}
			
			file.close();
			
			// The number of destinations
			for(int a = 1 ; a < count ; a++)
				output_dest(menu, source, a, readtxtFile);
		}
		
		catch (FileNotFoundException fnfe)
		{
			JOptionPane.showMessageDialog(null, "Unable to find text file, exisiting");
		}
	}
	
	
	// To execute command 1
	public void output_txtFile(String txtFile){
		try{
			Scanner file = new Scanner(new File(txtFile));

			while(file.hasNext())
			{
				String stringRead = file.nextLine();
				System.out.println(stringRead);
			}
			
			file.close();
		}
		
		catch (FileNotFoundException fnfe)
		{
			JOptionPane.showMessageDialog(null, "Unable to find text file, exisiting");
		}
		
	}
	
	
	// To find connection table, the shortest path and the total cost
	public void output_dest(int menu, int start, int dest, String txtFile){
		try{
			int cnt = 0;	// To know the number of rows
			String data [] = new String[100];	// Array which saves rows

			Scanner file = new Scanner(new File(txtFile));
			
			while(file.hasNext())
			{
				String stringRead = file.nextLine();
				data[cnt] = stringRead;
				cnt = cnt + 1;
			}
			
			file.close();
			
			
			// Detect error
			if(start > cnt){
				JOptionPane.showMessageDialog(null, "Wrong answer\nChange wrong answer to max source value\n\n");
				start = cnt-1;
			}
			else if(dest > cnt){
				JOptionPane.showMessageDialog(null, "Wrong answer\nChange wrong answer to max destination value\n\n");
				dest = cnt-1;
			}
			
			
			int n = cnt;	// The maximum length of array
			int m = 999;	// Infinity value
			
			int i,j,k = 1;
			int min;
			int[] vertex = new int[n];	
			int[] distance = new int[n]; // total cost value
			int[] via = new int[n];		// Array which the user visited
			
			int path[] = new int[n];
			int path_cnt = 1;

			
			int data2[][] = new int[n][n];

			// Make complete matrix from text file
			for(int c = 1 ; c < n ; c++){
				Scanner parse = new Scanner(data[c]);
				parse.useDelimiter(" ");
				for(int d = 1 ; d < n ; d++){
					data2[c][d] = parse.nextInt();
					//System.out.print(data2[j][k] + " ");
				}
				//System.out.println();
				parse.close();
			}
			
			
			// if there is -1 value in matrix, the matrix change -1 to m(999)
			// because of comparison between infinity value(m) and min  
			for(int b = 1; b < n ; b++)
			{
				for(int c = 1; c < n ; c++){
					if(data2[b][c] == -1){
						data2[b][c] = m;
					}
				}
			}
			
			
			
			// Initialization
			for(j = 1 ; j < n ; j++)
			{
				vertex[j] = 0;
				distance[j] = m;
			}
			
			distance[start] = 0;
			
			
			
			for(i = 1 ; i < n ; i++)
			{
				min = m;
				// Find minimum value 
				for(j = 1 ; j < n; j++)
				{
					if(vertex[j] == 0 && distance[j] < min)
					{
						k = j;
						min = distance[j];
					}
				}
				
				
				// Mark a vertex which the computer visited
				vertex[k] = 1;
				
				
				// There is no connection vertex
				if(min == m)
					break;
				
				
				// Find total value
				for(j = 1 ; j < n; j++)
				{
					if(distance[j] > distance[k] + data2[k][j])
					{
						distance[j] = distance[k] + data2[k][j];
						via[j] = k;
					}

				}
			}
			
			
			k = dest;
			
			
			// path_cnt = 1 <- already initialization
			// Find the vertex which the user visited
			while(true)
			{
				path[path_cnt++] = k;
				if(k == start)
					break;
				
				k = via[k];
			}
			
			
			// if command is 2
			if(menu == 2){
				i = path_cnt - 2;
				if(path[i] == 0)
					System.out.println("  " + dest + "                  " + "-" );
				else
					System.out.println("  " + dest + "                  " + path[i] );
			}
			
			
			// if command is 3
			if(menu == 3){
				
				for(i = path_cnt - 1 ; i >= 2; i--)
				{
					System.out.print(path[i] + "  ");
				}
				
				System.out.println(path[i] + ",  the total cost is " + distance[dest]);
				}
		}
		
		catch (FileNotFoundException fnfe)
		{
			JOptionPane.showMessageDialog(null, "Unable to find text file, exisiting");
		}
	}
	
}
