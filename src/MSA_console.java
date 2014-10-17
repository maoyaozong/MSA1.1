import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path; 
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MSA_console {
	static String local_path;
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		 if(args.length!=3){
			System.out.println("please input the right parameter");
			System.out.println(args.length+" "+args[0]);
			System.exit(0);
		} 
	 
		 String filename=args[0]; 
		 local_path=args[1]; 
	     String dfs_path=args[2]; 
		
		 TranslateTheFile KVfile=new TranslateTheFile(local_path+"/"+filename,local_path+"/inputKV"); 
		KVfile.translate();	
	  
		Configuration conf1=new Configuration();	 	
	 	FileSystem fs =FileSystem.get(conf1);
	 	Path src=new Path(local_path+"/inputKV"); 
	    Path dst=new Path(dfs_path+"/MSAinput/inputKV"); 
	    fs.copyFromLocalFile(src,dst); 
	    conf1.set("id", TranslateTheFile.seq1); 
	    conf1.set("mapred.task.timeout","0");  
		Job job=new Job(conf1,"MSA_step1");
	 	job.setJarByClass(MSA_console.class);
	 	job.setInputFormatClass(TextInputFormat.class);	
	 
	 	job.setMapperClass(FirstMapper.class); 
	 	job.setMapOutputKeyClass(Text.class); 
	 	job.setMapOutputValueClass(Text.class); 
	 
	 	FileInputFormat.addInputPath(job,new Path(dfs_path+"/MSAinput/inputKV")); 
	 	FileOutputFormat.setOutputPath(job, new Path(dfs_path+"/out")); 
	 	job.setNumReduceTasks(1); 
	 	job.waitForCompletion(true);
	 	 
	 	
	 	Configuration conf3=new Configuration();
	 	FileSystem fs1=FileSystem.get(conf3);
	 	Path local_path1=new Path(local_path+"/squencestart"); 
	 	Path fs_path=new Path(dfs_path+"/out/part-r-00000"); 
	 	fs1.copyToLocalFile(true, fs_path, local_path1); 
	 	FetchTheSequenceStart ftss =new FetchTheSequenceStart(local_path+"/squencestart"); 
	 	ftss.combiner();
	 	File filed2=new File(local_path+"/squencestart"); 
	 	filed2.delete();
	 	 
		Configuration conf2=new Configuration(); 	
		FileSystem seq1_file=FileSystem.get(conf2);	 
		conf2.set("id2",FetchTheSequenceStart.s1);  	
		Path seqi_src_1=new Path(local_path+"/seqi"); 
	    Path seqi_dst_2=new Path(dfs_path+"/seqi/seqi"); 
	    seq1_file.copyFromLocalFile(seqi_src_1,seqi_dst_2); 
	    System.out.println("the FetchTheSequenceStar is "+FetchTheSequenceStart.s1);
		//System.exit(0);
		
	    Job job2=new Job(conf2,"MSA_step2");
	    //conf2.set("dfs.blocksize","1048576");
	 	job2.setJarByClass(MSA_console.class);	
	 	FileInputFormat.addInputPath(job2, new Path(dfs_path+"/seqi/seqi")); 
	 	FileOutputFormat.setOutputPath(job2,new Path(dfs_path+"/theFinaloutput")); 
	 	System.out.println("we are going to start the second mapper");
	 	job2.setInputFormatClass(TextInputFormat.class);	
	 	job2.setMapperClass(SecondMapper.class); 
		job2.setOutputKeyClass(Text.class); 
	 	job2.setOutputValueClass(Text.class); 
	 	job2.setNumReduceTasks(1); 
		job2.waitForCompletion(true);
		File filed=new File(local_path+"/seqi"); 
		filed.delete();
		System.out.println("the second part is Ok");
		
		System.out.println("star to translate to standard format"); 
		Configuration conf4=new Configuration();
		FileSystem final_file =FileSystem.get(conf4);
		Path final_path=new Path(dfs_path+"/theFinaloutput/part-r-00000"); 
		Path final_loacal_path=new Path(local_path+"/OutPut"); 
		 final_file.copyToLocalFile(true, final_path,final_loacal_path); 
	//	 Path final_local_output=new Path(local_path+"/finalOutPut");
		 String sequence_name_file=local_path+"/sequence_name_file"; 
		 String sequence_file=local_path+"/sequence_file"; 
		 TranslateTheFinalFile final_output=new TranslateTheFinalFile(local_path+"/OutPut",local_path+"/finalOutPut",sequence_name_file,sequence_file);
		 final_output.translate();	 
		 
	}
}
