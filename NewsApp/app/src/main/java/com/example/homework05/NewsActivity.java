package com.example.homework05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/*
Assignment : # Homework 05
Group: Groups1 41
NAME: AKHIL CHUNDARATHIL, RAVI THEJA GOALLA
*/

public class NewsActivity extends AppCompatActivity {
    Source source;
    ArrayList<News> articles = new ArrayList<>();
    ProgressBar pb;
    ListView newslistview;
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newslistview = findViewById(R.id.newslistview);
        pb = findViewById(R.id.progressBar_news);

        if(getIntent() != null && getIntent().getExtras() != null){
            source = getIntent().getParcelableExtra(MainActivity.selectedsource);
            setTitle(source.name);
            if(!isConnected()){
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
            else {
                pb.setVisibility(View.VISIBLE);
                new GetNews().execute("https://newsapi.org/v2/top-headlines?sources=" + source.id + "&apiKey=a717dffdd6074b95be6a2c9e7fc2d841");
            }
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();

        if(network != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        }
        return false;

    }

    private class GetNews extends AsyncTask<String, Void, ArrayList<News>>{

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            HttpURLConnection connection = null;

            URL url = null;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                    if(json.equals("")){
                        return null;
                    }

                    JSONObject root = new JSONObject(json);
                    JSONArray articlesJSONArray = root.getJSONArray("articles");

                    for(int i=0;i < articlesJSONArray.length(); i++){

                        JSONObject articleJSON = articlesJSONArray.getJSONObject(i);

                        articles.add(new News(articleJSON.getString("author"), articleJSON.getString("title"), articleJSON.getString("url"), articleJSON.getString("urlToImage"), articleJSON.getString("publishedAt")));

                    }

                    return articles;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {

            pb.setVisibility(View.INVISIBLE);

            NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, R.layout.news_item, news);
            newslistview.setAdapter(newsAdapter);

            newslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News newsitem = (News) parent.getItemAtPosition(position);

                    if(!isConnected()){
                        Toast.makeText(NewsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                    else if(newsitem.url.trim().length() == 0){
                        Toast.makeText(NewsActivity.this, "No URL Found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        myWebView = new WebView(NewsActivity.this);
                        myWebView.setWebViewClient(new WebViewClient());
                        myWebView.loadUrl(newsitem.url);
                        setContentView(myWebView);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if(myWebView != null && myWebView.canGoBack()){
            myWebView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
