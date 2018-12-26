package com.example.android.kubernetesclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.kubernetesclient.R;
import com.example.android.kubernetesclient.models.Pod;

import java.util.ArrayList;
import java.util.List;

public class PodsAdapter extends BaseAdapter {
    private Context c;
    private List<Pod> pods;
    private static LayoutInflater inflater=null;

    public class Holder {
        TextView textView;
        ImageView imageView;
        ImageView statusImageView;
    }

    public PodsAdapter(Context c) {
        this.c = c;
        this.pods = new ArrayList<>();
        this.inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public PodsAdapter(Context c, List<Pod> pods) {
        this(c);
        this.pods = pods;
    }

    public List<Pod> getPods() {
        return pods;
    }

    public void setPods(List<Pod> pods) {
        this.pods = pods;
    }

    public int getCount() {
        return pods.size();
    }

    public Object getItem(int position) {
        return pods.get(position);
    }

    public long getItemId(int position) {
        return pods.get(position).hashCode();
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
        holder.statusImageView = itemView.findViewById(R.id.pod_status_image);

        holder.textView.setText(pods.get(position).getName());

        int podImageResourceId;
        if (pods.get(position).isRunning()) {
            podImageResourceId = R.drawable.success;
        } else {
            podImageResourceId = R.drawable.error;
        }
        holder.statusImageView.setImageResource(podImageResourceId);

        return itemView;
    }
}
