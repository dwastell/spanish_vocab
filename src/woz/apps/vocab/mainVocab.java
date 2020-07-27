package woz.apps.vocab;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;

public class mainVocab extends FragmentActivity {  
    Integer nImport;
	public static vocabList vocab= new vocabList();
	public static final int NOFILTER=-1;
	public static final int NOTFOUND=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_vocab);
		nImport=vocab.initialize(this); System.out.println("imported: "+nImport);
		nImport=vocab.loadVocab(this); System.out.println("number loaded="+nImport);
		if (nImport==0) finish();
		FragmentManager fm=getSupportFragmentManager();
		Fragment fragment=fm.findFragmentById(R.id.main);
		if (fragment== null) {
		fragment=newVocab.newInstance();
		fm.beginTransaction().add(R.id.main, fragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.acbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			
			case R.id.progress:
				FragmentManager fm=getSupportFragmentManager();
				Fragment fragment=fm.findFragmentByTag("graph");
				if (fragment == null) {
					fragment=new vocabGraph();
					fm.beginTransaction().replace(R.id.main, fragment,"graph").addToBackStack(null).commit();
				}
				return true;
				
			case R.id.help:
				FragmentManager fm_help=getSupportFragmentManager();
				Fragment fragment_help=fm_help.findFragmentByTag("help");
				if (fragment_help == null) {
					fragment_help=new vocabHelp();
					fm_help.beginTransaction().replace(R.id.main, fragment_help,"help").addToBackStack(null).commit();
				}
				return true;
			}	
			return false;
		}
}
