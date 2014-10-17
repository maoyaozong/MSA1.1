import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class TranslateTheFile { 
	static String seq1; 
	String inputfile;
	String outputfile;
	public TranslateTheFile(String inputfile,String outputfile){ 
    	 this.inputfile=inputfile; 
    	 this.outputfile=outputfile; 
     }
        
     public void translate() throws IOException{
    	 
    	 BufferedReader br1=new BufferedReader(new FileReader(inputfile)); 
    	 String line1="";
    	 String cur1="";
    	 int average_length=0; 
    	 int count=0; 
    	 line1=br1.readLine();
    	 while(br1.ready()&&count<10){
    		 while(line1.length()==0&&br1.ready()){  
      			  line1=br1.readLine();
      		  }
    		 if(line1.charAt(0)=='>') 
    			 line1=br1.readLine(); 
    		 while(line1.length()==0&&br1.ready()){ 
     			  line1=br1.readLine();
     		  }
    		 while(line1.length()!=0 && line1.charAt(0)!='>'){ 
    	   			  cur1=cur1+line1; 
    	   			  line1=br1.readLine(); 
    	   			  if(!br1.ready())
    	   				  break; 
    	   			  while(line1.length()==0&&br1.ready()){ 
    	   				  line1=br1.readLine();
    	   			  } 
    	   			if(!br1.ready())
  	   				  break; 
    	   		  }	  
    		 average_length+=cur1.length(); 
    		 cur1=""; 
    		 count++; 
    	 } 
    	 average_length=average_length/count; 
    	 System.out.println("the average_length is "+average_length );
    	 
		 
    	 BufferedReader br=new BufferedReader(new FileReader(inputfile)); 
    	 BufferedWriter bw=new BufferedWriter(new FileWriter(outputfile));  
   	  String line="";
   	  String wline="";
   	  String cur="";
   	  String prefix="";
   	  line=br.readLine();
   	  while(br.ready()){ 
   		  
   		 while(line.length()==0&&br.ready()){ 
   			  line=br.readLine();
   		  }
   		  if(line.charAt(0)=='>'){ 
   			  prefix=line;  
   			  line=br.readLine(); 
   		  } 
   		 while(line.length()==0&&br.ready()){ 
  			  line=br.readLine();
  		  }
   		while(line.length()!=0 && line.charAt(0)!='>'){  
   			line=line.toUpperCase(); 
   			for(int i=0;i<line.length();i++){   	             		 
      			if(line.charAt(i)!='A'&&line.charAt(i)!='T'&&line.charAt(i)!='C'&&line.charAt(i)!='G'){  
      				if(i==line.length()-1){ 
      					if(line.length()==1){ 
      						line=""; 
      					}
      					else{ 
      					line=line.substring(0,i); 
      					}
      				} 
      				else if(i==0){ 
      					line=line.substring(i+1); 
      				}
      				else{ 
      					line=line.substring(0,i)+line.substring(i+1,line.length()); 
      					i=i-1;  
      				   }
      		        }
      	        } 
   			  cur=cur+line; 
   			  line=br.readLine(); 
   			  if(!br.ready())
   				  break; 
   			  while(line.length()==0&&br.ready()){ 
   				  line=br.readLine();
   			  } 
   		  }		
   		 
   		 if (cur.length()<0.75*average_length || cur.length()>1.25*average_length){ 
   			 cur="";  
   			 line=br.readLine(); 
   		 }	
   		
   	  if(cur.length()!=0){ 
   		  wline=prefix+":"+cur;  
   		  cur="";
   		  bw.write(wline);
   		  bw.newLine();
   		  bw.flush();
   		  }   
   	  } 
   	  bw.close();
   	  br.close();
	  
         System.out.println("Translate OK ");
     	 br=new BufferedReader(new FileReader(outputfile));
     	 line=br.readLine(); 
     	 int p1;
     	for(p1=0;p1<line.length();p1++){ 
    		if(line.charAt(p1)!=':');
    		else break;
    	}		    
       br.close();
       
    	seq1=line.substring(p1+1); 
    	if(seq1==null)
    		System.out.println("in the TranslateTheFile the seq1 is null");
     
     }
}
