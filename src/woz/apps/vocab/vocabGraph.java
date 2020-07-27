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

public class vocabGraph extends android.support.v4.app.Fragment {

private GraphView gr;

@Override
    public void onCreate(Bundle State) {
	super.onCreate(State);
	}
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

	View v=inflater.inflate(R.layout.frag2_vocab, parent, false);
	LinearLayout mygr=(LinearLayout) v.findViewById(R.id.graph);
	gr=new GraphView(getActivity()); // originally passed this
	DataPoint d[]=new DataPoint[100];
	for (int i=0;i<100;i++) {

	d[i]=new DataPoint(i,(float)i/30.0+2+Math.sin(6.284*(float)i/20.0));
	}
	LineGraphSeries<DataPoint> series=
	    new LineGraphSeries<DataPoint>(d);
    gr.addSeries(series);
	gr.getViewport().setYAxisBoundsManual(true);
	gr.getViewport().setMinY(0);
	gr.getViewport().setMaxY(10);
	gr.getViewport().setScalable(true);
	gr.setLayoutParams(new LinearLayout.LayoutParams
	  (LinearLayout.LayoutParams.MATCH_PARENT,
	 LinearLayout.LayoutParams.MATCH_PARENT));
    mygr.addView(gr);
	return v;
  }
}

