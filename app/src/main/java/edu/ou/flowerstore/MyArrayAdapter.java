package edu.ou.flowerstore;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Item> {
    Activity context;
    int IdLayout;
    ArrayList<Item> myList;

    public MyArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    // Tao constructor
    public MyArrayAdapter(Activity context, int idLayout, ArrayList<Item> myList) {
        super(context, idLayout, myList);
        this.context = context;
        IdLayout = idLayout;
        this.myList = myList;
    }
    // Goi ham getView de Adapter lay va hien thi du lieu
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Tao de chua Layout
        LayoutInflater myflactor = context.getLayoutInflater();
        // Dat IdLayout len de de tao View
        convertView = myflactor.inflate(IdLayout, null);
        //Lay 1 phan tu trong mang du lieu
        Item myitem = myList.get(position);
        //Khai bao, anh xa id va hien thi anh, ten, gia, rate cua san pham
        ImageView img_item = convertView.findViewById(R.id.img_item);
        img_item.setImageResource(myitem.getImage());
        TextView txt_tensp = convertView.findViewById(R.id.txt_tensp);
        txt_tensp.setText((myitem.getName()));
        TextView txt_giasp = convertView.findViewById(R.id.txt_giasp);
        txt_giasp.setText(String.valueOf(myitem.getPrice()));
        TextView txt_danhgia =convertView.findViewById(R.id.txt_danhgia);
        txt_danhgia.setText(String.valueOf(myitem.getRate()));
        return convertView;
    }
}
