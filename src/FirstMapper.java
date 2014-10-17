import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.TaskAttemptID;
 


public class FirstMapper extends Mapper<Object,Text,Text,Text> { 
	private Text firstKey=new Text(); 
	private Text firstValue=new Text(); 
    private String seq1 = new String(); 
   
	 public void setup(Context context) 
	    {
	    	Configuration conf = context.getConfiguration();
	    	seq1 = conf.get("id", "Empty");
	    	 
	    }
  
	public void map(Object key,Text value,Context context) throws IOException, InterruptedException{
		   
	     firstKey.set("one"); 
		String value_temp=value.toString(); 
		String name;
		int p1;
		for(p1=0;p1<value_temp.length();p1++){ 
			if(value_temp.charAt(p1)!=':');
			else break;
		}		
		name=value_temp.substring(0,p1); 
		String seq2=value_temp.substring(p1+1);	 
		 
	   if(seq1==null)
		   System.out.println("TranslateTheFile.seq1 is null");	 
	   
		AlignTwoSeq ats = new AlignTwoSeq(seq1,seq2); 
		ats.run(); 
	 
		firstValue.set(name+":"+Make.alignt+"\t"+Make.aligns); 
		 
		context.write(firstKey,firstValue); 
	  }
}
