package com.example.android.kubernetesclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.android.kubernetesclient.R;
import com.example.android.kubernetesclient.models.Node;
import com.example.android.kubernetesclient.models.Service;

import java.util.ArrayList;
import java.util.List;

public class NodesAdapter extends BaseAdapter {

    private Context c;
    private List<Node> nodes;
    private static LayoutInflater inflater=null;

    public NodesAdapter(Context c) {
        this.c = c;
        this.nodes = new ArrayList<>();
        this.inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public NodesAdapter(Context c, List<Node> nodes) {
        this(c);
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public int getCount() {
        return nodes.size();
    }

    public Object getItem(int position) {
        return nodes.get(position);
    }

    public long getItemId(int position) {
        return nodes.get(position).hashCode();
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

        holder.textView.setText(nodes.get(position).getName());
        holder.statusImageView.setVisibility(View.GONE);
        holder.imageView.setImageResource(R.drawable.node);

        return itemView;
    }

}
