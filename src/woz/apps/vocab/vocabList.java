package woz.apps.vocab;
import android.content.*;
import android.os.*;
import java.io.*;
import java.util.*;
import java.text.*;


public class vocabList{
	private ArrayList<vocabItem> mList= new ArrayList<vocabItem>();
	String root=Environment.getExternalStorageDirectory().toString();
    Integer nImport,currentItem;
	java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("dd-M-yyyy HH:mm:ss");
	
public Integer initialize(Context mycon) {
	   nImport=0; 
	   File vocab=new File(mycon.getFilesDir()+"/vocabFile.txt");
	   //if(vocab.exists()) vocab.delete();
	   if(!vocab.exists())
		 {try	       
		   {// nb file must be in utf-8 format too
		   BufferedReader br = new BufferedReader(new InputStreamReader(mycon.getAssets().open("data/vocab.txt"),"utf-8"));		  		  
		   String strline,l1,l2;
		   while((strline = br.readLine())!= null && nImport<100) 
			     {l1=strline.substring(0,strline.indexOf(";"));
			      l2=strline.substring(strline.indexOf(";")+1);
                  vocabItem newitem=new vocabItem();
                  newitem.mLang1=l1; 
				  newitem.mLang2=l2;
				  newitem.lastRev=new Date();
				  newitem.level=0;
                  mList.add(newitem);
			      nImport++;
                  }	
              br.close(); 
			  saveVocab(mycon);
			  //mList.clear();
			  }  // end try
			   catch (Exception e) {System.out.println("output error:"+e); } 
		  }
		  return nImport;}
		  
	public void saveVocab(Context mycon)
	   {try
		   {
		   //BufferedWriter output=new BufferedWriter(new FileWriter(root+"/vocabFile.txt"));
		   BufferedWriter output=new BufferedWriter(new FileWriter(mycon.getFilesDir()+"/vocabFile.txt"));
		   output.write(mList.size()+"\n");
		   for (int i=0; i<=mList.size()-1;i++)
		   {output.write("*********"+"\n"+mList.get(i).mLang1+"\n"+mList.get(i).mLang2+"\n"+dateFormat.format(mList.get(i).lastRev)+"\n"+mList.get(i).level+"\n");}
		   output.close();
		   mList.clear();
           }
		catch (Exception e) {System.out.println("save error");}
		}
	  
	public Integer loadVocab(Context mycon)
          {try 
			  { 
			  BufferedReader input= new BufferedReader(new FileReader(mycon.getFilesDir()+"/vocabFile.txt"));	
			Integer nInList=Integer.valueOf(input.readLine());	
			nInList=10;
			mList.clear();
		    for (int i=1; i<=nInList;i++) {
				  vocabItem newitem=new vocabItem();
				  input.readLine();
                  newitem.mLang1=input.readLine();
				  newitem.mLang2=input.readLine();
					  try {newitem.lastRev=dateFormat.parse(input.readLine());} 
					  catch (Exception e) {System.out.println("date error "+e);}
				  newitem.level=Integer.valueOf(input.readLine());
                  mList.add(newitem);}
		    input.close();
			}
				catch (Exception e) {System.out.println("input error:"+e);
		   } // end try
    	return mList.size();
	}
	
	public vocabItem getItem(Integer filter,boolean todayOnly) {
		currentItem=findOldest(filter,todayOnly);
		System.out.println("get oldest"+currentItem);
		if (currentItem !=mainVocab.NOTFOUND) return mList.get(currentItem); else return null;
    }    
	
	public void updateItem(vocabItem vItem) {
		System.out.println("update"+currentItem);
		if (currentItem!=mainVocab.NOTFOUND) mList.set(currentItem,vItem);
	}
	
	private Integer findOldest(Integer filter,boolean todayOnly) {
		 int oldest=mainVocab.NOTFOUND;
		 Date today=new Date();
		 long oldTime=today.getTime();
		 for (int i=0; i < mList.size();i++) {
			Date cDay= mList.get(i).lastRev;
		    long cTime=cDay.getTime();
		    int cLevel=mList.get(i).level; 
			Boolean skip=false;
			if (todayOnly&&false==((today.getDate()==cDay.getDate())&& (today.getMonth()==cDay.getMonth()) && (today.getYear()==cDay.getYear()))) skip=true;
			if (cTime>oldTime) skip=true;
			if (false==(filter==mainVocab.NOFILTER|| filter==cLevel)) skip=true;
			if (skip==false) {oldest=i; oldTime=cTime;}
		 }
		return oldest;
	 }
	}



 
