package com.travelsafe.particle.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent startServiceIntent = new Intent(getApplicationContext(), MyService.class);
        startService(startServiceIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Intent intent = new Intent(this, SettingsActivity.class);
            //startActivity(intent);
            StringBuilder logs = LogUtils.readLogs();
            try {
                createFileOnDevice(logs);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static BufferedWriter out;
    private void createFileOnDevice(StringBuilder logs) throws IOException {
                /*
                 * Function to initially create the log file and it also writes the time of creation to file.
                 */
        File Root = Environment.getExternalStorageDirectory();
        if(Root.canWrite()){
            File  LogFile = new File(Root, "Test App Log.txt");
            FileWriter LogWriter = new FileWriter(LogFile, false);
            out = new BufferedWriter(LogWriter);
            out.write(logs.toString());
            out.close();

        }
    }
    public void printLog(String msg){
        Log.d("TEST", msg);
    }
    @Override
    protected void onStop() {
        printLog("on Stop activity");
        StringBuilder logs = LogUtils.readLogs();
        try {
            createFileOnDevice(logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        StringBuilder logs = LogUtils.readLogs();
        try {
            createFileOnDevice(logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printLog("on Destroy activity");
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        printLog("on Low Memory activity");
        super.onLowMemory();
    }

    @Override
    protected void onPause() {
        printLog("on Pause activity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        printLog("on Resume activity");
        super.onResume();
    }
}
