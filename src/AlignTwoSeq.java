
public class AlignTwoSeq {
        int g=10,h=2,M=1,misM=-1,rate = 2;   
	    String s,t;
	    static int maxk;
	    boolean flag = true;
	    public AlignTwoSeq(String s, String t) { 	    	
	        if(s.length()<t.length()){ 
	            this.s = s;
	            this.t = t;
	        }
	        else{
	            this.s = t;
	            this.t = s;
	            flag = false;
	        }
	   //System.out.println("the s is :"+this.s.length());
	   //System.out.println("the t is :"+this.t.length());
	    }
	    public void run(){
	    	 int mn = Math.abs(s.length()-t.length()); 
	         int k = rate*mn+1; 
	         int pok,m = s.length(),n=t.length();   
	         
	         do{	       
	      //System.out.println("the error place 1");
	             AffineGapPenalty aff = new AffineGapPenalty(m, n, g, h, k, mn, s, t,M,misM);
	              maxk = aff.Init(); 
	           
	             pok=M*(n-k-1)-2*(k+1)*(h+g); 
	             if(maxk<pok) 
	                 {k=k*2;
	    //System.out.println(maxk);
	   // System.out.println(k);
	   //System.out.println(pok);
	                 }
	             else break;	   
	//System.out.println("the error place 3");
	         }while(k<=pok);
	         
	         int ch=1; 
	         if(maxk==AffineGapPenalty.a[m][n+k-m]){ch=1;}
	         else if(maxk==AffineGapPenalty.b[m][n+k-m]){ch=2;}
	         else {ch=3;}//(k==AffineGapPenalty.c[m][n+k-m])
	//System.out.println("this part is ok 3");
	         Make make=new Make(g,h,m,n,k,mn,s,t,flag);
	         make.f(ch);
	//System.out.println("this part is ok 4");
	    }
}
