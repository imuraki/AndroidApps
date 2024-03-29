package com.example.homework05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class NewsAdapter extends ArrayAdapter {

    public NewsAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = (News)getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
            viewHolder = new ViewHolder((TextView) convertView.findViewById(R.id.imagetitleview).findViewById(R.id.title),(TextView) convertView.findViewById(R.id.authordateview).findViewById(R.id.author),
                    (TextView) convertView.findViewById(R.id.authordateview).findViewById(R.id.date),(ImageView) convertView.findViewById(R.id.imagetitleview).findViewById(R.id.newsimage));
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(news.title == "null" ? "No title Found" : news.title);
        viewHolder.author.setText(news.author == "null" ? "No author Found" : news.author);
        viewHolder.date.setText(news.publishedat);
        if(news.urlToImage.trim().length() == 0){
            viewHolder.newsimage.setImageResource(R.drawable.no_photo);
        }
        else{
            Picasso.get().load(news.urlToImage).error(R.drawable.no_photo).into(viewHolder.newsimage);
        }


        return convertView;
    }

    private static class ViewHolder{
        TextView title,author,date;
        ImageView newsimage;

        public ViewHolder(TextView title, TextView author, TextView date, ImageView newsimage) {
            this.title = title;
            this.author = author;
            this.date = date;
            this.newsimage = newsimage;
        }
    }
}
