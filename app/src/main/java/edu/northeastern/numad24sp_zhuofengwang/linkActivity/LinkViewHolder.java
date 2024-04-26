package edu.northeastern.numad24sp_zhuofengwang.linkActivity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad24sp_zhuofengwang.R;


public class LinkViewHolder extends RecyclerView.ViewHolder {

    public TextView url;
    public TextView linkName;

    public LinkViewHolder(View itemView, final LinkClickListener listener) {
        super(itemView);
        this.url = itemView.findViewById(R.id.link);
        this.linkName = itemView.findViewById(R.id.linkName);

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlText = url.getText().toString();
                if (!urlText.startsWith("http://") && !urlText.startsWith("https://")) {
                    urlText = "http://" + urlText;
                }
                Uri webpage = Uri.parse(urlText);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                v.getContext().startActivity(intent);

                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onLinkClick(position);
                    }
                }
            }
        });
    }

}
