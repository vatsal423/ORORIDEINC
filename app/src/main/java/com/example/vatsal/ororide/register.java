package com.example.vatsal.ororide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends ActionBarActivity implements View.OnClickListener{

    EditText e1,e2,e3,e4;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findall();
        b1.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    private void findall()
    {
        e1 = (EditText)findViewById(R.id.edname);
        e2 = (EditText)findViewById(R.id.edemail);
        e3 = (EditText)findViewById(R.id.eduserreg);
        e4 = (EditText)findViewById(R.id.edpassreg);
        b1 = (Button)findViewById(R.id.btreg);

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

    @Override
    public void onClick(View v) {
        Mytask2 t2 = new Mytask2();
        t2.execute();
    }

    class Mytask2 extends AsyncTask<String,String,String>
    {
        ProgressDialog pd;
        String name,email,uname,pass;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd = new ProgressDialog(register.this);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.setTitle("Loading...");
            pd.setMessage("Please Wait...");
            pd.show();

            name = e1.getText().toString();
            email = e2.getText().toString();
            uname = e3.getText().toString();
            pass = e4.getText().toString();

        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if(pd!=null)
            {
                pd.dismiss();
            }

            int value = Integer.parseInt(s.trim());

            if(value==1)
            {
                Toast.makeText(register.this,"Registration Successful !!!!",Toast.LENGTH_LONG).show();

                Intent i = new Intent(register.this,MainActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(register.this,"Please try again... Registration Unsuccessful",Toast.LENGTH_LONG).show();

                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
            }
        }

        @Override
        protected String doInBackground(String... params)
        {
            RequestPackage rp = new RequestPackage();
            rp.setUri(Utility.serverurl);
            rp.setParam("type", "register");
            rp.setParam("name",name);
            rp.setParam("email",email);
            rp.setParam("uname",uname);
            rp.setParam("pass",pass);
            rp.setMethod("GET");
            String ans=HttpManager.getData(rp);
            return ans;
        }


    }
}
