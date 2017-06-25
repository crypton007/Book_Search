package com.example.aman0.booksearch10;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by aman0 on 23-06-2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Getdata>> {
    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();

    private String murl;
    public BookLoader(Context context,String url) {
        super(context);
        murl= url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Getdata> loadInBackground() {
        if (murl==null) {
            return null;
        }

        List<Getdata> result = QueryUtils.fetchbooks(murl);
        return result;
    }
}
