package com.example.firebaseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArrayAdapter extends android.widget.ArrayAdapter<User> {
    public ArrayAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        User user_object = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);

        TextView name = convertView.findViewById(R.id.item_name);
        //TextView description = convertView.findViewById(R.id.description_description);
        ImageView image = convertView.findViewById(R.id.item_image);

        name.setText(user_object.name);
        //description.setText(user_object.status);
        Picasso.get().load(user_object.image).fit().centerCrop().placeholder(R.drawable.default_user).error(R.drawable.default_user).into(image);

        return convertView;
     }
}
