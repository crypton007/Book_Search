package com.example.aman0.booksearch10;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman0 on 23-06-2017.
 */

public class Bookadapter extends ArrayAdapter<Getdata>{

    List<Getdata> booklist = new ArrayList<>();

    public Bookadapter(Activity context, List<Getdata> books){
        super(context,0,books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem = convertView;
        if(listitem == null){
            listitem = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Getdata currentbook = getItem(position);

        TextView title = (TextView) listitem.findViewById(R.id.title);
        title.setText(currentbook.getTitle());

//        TextView subtitle = (TextView) listitem.findViewById(R.id.subtitle);
//        subtitle.setText(currentbook.getSubtitle());

        TextView author = (TextView) listitem.findViewById(R.id.authors);
        author.setText(currentbook.getAuthor());

        TextView publisher = (TextView) listitem.findViewById(R.id.publishers);
        publisher.setText(currentbook.getPublisher());

        ImageView images = (ImageView) listitem.findViewById(R.id.image);
        Picasso.with(getContext()).load(currentbook.getImageurl()).into(images);

        return listitem;
    }
}
