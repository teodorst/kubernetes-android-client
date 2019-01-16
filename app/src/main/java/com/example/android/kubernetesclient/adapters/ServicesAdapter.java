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

import com.example.android.kubernetesclient.R;
import com.example.android.kubernetesclient.ServiceActivity;
import com.example.android.kubernetesclient.models.Service;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageView elementImageView;
        public ImageView statusImageView;
        public Context c;
        public Service service;

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
            Intent intent = new Intent(view.getContext(), ServiceActivity.class);
            intent.putExtra("service", service);
            view.getContext().startActivity(intent);
        }
    }

    // Store a member variable for the services
    private List<Service> services;

    public ServicesAdapter(List<Service> services) {
        this.services = services;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Service service = services.get(position);


        // Set item views based on your views and data model
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(service.getName());

        ImageView elementImageView = viewHolder.elementImageView;
        elementImageView.setImageResource(R.drawable.service);

        ImageView statusImageView = viewHolder.statusImageView;
        statusImageView.setVisibility(View.GONE);

        viewHolder.service = services.get(position);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return services.size();
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public long getItemId(int position) {
        return services.get(position).hashCode();
    }

}
