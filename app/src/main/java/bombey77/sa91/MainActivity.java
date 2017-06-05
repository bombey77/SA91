package bombey77.sa91;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    MyTask myTask;

    private final static String LOG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        myTask = (MyTask) getLastCustomNonConfigurationInstance();

        if (myTask == null) {
            myTask = new MyTask();
            myTask.execute();
            Log.d(LOG, "Create MainActivirty " + hashCode() + " myTask " + myTask.hashCode());
        }
        myTask.link(this);

    }

    public Object onRetainCustomNonConfigurationInstance() {
        myTask.unLink();
        return myTask;
    }

    public void onClick(View view) {
        myTask.execute();
    }

    static class MyTask extends AsyncTask <Void, Integer, Integer> {

        MainActivity activity;

        void link(MainActivity act) {
            activity = act;
        }

        void unLink () {
            activity = null;
        }


        @Override
        protected Integer doInBackground(Void... params) {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
                Log.d(LOG, "Create MainActivirty " + activity.hashCode() + " " + i +  " " + " myTask " + hashCode());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            activity.textView.setText("i = " + values[0]);
        }

    }
}
