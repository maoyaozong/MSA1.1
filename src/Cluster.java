import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Cluster {
	Set<Set> set_sum=new HashSet(); 
	
	public void inite(int CABMDP_array){ 
		Set<Integer> set_1=new HashSet(); 
		set_1.add(CABMDP_array);
		set_sum.add(set_1);
	}  
	
	public Set get_set(){ 
		return set_sum;
	} 
	  
	 public void clustering(int [] CABMDP_array,int totallineNumber, double r,ArrayList<Double> distance) throws IOException{	
		double average_distance;
		
		for(int i=1;i<CABMDP_array.length;i++){  
            int index=CABMDP_array[i]; 	 
            double min_distance=100000.0; 
			Set<Integer> nearest_set=null; 
			
			for(Set<Integer> seti:set_sum){ 
	       
	                 average_distance=0.0;  
	                
	                 for(Integer set_index:seti){ 
	                	 int Index;
	                	 if(index<set_index){ 
	                		  Index=(index*(2*totallineNumber-index-1))/2+(set_index-index-1); 
	                	   }
	                	   else {
	                		   Index=(set_index*(2*totallineNumber-set_index-1))/2+(index-set_index-1);
	                	   }
	             	  average_distance+=distance.get(Index); 
	                 } 
	                  average_distance=average_distance/seti.size();  
	                  System.out.println("the average distance from "+index+" to the set"+seti.toString()+" is: "+average_distance); 
	                 
	                	  if(average_distance<min_distance){ 
	                		  min_distance=average_distance; 
	                		  nearest_set=seti; 
	                	  } 
	            }  
	            if(min_distance<r){ 
	            	nearest_set.add(index);
	            }
	            else  if(min_distance>=r){ 
	            	 Set<Integer> set_new=new HashSet(); 
              	   set_new.add(index); 
              	   set_sum.add(set_new); 
	            } 
		} 
	}
	
}
