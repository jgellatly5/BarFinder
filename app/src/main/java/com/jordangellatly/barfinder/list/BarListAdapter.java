package com.jordangellatly.barfinder.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jordangellatly.barfinder.R;
import com.jordangellatly.barfinder.models.Business;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BarListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private BarListAdapterListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView barImage;
        public TextView barName;

        public ViewHolder(View itemView) {
            super(itemView);

            barImage = itemView.findViewById(R.id.bar_image);
            barName = itemView.findViewById(R.id.bar_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onBarSelected(businesses.get(getAdapterPosition()));
                }
            });
        }
    }

    private List<Business> businesses;

    public BarListAdapter(List<Business> businesses, BarListAdapterListener listener) {
        this.businesses = businesses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((parent.getContext()));
        View barRowView = inflater.inflate(R.layout.row_bar_location, parent, false);
        ViewHolder viewHolder = new ViewHolder(barRowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Business business = businesses.get(position);

        TextView name = holder.itemView.findViewById(R.id.bar_name);
        name.setText(business.getName());
        ImageView image = holder.itemView.findViewById(R.id.bar_image);
        Picasso.get().load(business.getImageUrl()).into(image);
    }

    @Override
    public int getItemCount() {
        return businesses.size();
    }

    public interface BarListAdapterListener {
        void onBarSelected(Business business);
    }
}
