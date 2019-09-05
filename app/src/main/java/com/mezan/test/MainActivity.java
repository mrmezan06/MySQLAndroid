package com.mezan.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText name,mob,email;
    Button btn;
    TextView Res;
    String res="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        mob = findViewById(R.id.mob);
        email = findViewById(R.id.email);
        btn = findViewById(R.id.btn);
        Res = findViewById(R.id.result);

    }

    //http://localhost/test/insert.php?name=Mezan&mob=01786995549&email=mrmezan06@gmail.com

    public void data_submit(View view) {
        Res.setText("Please Wait...");
        String Name = "";
        String Mob = "";
        String Email = "";
        try {
            Name = name.getText().toString();
            Mob = mob.getText().toString();
            Email = email.getText().toString();
            //Res.setText(Email);

        }catch (Exception e){
            e.printStackTrace();
        }
        if(Name.endsWith("") || Mob.equals("") || Email.equals("")){
            Toast.makeText(getApplicationContext(),"Please Fillup All The Field.",Toast.LENGTH_LONG).show();
        }else {
            new MyTask().execute("https://carrent01.000webhostapp.com/insert.php?name=" + Name + "&mob=" + Mob + "&email=" + Email);
        }
    }

    public void data_show(View view) {
        Res.setText("Please Wait...");
        new ShowClass().execute("https://carrent01.000webhostapp.com/");
    }

    private class ShowClass extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String result) {
            StringBuilder Finalresult=new StringBuilder();
            try{
                JSONArray js=new JSONArray(result);
                for(int i=0;i<js.length();i++){
                    JSONObject obj = js.getJSONObject(i);
                    Finalresult.append(obj.get("Name") +"\n"+obj.get("Mobile")+"\n"+obj.get("Email")+"\n\n");
                }

                Res.setText(Finalresult.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e){
                // Toast.makeText(getApplicationContext(),"Please Input any value",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result=new StringBuilder();
            HttpURLConnection urlConnection=null;
            try{
                URL url=new URL(strings[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                String line;
                while((line=reader.readLine())!=null){
                    result.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }
    }
    private class MyTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String result) {
            Res.setText(result);
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result=new StringBuilder();
            HttpURLConnection urlConnection=null;
            try{
                URL url=new URL(strings[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                String line;
                while((line=reader.readLine())!=null){
                    result.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }
    }

}




