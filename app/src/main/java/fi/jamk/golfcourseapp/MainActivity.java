package fi.jamk.golfcourseapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public List <GolfData> golfcourses;
    public RecyclerView rview;
    public RecyclerView.Adapter rviewadapter;
    public RecyclerView.LayoutManager rviewlayoutmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FetchDataTask fdtask = new FetchDataTask();
        fdtask.execute("http://ptm.fi/jamk/android/golfcourses/golf_courses.json");
    }


    protected class FetchDataTask extends AsyncTask<String, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(String... urls){
            HttpURLConnection urlConnection = null;
            JSONObject jsonobj = null;
            try{
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                jsonobj = new JSONObject(stringBuilder.toString());
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            } finally{
                if(urlConnection != null) urlConnection.disconnect();
            }
            return jsonobj;
        }


        protected void onPostExecute(JSONObject jsonobj){
            try{
                JSONArray courses = jsonobj.getJSONArray("kentat");
                JSONObject course;

                final GolfData golfplaces = new GolfData();
                for(int i = 0;i < courses.length();i++){
                    course = courses.getJSONObject(i);
                    golfplaces.addGolfplace(new GolfData(
                            course.getString("Kentta"),
                            course.getString("Osoite"),
                            course.getString("Puhelin"),
                            course.getString("Sahkoposti"),
                            course.getString("Kuva")
                    ));
                }

                golfcourses = golfplaces.getGolfplaces();
                rview = (RecyclerView) findViewById(R.id.recviewgolf);
                rviewlayoutmanager = new LinearLayoutManager(getApplicationContext());
                rviewadapter = new GolfAdapter(golfcourses);
                rview.setLayoutManager(rviewlayoutmanager);
                rview.setAdapter(rviewadapter);

            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}