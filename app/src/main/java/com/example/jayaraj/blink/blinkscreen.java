package com.example.jayaraj.blink;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.UUID;


public class blinkscreen extends ActionBarActivity {

    Button b1, b2, b3;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter mybluetooth = null;
    BluetoothSocket btsocket = null;
    private boolean isbtconnected = false;
    static final UUID myuuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    TextView tt;
    String name=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinkscreen);
        Intent newi = getIntent();
        address = newi.getStringExtra(devicelist.location);
        b1 = (Button) findViewById(R.id.button3);
        b2 = (Button) findViewById(R.id.button4);
        b3 = (Button) findViewById(R.id.button5);
        new connectbt().execute();
        name=newi.getStringExtra(devicelist.name);
       // tt=(TextView)findViewById(R.id.textView2);
       // tt.setText("Device connected to : "+name);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnonled();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnoffled();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });


    }

    private void disconnect() {
        if (btsocket != null) {
            try {
                btsocket.close();
            } catch (IOException e) {
                msg("error");
            }

        }
        finish();
    }

    private void turnonled() {
        if (btsocket != null) {
            try {
                btsocket.getOutputStream().write("TO".toString().getBytes());
            } catch (IOException e) {
                msg("error");
            }
        }
    }

    private void turnoffled() {
        if (btsocket != null) {
            try {
                btsocket.getOutputStream().write("TF".toString().getBytes());
            } catch (IOException e) {
                msg("error");
            }
        }

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blinkscreen, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class connectbt extends AsyncTask<Void,Void,Void> {
        private boolean connectsuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(blinkscreen.this, "connecting......", "please wait...");

        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btsocket == null || !isbtconnected) {
                    mybluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositiv = mybluetooth.getRemoteDevice(address);
                    btsocket = dispositiv.createInsecureRfcommSocketToServiceRecord(myuuid);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btsocket.connect();
                }
            } catch (IOException e) {
                connectsuccess = false;
            }
            return null;
        }


        @Override
          protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!connectsuccess) {
                msg("connection failed...is it a spp bluetooth? tryagain");
                finish();

            } else {
                msg("connected");
                isbtconnected = true;

            }
            progress.dismiss();

        }
    }
}






