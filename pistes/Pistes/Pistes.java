import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Set;

public class Pistes { 
	static int N;
	static int opt = Integer.MIN_VALUE; 
	static int[] r = new int[42];
	static int[] s = new int[42];
	static int[] k = new int[42];
	static int[][] keys = new int[42][10];
	static int[][] rewards = new int[42][10];
 	
	public static int max(int a, int b) {
		return a > b ? a : b;
	}
	
	public static void permute(int[] arr){
	    permuteHelper(arr, 1);
	}

	private static void permuteHelper(int[] arr, int index){
	    if(index >= arr.length - 1){ //If we are at the last element - nothing left to permute
	        for (int i = 0; i < N; i++) {
	        	System.out.print(arr[i]);
	        }
	        System.out.println();
	    	int score = checkPermutation(arr);
	    	
	    	opt = max(score, opt);
	        return;
	    }

	    for(int i = index; i < arr.length; i++){ //For each index in the sub array arr[index...end]

	        //Swap the elements at indices index and i
	        int t = arr[index];
	        arr[index] = arr[i];
	        arr[i] = t;

	        //Recurse on the sub array arr[index+1...end]
	        permuteHelper(arr, index+1);

	        //Swap the elements back
	        t = arr[index];
	        arr[index] = arr[i];
	        arr[i] = t;
	    }
	}	
	
	public static int checkPermutation(int arr[]) { 
		System.out.println("=====================");
		int score = 0;
		int visited = 1;
		int current = arr[0];
		int i = 0;
		LinkedList<Integer> holding = new LinkedList<Integer>();
		for (int j = 0; j < r[current]; j++) {
			holding.add(rewards[current][j]);
		}
		
		while (i < N - 1) {
			System.out.println("You are at " + current + " holding:");
			
			printList(holding);
		
			for (int j = 0; j < k[current]; ++j) {
				System.out.println("Required is " + keys[current][j]);
				boolean found =	holding.removeFirstOccurrence(keys[current][j]);
				if (!found) {
					System.out.println("Score for this: " + score );

					return score;
					
				}
				
			}
			score += s[current];
			i++;
			current = arr[i];
			for (int j = 0; j < r[current]; ++j) {
				holding.add(rewards[current][j]);
			}
				
			
		}
		
		System.out.println("Finally holding:");
		printList(holding);
		if (i == N - 1) {
			current = arr[i];
			System.out.println("Came to last" + arr[i]);
			for (int j = 0; j < k[current]; ++j) {
				System.out.println("Required is " + keys[current][j]);
				boolean found =	holding.removeFirstOccurrence(keys[current][j]);
				if (!found) {
					System.out.println("Did last! Score for this: " + score );

					return score;
					
				}
				
			}
			score += s[current];

			
		}
		
		System.out.println("Ended! Score for this: " + score );
		return score;
	}
	
	public static void printList(LinkedList<Integer> l) {
		for (int i : l) {
			System.out.print(" " + i);
		}
		System.out.println();
	}
	
	public static void printPista(int j) {
		System.out.println("This is pista " + j);
		System.out.println(k[j] + " " + " " + r[j] + " " + s[j]);
		System.out.println("Required: ");
		for (int i = 0; i < k[j]; i++) {
			System.out.print(" " + keys[j][i]);
		}
		System.out.println();
		System.out.println("Rewarded");
		for (int i = 0; i < r[j]; i++) {
			System.out.print(" " + rewards[j][i]);
		}
		System.out.println();
		
	}
	
	public static void main(String[] args) throws Exception {
		
		File infile = new File(args[0]);
		Scanner sc = new Scanner(infile);
		N = sc.nextInt() + 1;
		int[] idx = new int[N];
		
		LinkedList<Integer> l = new LinkedList<Integer>();
		
		
		for (int i = 0; i < N; i++) {
			k[i] = sc.nextInt();
			r[i] = sc.nextInt();
			s[i] = sc.nextInt();
			for (int j = 0; j < k[i]; j++) {
				keys[i][j] = sc.nextInt();
			}
			for (int j = 0; j < r[i]; j++) {
				rewards[i][j] = sc.nextInt();
			}
			
			
		}
		
		
		for (int i = 0; i < k[0]; i++) {
			System.out.println(keys[0][i]);
		}
		for (int i = 0; i < r[0]; i++) {
			System.out.println(rewards[0][i]);
		}
		
		for (int i = 0; i < N; i++) idx[i] = i;
		
		
		permute(idx);
	
		for (int i : idx ) printPista(i);
		System.out.println("N = " + N);
		System.out.println("Score = " + opt);
		
		
		
	}
	
	
}