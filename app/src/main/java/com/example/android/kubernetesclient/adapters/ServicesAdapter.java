package com.example.android.kubernetesclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.android.kubernetesclient.R;
import com.example.android.kubernetesclient.models.Service;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;

public class ServicesAdapter extends BaseAdapter {

    private Context c;
    private List<Service> services;
    private static LayoutInflater inflater=null;

    public ServicesAdapter(Context c) {
        this.c = c;
        this.services = new ArrayList<>();
        this.inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ServicesAdapter(Context c, List<Service> services) {
        this(c);
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public int getCount() {
        return services.size();
    }

    public Object getItem(int position) {
        return services.get(position);
    }

    public long getItemId(int position) {
        return services.get(position).hashCode();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View itemView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            itemView = inflater.inflate(R.layout.gridlayout_item, null);
        } else {
            itemView = convertView;
        }

        holder.textView = itemView.findViewById(R.id.item_text);
        holder.imageView = itemView.findViewById(R.id.item_image);
        holder.statusImageView = itemView.findViewById(R.id.status_image);

        holder.textView.setText(services.get(position).getName());
        holder.statusImageView.setVisibility(View.GONE);
        holder.imageView.setImageResource(R.drawable.service);

        return itemView;
    }

}
