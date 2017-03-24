//Gretchen Wilson
//BNFO 501: Project 3
//GWilsonProject3.java
//Reads data input 
//Organizes input into two data arrays: Data and Query
//Query holds the search items, Data holds the array to be searched
//Data is added to a binary search tree
//Tree Structure starting at root node variable
//Binary search tree is searched for query values

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class GWilsonProject3{
	static class Node {
  		private Node left;
  		private Node right;
  		private Node parent;
	  	private Integer value;
  
  		public Node(){
   			left = right = parent = null;
   			value = null;
 		}
  		public Node(int val, Node p, Node l, Node r){
   			value =val;
   			left = l;
   			parent = p;
   			right = r; 
  		}		
  		public Integer getValue(){
   			return value;
  		}
  		public Node getLeft(){
   			return left;
	  	}
  		public Node getRight(){
   			return right;
  		}
  		public Node getParent(){
   			return parent;
  		}
  		public void setParent(Node n){
   			parent = n;
  		}
  		public void setRight(Node n){
   			right = n;
  		}
  		public void setLeft(Node n){
   			left = n;
  		}
  		public String toString(){
   			String out = "Node created for value: " + this.getValue();
   			return out;
 		}
 	}
 
 	//root of the tree structure
 	public static Node root = null;
 
 	//Method to insert new Node
 	public static void insertValue(Node newNode){
  		Node current = root;
  		Node currentparent = null;
  
  		//parse through tree until Leaf
  		while(current != null){
   			currentparent = current;
   			if (newNode.getValue() < current.getValue())current = current.getLeft();
   			else if (newNode.getValue() > current.getValue())current = current.getRight();
   			else return;  
  		}
  		//set variables for newNode
  		newNode.setParent(currentparent);
  		if (currentparent == null)root = newNode;
  		else if (newNode.getValue() < currentparent.getValue()) currentparent.setLeft(newNode); 
  		else currentparent.setRight(newNode);
  		//System.out.println("New Node Created: " + newNode.getValue());
	 }
 
 	//Method for searching the binary tree data structure
 	public static boolean searchTree(int value) {
  		Node current = root;
  		while (current != null) {
   			if (current.getValue()==value) return true;
   			else if (current.getValue() > value) current = current.getLeft();
   			else if (current.getValue() < value) current = current.getRight();
   			else return false;
  		}
  		return false;
	 }
 
 	//Method for merge sorting
 	public static ArrayList<Integer> mergeSort(ArrayList<Integer> array, int first, int last) {
  		if (first<last){
   			int mid = Math.round((last+first)/ 2);
   			mergeSort(array, first, mid);
   			mergeSort(array, mid+1, last);
	   		merge(array, first, mid, last);
  		}
  		return array;
	 }
 
 	//Method to merge two sorted data sets
 	public static ArrayList<Integer> merge(ArrayList<Integer> array, int first, int middle, int last){
  		//Set up let and right arrays
 		ArrayList<Integer> left= new ArrayList<Integer>(array.subList(first, middle+1));
  		ArrayList<Integer> right= new ArrayList<Integer>(array.subList(middle+1, last+1));
  		left.add(Integer.MAX_VALUE); right.add(Integer.MAX_VALUE);
  
  		//Merge left and right arrays
  		int l=0;
 		int r=0;
  		for (int i=first; i<=last; i++){
   			if (left.get(l) < right.get(r)) array.set(i, left.get(l++));
   			else array.set(i, right.get(r++));
  		}
  		return array;
 	}

 	//Method for binary searching
 	public static boolean binarySearch(int value, int start, int end, ArrayList<Integer> array ) {
 		if ( start>end) return false;
  			int midPoint = Math.round((start+end)/2);
  			int middle = array.get(midPoint);
  
  			if (value == middle) return true;
  			if (value<middle) return binarySearch(value, start, midPoint-1, array );
  			if (value>middle) return binarySearch(value,midPoint+1, end, array );
  			else return false;
 	}
 
 	//Method for sequential searching
 	public static boolean sequentialSearch(int value, ArrayList<Integer> array) {
  		boolean found = false;
  		for (int i=0; !found&&i<array.size(); i++){
   			if (value==array.get(i)) return true;
  			}
  		return found;
 	}

 	public static void main(String[] args) {
  
  		//Variables
  		ArrayList<Integer> dataList = new ArrayList<Integer>();
  		ArrayList<Integer> queryList = new ArrayList<Integer>();
  		int query = 0;
  		int data = 0;
  
  		try{
   			//Read File/Data Input
   			Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
   			String [] sizes = in.nextLine().split(" ");
   			data = Integer.parseInt(sizes[0]);
   			query = Integer.parseInt(sizes[1]);
   
  			//Fill data array and tree
   			long startTree = System.currentTimeMillis();
   			for (int i=0; i<data; i++){
    			//Fill data array
    			int input = in.nextInt();
    			dataList.add(input);
    
    			//Create Node and insert into tree
    			Node newNode = new Node(input, null, null, null);
    			insertValue(newNode);
   			}
   			long endTree = System.currentTimeMillis();
   			long timeTree = endTree - startTree;
   			System.out.println("Prep Time: " + timeTree + "ms");
   
   			//Fill query array
   			for (int e=0; e<query; e++) {
    			int input = in.nextInt();
    			queryList.add(input);
   			}
   
   			in.close();
  		}
  		catch(Exception e){
   			System.out.println("Input Error: " + e.getMessage());
   			System.exit(0);
  		}
  
  		//Perform mergeSort on dataList
  		long startMerge = System.currentTimeMillis();
  		dataList = mergeSort(dataList, 0, dataList.size()-1);
  		long endMerge = System.currentTimeMillis();
  		long mergeTime = endMerge - startMerge;
  		System.out.println("Prep Time: " + mergeTime + "ms");
  
  		//Search dataList for each query item in queryList
  		//Print results
  		for (int i=0; i<query; i++){
   			//Binary Tree Search
   			long startTree = System.currentTimeMillis();
   			int value = queryList.get(i);
   			boolean treeSearch = searchTree(value);
   			long endTree = System.currentTimeMillis();
   			long timeTree = endTree - startTree;
   			System.out.println(treeSearch + ":" + timeTree + "ms:" + value);
   
   			//Normal Binary Search
   			long startBin = System.currentTimeMillis();
   			boolean binary = binarySearch(value, 0, dataList.size()-1, dataList);
	   		long endBin = System.currentTimeMillis();
   			long timeBin = endBin - startBin;
   
   			//Sequential Search
  			long startSeq = System.currentTimeMillis();
   			boolean sequential = sequentialSearch(value, dataList);
   			long endSeq = System.currentTimeMillis();
   			long timeSeq = endSeq - startSeq;
   
   			System.out.println(sequential + ":" + timeSeq + "ms:" + binary + ":" + timeBin + "ms:" + value);
  }
 
 }
}
