package com.example.jayaraj.blink;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class meclabs extends ActionBarActivity {
    private BluetoothAdapter mblue=BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meclabs);
        if(mblue==null)
        {
            Toast.makeText(getApplicationContext(),"bluetoothdevice not available",Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            if (!mblue.isEnabled()) {
                Intent turnon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnon, 1);
            }
}}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meclabs, menu);
        return true;
    }

    public void newscreen(View view)
    {
 if (mblue.isEnabled()) {

                Intent intent = new Intent(this,devicelist.class);
                startActivity(intent);
            }


/*
         else
          {
                Intent intent = new Intent(this,devicelist.class);
                startActivity(intent);
          }
*/

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
