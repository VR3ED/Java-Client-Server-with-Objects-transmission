import java.io.Serializable;

public class TheData implements Serializable{
   int numItems;
   String Item1;
   String Item2;   
   String Item3;
   
   TheData(int n, String s1, String s2, String s3){
	   numItems=n;
	   Item1=s1;
	   Item2=s3;
	   Item3=s3;
   }
   
   TheData(){
	   numItems=0;
	   Item1="";
	   Item2="";
	   Item3="";
   }
   
   public TheData read() {
	   return this;
   }
   
   public String toString() {
	   return "["+numItems+","+Item1+","+Item2+","+Item3+"]";
	   
   }
   
   public void write(TheData x) {
	   numItems=x.numItems;
	   Item1=x.Item1;
	   Item2=x.Item2;
	   Item3=x.Item3;
   }
   
   public void compose(int n, String s1, String s2, String s3) {
	   if(n>0) {
		   numItems=n;
		   Item1=s1;
		   if(n>1) {
			   Item2=s2;
			   if(n==3) {
				   Item3=s3;
			   }
		   }
	   }
   }
   
}
