package com.codepath.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.MediaController;

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

        ImageView ivprofilePic = (ImageView) convertView.findViewById(R.id.IvProfilePic);
        TextView tvcaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivphoto = (ImageView) convertView.findViewById(R.id.Ivphoto);
        TextView tvCommentUsername = (TextView) convertView.findViewById(R.id.TvcommentUsername);
        TextView tvComment = (TextView) convertView.findViewById(R.id.Tvcomment);
        TextView tvusername = (TextView) convertView.findViewById(R.id.TvUsername);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.TvTimestamp);
        final VideoView mVideoView = (VideoView) convertView.findViewById(R.id.VvVideo);

        tvusername.setText(photo.username);
        tvTimestamp.setText(photo.Timestamp);

        if (photo.type.equals("image")) {
            tvcaption.setVisibility(View.VISIBLE);
            tvCommentUsername.setVisibility(View.VISIBLE);
            tvComment.setVisibility(View.VISIBLE);
            ivphoto.setVisibility(View.VISIBLE);
            mVideoView.setVisibility(View.INVISIBLE);
            tvcaption.setText(photo.caption);
            tvCommentUsername.setText(photo.comments[0][0]);
            tvComment.setText(photo.comments[0][1]);

            // clear Image View
            ivphoto.setImageResource(0);
            // Insert Image view using Picasso
            Picasso.with(getContext()).load(photo.url).into(ivphoto);
        } else if (photo.type.equals("video")) {
            mVideoView.setVisibility(View.VISIBLE);
            tvcaption.setVisibility(View.INVISIBLE);
            ivphoto.setVisibility(View.INVISIBLE);

            // clear Image View
            ivphoto.setImageResource(0);
            tvCommentUsername.setText(photo.comments[0][0]);
            tvComment.setText(photo.comments[0][1]);

            mVideoView.setVideoPath(photo.url);
            MediaController mediaController = new MediaController(getContext());
            mediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mediaController);
            mVideoView.requestFocus();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    mVideoView.start();
                }
            });

        }
        // clear Profile Pics
        ivprofilePic.setImageResource(0);
        // Insert Profile pics
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(1)
                .cornerRadiusDp(40)
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
