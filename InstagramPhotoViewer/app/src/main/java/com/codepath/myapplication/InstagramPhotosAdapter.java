package com.codepath.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by abgandhi on 2/21/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhotp> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhotp> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhotp photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvcaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivphoto = (ImageView) convertView.findViewById(R.id.Ivphoto);
        ImageView ivprofilePic = (ImageView) convertView.findViewById(R.id.IvProfilePic);
        TextView tvComments = (TextView) convertView.findViewById(R.id.Tvcomments);
        TextView tvusername = (TextView) convertView.findViewById(R.id.TvUsername);

        tvcaption.setText(photo.caption);
       // tvComments.setText(photo.comments);
        tvusername.setText(photo.username);

        // clear Image View
        ivphoto.setImageResource(0);
        // Insert Image view using Picasso
        Picasso.with(getContext()).load(photo.url).into(ivphoto);
       // Picasso.with(getContext()).load(photo.profilePicUrl).into(ivprofilePic);

        // Insert Profile pics
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Log.i("DEBUG","Profile Pics url" + photo.profilePicUrl);
        Picasso.with(getContext())
                .load(photo.profilePicUrl)
                .transform(transformation)
                .into(ivprofilePic);

        return convertView;
    }
}
