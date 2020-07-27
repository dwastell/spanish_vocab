package woz.apps.vocab;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.support.v4.widget.*;
import android.widget.*;
import com.jjoe64.graphview.*;
import java.io.*;
import java.net.*;
import com.jjoe64.graphview.series.*;

public class vocabHelp extends android.support.v4.app.Fragment {

private TextView mHelp;

@Override
    public void onCreate(Bundle State) {
	super.onCreate(State);
	}
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
	View v=inflater.inflate(R.layout.frag3_vocab,parent,false);
	mHelp=(TextView) v.findViewById(R.id.help);
	return v;
  }
}

