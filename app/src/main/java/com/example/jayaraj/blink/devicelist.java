package com.example.jayaraj.blink;

import android.app.DownloadManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.OutputStream;
import java.util.Set;
import java.util.ArrayList;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;


public class devicelist extends ActionBarActivity {
    public static String name="device name";
    Button btnpaired;
    ListView devicelist;
    public static String location="device address";
    private BluetoothAdapter myblue=BluetoothAdapter.getDefaultAdapter();
    private Set<BluetoothDevice> pairedDevices;
    private void pairedDevicelist()
    {
        pairedDevices=myblue.getBondedDevices();
        ArrayList list=new ArrayList();
        if(pairedDevices.size()>0)
        {
            for(BluetoothDevice bt:pairedDevices) {
                list.add(bt.getName() + "\n" +bt.getAddress());

            }
        }
        else {
            Toast.makeText(getApplicationContext(), "no paired bluetooth device found", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(mylistclicklistner);

    }


    private OnItemClickListener mylistclicklistner=new OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> av,View v,int arg2,long arg3)
        {
        String info=((TextView)v).getText().toString();
        String address=info.substring(info.length() - 17);
        Intent im=new Intent(com.example.jayaraj.blink.devicelist.this,blinkscreen.class);
         im.putExtra(name,info);
           im.putExtra(location,address);
            startActivity(im);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicelist);

        btnpaired=(Button)findViewById(R.id.button2);
        devicelist=(ListView)findViewById(R.id.listView);

        btnpaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicelist();
          Toast.makeText(getApplicationContext(),"loading device ...",Toast.LENGTH_SHORT);
      } });
   }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_devicelist, menu);
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
}
