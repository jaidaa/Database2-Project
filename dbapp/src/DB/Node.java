package DB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Node {
	int n;
	ArrayList number;
	Node parent;
	boolean isLeaf;
	
	public Node(int n,Node Parent)
	{
		this.n=n;
		number=new ArrayList<>();
		this.parent=Parent;
	}
	public Node(int n)
	{
		this.n=n;
		number=new ArrayList<>();
		this.parent=null;
	}
	
	public void insertInNode(Object v){
		boolean isFull=true;
		if(number.size()!=n){
		for(int i=0;i<n;i++){
			if(number.get(i)==null){
				number.add(v);
				isFull=false;
				break;
				
			}
		}
		
			
			//Split and insert in parent
		
			
	}
	}
	public void insertInNodeREC(Object v){
		if(number.size()<n){
			this.number.add(v);
			return;
			}
		if(this.parent==null){
			//split el parent w neshta3'al
			Node nr=new Node(n);
			Node node=new Node(n,nr);
			this.number.add(v);
			Collections.sort(number);
			
			for(int k=0;k<n+1;k++){
				if(k==(n+1)/2){
					
					nr.insertInNodeREC(number.get(k));
					
					
					
				}else{
					if(k>(n+1)/2){
						node.insertInNodeREC(number.get(k));
					}
				}
				for (int z=0;z<n+1;z++){
					if(k==(n+1)/2){
						
						number.remove(k);
						
						
						
					}else{
						if(k>(n+1)/2){
							number.remove(k);
						}
					}
					
					
				}
				
				
			}
			
			
			
		}
		
		
		
		
		
		parent.insertInNodeREC(v);
			
		
		}
			
		
		
	
	public static void main(String[] args) {
		int d  = (int) (3*Math.random()+1);
		System.out.println(d);
	}
	

}
