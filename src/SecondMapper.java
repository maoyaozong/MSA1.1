import java.io.IOException;

 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
 


public class SecondMapper extends Mapper<Object,Text,Text,Text> { 
private Text secondKey=new Text(); 
private Text secondValue=new Text();  
private String seq1 = new String(); 

public void setup(Context context) 
   {
   	Configuration conf = context.getConfiguration();
   	seq1 = conf.get("id2", "Empty"); 
   }

public void map(Object key,Text value,Context context) throws IOException, InterruptedException{
	String seqi=value.toString(); 
	String name;
	int pos;
	//System.out.println("This is Ok !!");
	for(pos=0;pos<seqi.length();pos++){ 
		if(seqi.charAt(pos)!=':');
		else break;
	}
	name=seqi.substring(0,pos); 
	String seq2=seqi.substring(pos+1); 
	
	 if(seq1==null)
		   System.out.println("TranslateTheFile.seq1 is null");	 
	 
	AlignTwoSeqII at = new AlignTwoSeqII(seq1,seq2); 
	if(seq1.length()==0 || seq2.length()==0)
	{
		System.out.println("the sequence is null");
		System.exit(0);
	}
	String align_str=at.run();
	
	//System.out.println("this part is OK ");
	secondValue.set(align_str); 
	secondKey.set(name); 
	context.write(secondKey,secondValue);
}

}
