package com.example.android.kubernetesclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.kubernetesclient.PodActivity;
import com.example.android.kubernetesclient.R;
import com.example.android.kubernetesclient.models.Pod;

import java.util.List;

public class PodsAdapter extends RecyclerView.Adapter<PodsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageView elementImageView;
        public ImageView statusImageView;
        public Context c;
        public Pod pod;

        public ViewHolder(View itemView, Context c) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.item_name_textview);
            elementImageView = itemView.findViewById(R.id.item_image);
            statusImageView = itemView.findViewById(R.id.status_image);
            this.c = c;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PodActivity.class);
            intent.putExtra("service", pod);
            view.getContext().startActivity(intent);
        }
    }

    // Store a member variable for the pods
    private List<Pod> pods;

    public PodsAdapter(List<Pod> pods) {
        this.pods = pods;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public PodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.gridlayout_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, context);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull PodsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Pod pod = pods.get(position);


        // Set item views based on your views and data model
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(pod.getName());

        ImageView elementImageView = viewHolder.elementImageView;
        elementImageView.setImageResource(R.drawable.pod);

        ImageView statusImageView = viewHolder.statusImageView;
        int podImageResourceId;
        if (pods.get(position).isRunning()) {
            podImageResourceId = R.drawable.success;
        } else {
            podImageResourceId = R.drawable.error;
        }
        statusImageView.setImageResource(podImageResourceId);

        viewHolder.pod = pods.get(position);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return pods.size();
    }

    public void setPods(List<Pod> pods) {
        this.pods = pods;
    }

    public long getItemId(int position) {
        return pods.get(position).hashCode();
    }

}
