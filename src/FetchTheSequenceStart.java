import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class FetchTheSequenceStart { 
	   static String s1; 
	String theStartPath; 
	public FetchTheSequenceStart(String thepath){ 
		this.theStartPath=thepath;
	}
	
	public void  combiner() throws IOException{ 
	int[] Space=new int[TranslateTheFile.seq1.length()+1]; 
	BufferedReader br=new BufferedReader(new FileReader(theStartPath)); 
	String line;
	
	while(br.ready()){
		
	    line = br.readLine();
	    String[] tempstring=line.split("\t"); 
	 	String seq=tempstring[2]; 
	 	String seqi=tempstring[1]; 
	 
	 	FileWriter fw = new FileWriter(MSA_console.local_path+"/seqi", true);    
		   fw.write(seqi+"\r\n");
		    
		   fw.close();
	 	
		   int index=0;
	 	int Spaceevery[] = new int[TranslateTheFile.seq1.length()+1]; 
	 	for(int j=0;j<seq.length();j++){ 
	 		if(seq.charAt(j)=='-') 
	 			Spaceevery[index]++;
	 		else{ 
	 			if(Spaceevery[index]>Space[index])  
                    Space[index]=Spaceevery[index]; 
                    index++;
	 		}
	 	}	 	
	}
	br.close();
	
	
	StringBuffer sb = new StringBuffer();
    for(int i=0;i<Space.length;i++){ 
    	/*if (Space[i]==1 || Space[i]==2)
    	{
    		Random random =new Random();
    		double ran =random.nextDouble();
    		if (ran<0.7)
    			if(i!=Space.length-1)
    		         sb.append(TranslateTheFile.seq1.charAt(i));
    			continue;    		
    	}*/
        for(int j=0;j<Space[i];j++) 
            sb.append('-');
        if(i!=Space.length-1)
         sb.append(TranslateTheFile.seq1.charAt(i)); 
    }
   s1 = sb.toString();
	if(s1==null)
		System.out.println("the s1 is null");
	}
	

}
