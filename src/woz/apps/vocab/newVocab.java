package woz.apps.vocab;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import woz.apps.vocab.*;
import android.text.method.*;

// need to ensure correct version of fragment used
public class newVocab extends android.support.v4.app.Fragment implements View.OnClickListener{
	private Button  mShowButton, mDirection,mDownLevel,mNextButton,mUpLevel,mChangeFilter,mPeriodButton;
	private TextView mLang1,mLang2,mLevel,mLastRev,mShowScore;
	private vocabItem currentItem;
	private boolean EngSpan=true;
	private int currentLevel=0;
	private int filter=mainVocab.NOFILTER;
	private boolean todayOnly=true;
	
// factory method preferred to no explicit constructor
public static newVocab newInstance() {
	return new newVocab();
}
	
@Override
public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
}
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
	View v=inflater.inflate(R.layout.frag1_vocab,parent,false);
	mDirection=(Button) v.findViewById(R.id.direction);
	if (EngSpan) mDirection.setText(R.string.EngSpan); else mDirection.setText(R.string.SpanEng);
		mDirection.setOnClickListener(this);
		mNextButton=(Button) v.findViewById(R.id.next_button); 
		mNextButton.setOnClickListener(this);
		mLang1=(TextView) v.findViewById(R.id.lang1); 
		mLang1.setMovementMethod(new ScrollingMovementMethod());
	    mLang2=(TextView) v.findViewById(R.id.lang2);	
	    mLang2.setMovementMethod(new ScrollingMovementMethod());
	    mLastRev=(TextView) v.findViewById(R.id.lastRev);	
	    mLevel=(TextView) v.findViewById(R.id.level);	
		//mShowFilter=(TextView) v.findViewById(R.id.showFilter);
		mShowButton=(Button) v.findViewById(R.id.show_button); 
		mShowButton.setText("SHOW");mLang2.setText("");
		mShowButton.setOnClickListener(this);
		mPeriodButton=(Button) v.findViewById(R.id.periodButton); 
		mPeriodButton.setOnClickListener(this);
		mUpLevel=(Button) v.findViewById(R.id.upLevel); 
		mUpLevel.setOnClickListener(this);
		mDownLevel=(Button) v.findViewById(R.id.downLevel); 
		mDownLevel.setOnClickListener(this);
		mChangeFilter=(Button) v.findViewById(R.id.changeFilter);
		mChangeFilter.setOnClickListener(this);
	    
		if (currentItem==null) currentItem=mainVocab.vocab.getItem(mainVocab.NOFILTER,todayOnly);
	    if (currentItem!=null) {

		currentLevel=currentItem.level;
		show(); }  
		return v;
}
	
public void onClick(View v) {	
  switch(v.getId()) {
		
      case R.id.next_button:
	    mLang2.setText("");   
		mShowButton.setText("SHOW");
		update();
		currentItem=mainVocab.vocab.getItem(filter,todayOnly);
		  if (currentItem!=null) {
			  currentLevel=currentItem.level;
			  show();   
		  }
	  break;
	
	  case R.id.show_button:    
		  if (currentItem== null) break; 
		  if (mShowButton.getText()=="SHOW") {
			  mShowButton.setText(" HIDE  ");
		      if (EngSpan) mLang2.setText(currentItem.mLang2);
		      else mLang2.setText(currentItem.mLang1);
		      }
		   else {mShowButton.setText("SHOW"); mLang2.setText("");}
		  break;

	  case R.id.upLevel: 
		  if (currentLevel<4) currentLevel++; 
		  show();
		  break;

	  case R.id.downLevel:
		  if (currentLevel>0) currentLevel--;
		  show();
		  break;
		  
	  case R.id.changeFilter:
		  filter++;
		  if (filter==5) filter=mainVocab.NOFILTER;
		  show();
		  break;
		  
	  case R.id.periodButton:
		  if (todayOnly) todayOnly=false; else todayOnly=true;
		  show();
	  break;
	  
	  case R.id.direction:
		  mLang2.setText("");
		  mShowButton.setText("SHOW");
		  if (EngSpan) { 
			  EngSpan=false; show(); 
			  mDirection.setText(R.string.SpanEng);	
		  } else {
			  EngSpan=true; show();
			  mDirection.setText(R.string.EngSpan);
		  }
		  break;           
	 }
}
	
@Override
public void onDestroy() {
		super.onDestroy();
		update();
	    mainVocab.vocab.saveVocab(getActivity());
	    Toast.makeText(getActivity(), "Saving", Toast.LENGTH_LONG).show();
}
	
public void show() {
	if (currentItem!=null) {
	mLastRev.setText("Reviewed: "+currentItem.lastRev.toString().substring(0,20)); //+now.toString().substring(11,19));
	if (EngSpan) mLang1.setText(currentItem.mLang1);
	else mLang1.setText(currentItem.mLang2);
	mLevel.setText("Level: "+currentLevel);
	}
	if (filter!=mainVocab.NOFILTER) mChangeFilter.setText("FILTER:  "+filter+" ");
	else mChangeFilter.setText("NO FILTER");
	if (todayOnly) mPeriodButton.setText("TODAY ONLY"); else mPeriodButton.setText("ALL TIME");
}

public void update(){
	if (currentItem!=null) {
		currentItem.level=currentLevel;
		Date now=new Date();
	    currentItem.lastRev=now;
		//Toast.makeText(getActivity(), now.toString().substring(11,19), Toast.LENGTH_SHORT).show();
	    mainVocab.vocab.updateItem(currentItem);
		}
}
	
private void clearFields() {
    mLang1.setText("");
    mLang2.setText("");
    }
}
