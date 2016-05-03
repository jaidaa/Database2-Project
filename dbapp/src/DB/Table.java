package DB;
import java.io.*;
import java.util.*;
public class Table implements Serializable{
	
	String tableName;
	ArrayList <String> pages =new ArrayList<String>();
	int i=1;
	int columns;
	String primaryKey;
	public Table(String tableName,int columns,String primaryKey) throws FileNotFoundException, IOException{
		this.tableName=tableName;	
		Page init=new Page(columns, tableName,i);
		this.primaryKey=primaryKey;
		pages.add(tableName+i+".class");
		this.columns=columns;
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(tableName+".class")));
		oos.writeObject(this);
		oos.close();
		
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(new File(tableName+i+".class")));
		oos2.writeObject(init);
		oos2.close();
	}
	public boolean insertIntoTable(Hashtable<String,Object> htblColNameValue) throws FileNotFoundException, IOException, ClassNotFoundException{
		  
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(tableName+i+".class")));
	        Page p = (Page)ois.readObject();
	        ois.close();
	        String returnValue=p.insertIntoPage(htblColNameValue);
	        if(returnValue.equals("size problem"))
	        {
	        	
	        	i++;
	        	Page pa=new Page(columns, tableName,i);
	        	pages.add(tableName+i+".class");
	        	String returnValue2=pa.insertIntoPage(htblColNameValue);
	        	if(returnValue2.equals("false"))
	        		return false;
	        	else{
	        		
	        		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(tableName+".class")));
	           		oos.writeObject(this);
	            	oos.close();
	        		return true;
	        	
	        	}
	        }
	        else{
	        	if(returnValue.equals("false"))
	        		return false;
	        	else{
	        		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(tableName+".class")));
	        		oos.writeObject(this);
	        		oos.close();
	        		return true;
	        }
	        
	        
		
	}
	

}   public void deleteFromTable(String tableName,Hashtable<String,Object>htblColNameValue,String operator) throws IOException, ClassNotFoundException{
	


	ArrayList colnumber=new ArrayList<>();
	ArrayList colValues=new ArrayList<>();
	ArrayList colKeys=new ArrayList<>();
	  String newLine;
    	 String type;
    	
    	Set set = htblColNameValue.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
        	int counter=0;
            Map.Entry entry = (Map.Entry) it.next();
            String key=entry.getKey()+"";
            BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
        
         while ((newLine = br.readLine()) != null) {
          	String[] v=newLine.split(",");
          	if(v[0].equals(tableName)){
          		counter++;
          		if(v[1].equals(key)){
          			counter--;
          		colnumber.add(counter);//column number
          		colValues.add(entry.getValue());//the value to check for in that column
          		
          		colKeys.add(entry.getKey());
          		
          		break;
          		}
          		}
          	
          }
          
         br.close();
	
}
        int sizepages=this.pages.size();
        for(int i=0;i<sizepages;i++){
        	String pageToLoad=this.pages.get(i);
        	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(pageToLoad)));
            Page p = (Page)ois.readObject();
            ois.close();
            p.deleteFromPage(tableName, operator, colnumber, colValues,colKeys);
        }
	
	
}
	
	
	public  ArrayList selectFromTable2 (String tableName,Hashtable<String,Object>htblColNameValue,String operator) throws IOException, ClassNotFoundException
	{
		

		ArrayList result =new ArrayList <>();
		ArrayList colnumber=new ArrayList<>();
		ArrayList colValues=new ArrayList<>();
		ArrayList colKeys=new ArrayList<>();
		  String newLine;
	    	 String type;
	    	
	    	Set set = htblColNameValue.entrySet();
	        Iterator it = set.iterator();
	        while (it.hasNext()) {
	        	int counter=0;
	            Map.Entry entry = (Map.Entry) it.next();
	            String key=entry.getKey()+"";
	            BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
	        
	         while ((newLine = br.readLine()) != null) {
	          	String[] v=newLine.split(",");
	          	if(v[0].equals(tableName)){
	          		counter++;
	          		
	          		System.out.print("-----"+v[1]+"-----");
	          		if(v[1].equals(key)){
	          			counter--;
	          			System.out.println(counter);
	          		colnumber.add(counter);
	          		colValues.add(entry.getValue());
	          		
	          		colKeys.add(entry.getKey());
	          		System.out.println();
	          		break;
	          		}
	          		}
	          	
	          }
	          
	         br.close();
		
	}
	        int sizepages=this.pages.size();
	        for(int i=0;i<sizepages;i++){
	        	String pageToLoad=this.pages.get(i);
	        	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(pageToLoad)));
	            Page p = (Page)ois.readObject();
	            ois.close();
	        	ArrayList intermediateList=p.selectFromPage(tableName, operator, colnumber, colValues,colKeys);
	        	result.addAll(intermediateList);
	        	
	        	
	        }
						return result;
	

}
	
	public void updateTable(String strTableName, String strKey,
                            Hashtable<String,Object> htblColNameValue) throws IOException, ClassNotFoundException{

		ArrayList colnumber=new ArrayList<>();
		ArrayList colValues=new ArrayList<>();
		ArrayList colKeys=new ArrayList<>();
		  String newLine;
	    	 String type;
	    	
	    	Set set = htblColNameValue.entrySet();
	        Iterator it = set.iterator();
	        while (it.hasNext()) {
	        	int counter=0;
	            Map.Entry entry = (Map.Entry) it.next();
	            String key=entry.getKey()+"";
	            BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
	        
	         while ((newLine = br.readLine()) != null) {
	          	String[] v=newLine.split(",");
	          	if(v[0].equals(tableName)){
	          		counter++;
	          		if(v[1].equals(key)){
	          			counter--;
	          		colnumber.add(counter);//column number
	          		colValues.add(entry.getValue());//the value to check for in that column
	          		
	          		colKeys.add(entry.getKey());
	          		
	          		break;
	          		}
	          		}
	          	
	          }
	          
	         br.close();
		
	}
	        
	        int sizepages=this.pages.size();
	        for(int i=0;i<sizepages;i++){
	        	String pageToLoad=this.pages.get(i);
	        	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(pageToLoad)));
	            Page p = (Page)ois.readObject();
	            ois.close();
	            p.updatePage(strTableName, strKey, colnumber, colValues,colKeys,primaryKey);
	        }
		 
	        
	        
}
}


