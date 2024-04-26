package edu.northeastern.numad24sp_zhuofengwang.linkActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numad24sp_zhuofengwang.R;

public class LinkAdaptor extends RecyclerView.Adapter<LinkViewHolder> {

    private final ArrayList<Link> links;
    private LinkClickListener listener;

    public LinkAdaptor(ArrayList<Link> links) {
        this.links = links;
    }


    public void setOnItemClickListener(LinkClickListener listener) {
        this.listener = listener;
    }

    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false);
        return new LinkViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(LinkViewHolder holder, int position) {
        Link currentItem = links.get(position);

        holder.url.setText(currentItem.getUrl());
        holder.linkName.setText(currentItem.getLinkName());
    }

    @Override
    public int getItemCount() {
        return links.size();
    }
}
