package com.example.vatsal.ororide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener  {

    EditText e1,e2;
    Button b1;
    TextView link;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int userid = sp.getInt("userid", 0);
        if(userid>0) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
           finish();
        }

        findall();
        b1.setOnClickListener(this);
        link.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,register.class);
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void findall()
    {
        e1 = (EditText)findViewById(R.id.eduser);
        e2 = (EditText)findViewById(R.id.edpass);
        b1 = (Button)findViewById(R.id.btlog);
        link = (TextView)findViewById(R.id.txtlink);
    }

    @Override
    public void onClick(View v) {
        MyTask1 t = new MyTask1();
        t.execute();
    }

    class MyTask1 extends AsyncTask<String,String,String>
    {
        String uname,pass;
        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.setTitle("Loading...");
            pd.setMessage("Please Wait...");
            pd.show();

            uname = e1.getText().toString();
            pass= e2.getText().toString();

        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if(pd!=null)
            {
                pd.dismiss();
            }

            //Log.e("abc",s);
            int value = Integer.parseInt(s.trim());

            if(value>0)
            {
                SharedPreferences sp1 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                sp1.edit().putInt("userid", value).apply();

                Intent i = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected String doInBackground(String... params)
        {
            RequestPackage rp = new RequestPackage();
            rp.setUri(Utility.serverurl);
            rp.setParam("type","login");
            rp.setParam("uname",uname);
            rp.setParam("pass",pass);
            rp.setMethod("GET");
            String ans=HttpManager.getData(rp);
            return ans;
        }

    }


}
