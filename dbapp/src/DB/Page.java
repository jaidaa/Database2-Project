package DB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Page implements Serializable{
String pagename;
String tableName;
int recordIndex;
String[][] records;
int columns;
int pageindex;
	public Page(int columns,String tableName,int pageindex){
		recordIndex=0;//recordindex beyziid b wa7ed 
		this.records=new String [200][columns+1];
		this.columns=columns;
		this.tableName=tableName;
		this.pageindex=pageindex;
	}
	//
	public String insertIntoPage(Hashtable<String,Object> htblColNameValue) throws IOException {
		Set set = htblColNameValue.entrySet();
        Iterator it = set.iterator();
       
        
        if(recordIndex>=200)
        	return "size problem";
        	
        
        
        
        
       /* if(columns!=htblColNameValue.size()){
        	System.out.println("Error:Missing an entry to insert a record!!");
        	return "false";
        }*/
        	int i=0;
        while (it.hasNext()) {
          Map.Entry entry = (Map.Entry) it.next();
          Object value=entry.getValue();
          
          String type=DBApp.metadataValues(tableName,entry.getKey()+"");
          
         /* if(type.equals("String")){
        	  if(!(value instanceof String))
        		  System.out.println("Error This column only accepts Strings");
        	  return "false";
          }
          
          if(type.equals("Integer")){
        	  if(!(value instanceof Integer))
        		  System.out.println("Error This column only accepts Integers");
        	  return "false";
          }
          
          if(type.equals("Double")){
        	  if(!(value instanceof Double))
        		  System.out.println("Error This column only accepts Doubles");
        	  return "false";
          }
          
          if(type.equals("Date")){
        	  if(!(value instanceof Date))
        		  System.out.println("Error This column only accepts Dates");
        	  return "false";
          }
          
          if(type.equals("Boolean")){
        	  if(!(value instanceof Boolean))
        		  System.out.println("Error This column only accepts Booleans");
        	  return "false";
          }
        }
        */
        Set set2 = htblColNameValue.entrySet();
        Iterator it2 = set.iterator(); 
        int j=0;
        while (it2.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it2.next();
            Object value2=entry2.getValue();
            
           String newLine;
      	  ArrayList <String>result=new ArrayList<String>(); 
      	BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
      	 
            while ((newLine = br.readLine()) != null) {
            	String[] v=newLine.split(",");
            	if(v[0].equals(tableName)){
            		j++;
            		if(v[1].equals(entry2.getKey())){
            			j--;
            			break;
            		}
            		}
            	
            	
            }
            br.close();
             
            records[recordIndex][j]= value2+"";
            String[]v=records[recordIndex];
            v[v.length-1]="true";
           
            j=0;
	}
        
        

}
        for(int u=0;u<records[recordIndex].length;u++){
        	if(records[recordIndex][u]==null)
        		records[recordIndex][u]="NA";
        }
        
        recordIndex++;
       // System.out.println(recordIndex);
        
    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(tableName+pageindex+".class")));
   		oos.writeObject(this);
    	oos.close();
        

        
        return "true";
	}

	
	public void deleteFromPage(String tableName,
			String operator,ArrayList colnumber,ArrayList colValues,ArrayList colKeys){
		
		
		String[][] temp = new String[200][this.columns];
	    int tempRows=0;
		
		//OR operator
		if(operator.equalsIgnoreCase("OR")){
			
			//int index=recordIndex-1;
		
	for(int i=0;i<recordIndex;i++){
		
		for(int g=0;g<colnumber.size();g++){
			
			int columnIndex=(int) colnumber.get(g);
			if((records[i][columnIndex].equals(colValues.get(g)))){//law msh zayo
				// i will copy this row as it is 
				records[i][records[i].length-1]="false";
				}
				
				break;
				
			}
			
			
		}
		//had5ol 3ala el row eli ba3do mn gher ma3melo copy, w msh ha incremenet el temp Rows bas ha increment el records row 3ashan adol 3ali ba3do
		}
	   
		//AND operator
		else{
			for(int k=0;k<recordIndex;k++){
				
					
					int columnIndexcond1=(int) colnumber.get(0);
					int columnIndexcond2=(int) colnumber.get(1);
					if((records[k][columnIndexcond1].equals(colValues.get(0)) 
							&& records[k][columnIndexcond2].equals(colValues.get(1)))){
						// i will copy this row as it is 
						records[k][records[k].length-1]="false";
							
						}
						

				
		
			}
			}
		
		
		
		///hena ba2a lazem a3ml copy lel temp array fl records array
		
	
		
		
	}
	
	
	public ArrayList selectFromPage(String tableName,
			String operator,ArrayList colnumber,ArrayList colValues,ArrayList colKeys){
		
		ArrayList result=new ArrayList<>();
		//OR 
		if(operator.equalsIgnoreCase("OR")){
			
		
				int index=recordIndex-1;
				
			
		for(int i=0;i<index;i++){
			for(int g=0;g<colnumber.size();g++){
				
				
				//if(colValues.get(j).equals(null))
					//System.out.println("da telete b null");
			
				//records[i][columnIndex] da fih moshkela tele3 b null
				int columnIndex=(int) colnumber.get(g);
				//System.out.println("columns: "+columns+" --"+tableName+"-----"+"columnIndex: "+columnIndex);
				if(records[i][columnIndex].equals(colValues.get(g))
						&& records[i][records[i].length-1].equals("true")){
				
					result.add(records[i]);
				
					
				}
			}
			}
		}
			
			//AND
		else{
			for(int k=0;k<recordIndex;k++){
				
					
					int columnIndexcond1=(int) colnumber.get(0);
					int columnIndexcond2=(int) colnumber.get(1);
					if(records[k][columnIndexcond1].equals(colValues.get(0)) && 
							records[k][columnIndexcond2].equals(colValues.get(1))
							&& records[k][records[k].length-1].equals("true")){
						result.add(records[k]);
						
				}
		
			}
			}

	
		for(int c=0;c<result.size();c++){
			String[]h=(String[]) result.get(c);
			for(int y=0;y<h.length;y++){
				System.out.print("-----"+h[y]+"-----");
				
			}
			System.out.println();
		
		}
		
	
	return result;//result is an arraylist of arrays
	}
	public void updatePage(String strTableName, String strKey, ArrayList colnumber, ArrayList colValues,
			ArrayList colKeys, String primaryKey) throws IOException {
		  String newLine;
	    	 String type;
	    	
	    	int counter=0;
	            BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
	        
	         while ((newLine = br.readLine()) != null) {
	          	String[] v=newLine.split(",");
	          	if(v[0].equals(tableName)){
	          		counter++;
	          		if(v[1].equals(primaryKey)){
	          			counter--;
	          		
	          		
	          		break;
	          		}
	          		}
	          	
	          }
	          
	         br.close();
	        
	
	for(int o=0;o<records.length;o++){
		String[] arr=records[o];
		if(arr[counter].equals(strKey)){
			for(int c=0;c<colnumber.size();c++){
				int col=(int) colnumber.get(c);
				String val=colValues.get(c)+"";
				if(arr[arr.length-1].equals("true"))
				arr[col]=val;
				else
					System.out.println("Can not update this record as it had been deleted :(");
				
				
				
			}
			
			
			
			
			
		}
		
		
		
		
	}
}
		
	



}