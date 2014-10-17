import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


public class Prepare_for_Map {

	/**
	 * @param args
	 */
	
	private String path;
	private int setNumber=0;
	public void set_path(String set_path){
		this.path=set_path;
	}
	
	public int  get_setNumber(){
		return setNumber;
	}
	
	
	public void Make_files(ArrayList<String> sequence_names,ArrayList<String> sequences,Set<Set> set_sum) throws IOException{ 
	//函数的输入为序列名文件，序列文件，聚类结果
		 int i=2;
		 int seqLength=sequences.size();//后去有多少条序列
		 int setSize=set_sum.size();//获取有多少个类
		 int avgNumber=seqLength/setSize;//计算平均每个类有多少个元素  【我觉得吧这个地方有待商榷】
		 System.out.println("the average number of each set is "+avgNumber);
		 path=path+"/temp";
		 
		if(!(new File(path).isDirectory()))
		//如果目录不存在就创建一个新的 loaclpath/temp
		{
			new File(path).mkdir();//创建目录
			System.out.println("create the dir "+path);
		}
		 BufferedWriter bw0 =new BufferedWriter(new FileWriter(path+"/map"));//文件都写道这里来
		
		 int flag=0;
		 for(Set<Integer> seti:set_sum){//迭代所有的聚类 
			 if(seti.size()<avgNumber){//如果某个簇中的元素小于平均值就单独写成一类
				 for(Integer indexs:seti){
					 String line;
					 line=1+"\t"+sequence_names.get(indexs)+"\t"+sequences.get(indexs)+"\t"+indexs;//这些小于平均值的簇都归为1类，然后写入序列名以及序列 
					 bw0.write(line);
					 bw0.newLine();
					 bw0.flush();
				 }				 
				 if (flag==0){//setNumber统计集合的个数，也就是最后要有几个reduce
					 setNumber++;	//只有当是第一次创建0分类的时候才对集合个数加1，否则的话是不加的			     
				 }	
				 flag=1;
			 }
			 else{ //如果类的个数大于平均值那就创建一个分组
			 for(Integer indexs:seti){
				 String line;
				 line= i+"\t"+sequence_names.get(indexs)+"\t"+sequences.get(indexs)+"\t"+indexs;
				// line = i +"\t"+indexs;
				 bw0.write(line);
				 bw0.newLine();
				 bw0.flush();				 
			    }
			 setNumber++;//增加reduce个数
			 i++;//类别标记增加一
			 }
		 }	 
	}
	
	
}
