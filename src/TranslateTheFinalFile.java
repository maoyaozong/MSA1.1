import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class TranslateTheFinalFile { 
	 
	String inputfile;
	 String outputfile;
	 String sequence_name_file;
	 String sequecne_file;
	public TranslateTheFinalFile(String inputfile,String outputfile,String sequence_name_file,String sequecne_file){ 
    	 this.inputfile=inputfile;  
    	 this.outputfile=outputfile; 
    	 this.sequence_name_file=sequence_name_file; 
    	 this.sequecne_file=sequecne_file; 
     }
        
     public void translate() throws IOException{
 		BufferedReader br=new BufferedReader(new FileReader(inputfile)); 
     	BufferedWriter bw_temp=new BufferedWriter(new FileWriter(outputfile)); 
     	BufferedWriter bw_sequence_name =new BufferedWriter(new FileWriter(sequence_name_file)); 
     	BufferedWriter bw_sequence=new BufferedWriter(new FileWriter(sequecne_file)); 
              String line_temp=null;    
        while(br.ready()){
        	line_temp=br.readLine(); 
        	 String[] line=line_temp.split("\t"); 
        	 bw_sequence_name.write(line[0]); 
        	 bw_sequence_name.newLine();
        	 bw_sequence_name.flush();
        	 bw_sequence.write(line[1]); 
        	 bw_sequence.newLine();
        	 bw_sequence.flush();
        	bw_temp.write(line[0]); 
        	bw_temp.newLine();
        	int j=0;
        	int i=0;
        	while(i<line[1].length()){ 
        		 j=i;
        		// System.out.println(j);
        		i=i+60;
        		//System.out.println(i);
        		if(i<line[1].length()){
        		String tline=line[1].substring(j,i);
        		bw_temp.write(tline);
        		bw_temp.newLine();
        		bw_temp.flush();
        		}
        		else{
        			String tline=line[1].substring(j);
                	bw_temp.write(tline);
                	bw_temp.newLine();
                	bw_temp.flush();
        		}
        	}
        	
        }
     br.close();
     bw_temp.close();	 
	// File filed=new File(inputfile);// delete the local output file 
	 //filed.delete();
		
         
    	 
     }
}
