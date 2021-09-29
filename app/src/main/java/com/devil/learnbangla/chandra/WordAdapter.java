package com.devil.learnbangla.chandra;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ArrayAdapter <Word> {
    private  int  mColorResourceId;

    public WordAdapter(@NonNull Context context, ArrayList<Word> adapter,int color) {
        super(context, 0, adapter);
        mColorResourceId = color;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list

        Word wordsobj = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView bangla = (TextView) listItemView.findViewById(R.id.bangla);
        // Get the version name from the current Word object and
        // set this text on the name TextView
        bangla.setText(wordsobj.getBangla());
        
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView english = (TextView) listItemView.findViewById(R.id.english);
        // Get the version number from the current Word object and
        // set this text on the number TextView
        english.setText(wordsobj.getEnglish());
        ImageView img = listItemView.findViewById(R.id.image);
        Log.v("Adapter","Inside");
        if(wordsobj.isImg()) {
            img.setImageResource(wordsobj.getImag());
            img.setVisibility(View.VISIBLE);
        }else {
            img.setVisibility(View.GONE);
        }

        View color = listItemView.findViewById(R.id.container);
        // Find the color that the resource ID maps to
//        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        color.setBackgroundResource(mColorResourceId);
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
