package com.example.aman0.booksearch10;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.aman0.booksearch10.R.id.keyword;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Getdata>>{
    private Bookadapter adapter;
    String JSON = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int ID = 1;
    EditText key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listview);
        adapter = new Bookadapter(this,new ArrayList<Getdata>());

        listView.setAdapter(adapter);

        key = (EditText) findViewById(keyword);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Getdata currentbook = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentbook.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        final TextView search_by = (TextView) findViewById(R.id.search_by);

        Button search_by_author = (Button) findViewById(R.id.search_by_author);
        search_by_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_by.setText("Authors");
                JSON="https://www.googleapis.com/books/v1/volumes?q=inauthor:";
            }
        });
        Button search_by_publisher = (Button) findViewById(R.id.search_by_publisher);
        search_by_publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_by.setText("Publisher");
                JSON="https://www.googleapis.com/books/v1/volumes?q=inpublisher:";
            }
        });
        Button search_by_isbn = (Button) findViewById(R.id.search_by_isbn);
        search_by_isbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_by.setText("ISBN");
                JSON="https://www.googleapis.com/books/v1/volumes?q=isbn:";
            }
        });
        Button search_by_name  = (Button) findViewById(R.id.search_by_name);
        search_by_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_by.setText("Name");
                JSON="https://www.googleapis.com/books/v1/volumes?q=";
            }
        });
        ImageView find = (ImageView) findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSON=JSON+key.getText().toString()+"&maxResults=40";
                Log.v("onclick","json value"+JSON);
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.restartLoader(ID,null,MainActivity.this);
            }
        });



//        BookAsyncTask task = new BookAsyncTask();
//        task.execute(JSON);
    }

//    private String url(String search){
//        JSON ="https://www.googleapis.com/books/v1/volumes?q="+search+"&maxResults=20";
//        Log.v("url","JSON value "+JSON);
//        return JSON;
//    }

    @Override
    public Loader<List<Getdata>> onCreateLoader(int id, Bundle args) {
        Log.v("oncreateloader","Json"+JSON);
        return new BookLoader(this,JSON);
    }

    @Override
    public void onLoadFinished(Loader<List<Getdata>> loader, List<Getdata> data) {
        adapter.clear();
        JSON="https://www.googleapis.com/books/v1/volumes?q=";
        if(data!=null && !data.isEmpty())
            adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Getdata>> loader) {
        adapter.clear();
    }
    //    private class BookAsyncTask extends AsyncTask<String, Void, List<Getdata>> {
//
//        /**
//         * This method runs on a background thread and performs the network request.
//         * We should not update the UI from a background thread, so we return a list of
//         * {@link Getdata}s as the result.
//         */
//        @Override
//        protected List<Getdata> doInBackground(String... urls) {
//            // Don't perform the request if there are no URLs, or the first URL is null
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//
//            List<Getdata> result = QueryUtils.fetchEarthquakeData(urls[0]);
//            return result;
//        }
//
//        /**
//         * This method runs on the main UI thread after the background work has been
//         * completed. This method receives as input, the return value from the doInBackground()
//         * method. First we clear out the adapter, to get rid of earthquake data from a previous
//         * query to USGS. Then we update the adapter with the new list of earthquakes,
//         * which will trigger the ListView to re-populate its list items.
//         */
//        @Override
//        protected void onPostExecute(List<Getdata> data) {
//            // Clear the adapter of previous earthquake data
//            adapter.clear();
//
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if (data != null && !data.isEmpty()) {
//                adapter.addAll(data);
//            }
//        }
//    }
}
