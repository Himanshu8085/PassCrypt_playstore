package com.passcrypt.passwordmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TilesAdapter extends ArrayAdapter<Tileobj> {
    public TilesAdapter(@NonNull Context context, ArrayList<Tileobj> arrayList) {
        super(context, 0, arrayList);
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View convert = convertView; // this is view of one tile
        if (convert == null) {
            //Inflate view from that xml file
            convert = LayoutInflater.from(getContext()).inflate(R.layout.tiles, parent, false);
        }
        //Get Tileobj object at that position
        Tileobj tileobj = getItem(position);

        TextView t1 = convert.findViewById(R.id.textView2);
        t1.setText(tileobj.getText1());

        TextView t2 = convert.findViewById(R.id.textView);
        t2.setText(Character.toString(tileobj.getText1().toUpperCase(Locale.ROOT).charAt(0)));
        List<String> list = new ArrayList<>();
        list.add("#4285F4");
        list.add("#BB001B");
        list.add("#EA4335");
        list.add("#FBBC05");
        list.add("#34A853");
        Random rand = new Random();
        t2.setBackgroundTintList( ColorStateList.valueOf(Color.parseColor(list.get(rand.nextInt(list.size())))) );
        return convert;

    }
}
