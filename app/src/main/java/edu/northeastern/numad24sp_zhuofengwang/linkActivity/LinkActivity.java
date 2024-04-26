package edu.northeastern.numad24sp_zhuofengwang.linkActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import edu.northeastern.numad24sp_zhuofengwang.AddLinkActivity;
import edu.northeastern.numad24sp_zhuofengwang.EditLinkActivity;
import edu.northeastern.numad24sp_zhuofengwang.R;


public class LinkActivity extends AppCompatActivity {

    private ArrayList<Link> linkList = new ArrayList<>();

    private RecyclerView recyclerView;
    private LinkAdaptor rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private FloatingActionButton addButton;
    private TextView UrlText;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    public ArrayList<String> splitListIntoNameArray(){
        ArrayList<String> ret = new ArrayList<>();
        for(int i=0; i<linkList.size(); i++){
            ret.add(linkList.get(i).getLinkName());
        }
        return ret;
    }

    public ArrayList<String> splitListIntoUrlArray(){
        ArrayList<String> ret = new ArrayList<>();
        for(int i=0; i<linkList.size(); i++){
            ret.add(linkList.get(i).getUrl());
        }
        return ret;
    }

    public void combintTwoArray(ArrayList<String> name, ArrayList<String> url){
        for(int i=0; i<name.size(); i++){
            Link l = new Link(url.get(i), name.get(i));
            linkList.add(l);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        init(savedInstanceState);

        addButton = findViewById(R.id.addButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.getString("Mode").equals("Add")) {
                Log.v("Swipe", "Add");
                String linkName = extras.getString("linkName");
                String linkUrl = extras.getString("linkUrl");

                ArrayList<String> name = getIntent().getStringArrayListExtra("name");
                ArrayList<String> urls = getIntent().getStringArrayListExtra("urls");
                if (!name.isEmpty() && !urls.isEmpty()) {
                    combintTwoArray(name, urls);
                }

                createRecyclerView();

                addItem(0, linkName, linkUrl);
            }
            else if(extras.getString("Mode").equals("Edit")){
                Log.v("Swipe", "Edit");

                ArrayList<String> name = getIntent().getStringArrayListExtra("name");
                ArrayList<String> urls = getIntent().getStringArrayListExtra("urls");
                if (!name.isEmpty() && !urls.isEmpty()) {
                    combintTwoArray(name, urls);
                }

                createRecyclerView();

            }

            // Use the value
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddLinkActivity.class);
                intent.putStringArrayListExtra("name", splitListIntoNameArray());
                intent.putStringArrayListExtra("urls", splitListIntoUrlArray());

                startActivity(intent);
            }
        });

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == 4) {
                    Toast.makeText(LinkActivity.this, "Delete an item", Toast.LENGTH_SHORT).show();
                    int position = viewHolder.getLayoutPosition();
                    linkList.remove(position);

                    rviewAdapter.notifyItemRemoved(position);
                }
                else if(direction == 8){
                    int position = viewHolder.getLayoutPosition();
                    Intent intent = new Intent(getBaseContext(), EditLinkActivity.class);
                    intent.putStringArrayListExtra("name", splitListIntoNameArray());
                    intent.putStringArrayListExtra("urls", splitListIntoUrlArray());
                    intent.putExtra("posi", position);
                    intent.putExtra("selecName", linkList.get(position).getLinkName());
                    intent.putExtra("selecUrl", linkList.get(position).getUrl());

                    startActivity(intent);

                }

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private void addItem(int position, String linkName, String linkUrl) {
        linkList.add(position, new Link(linkUrl, linkName));

        Snackbar snackbar = Snackbar.make(this.recyclerView, "Added Successfully", Snackbar.LENGTH_LONG);
        snackbar.setAction("Open the URL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Snackbar action click
                String urlText = linkUrl;
                if (!urlText.startsWith("http://") && !urlText.startsWith("https://")) {
                    urlText = "http://" + urlText;
                }
                Uri webpage = Uri.parse(urlText);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                v.getContext().startActivity(intent);
            }
        });
        snackbar.show();

        rviewAdapter.notifyItemInserted(position);
    }

    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {

        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {

            if (linkList == null || linkList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);
                Log.v("Size of array", String.valueOf(size));
                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String urlName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String linkName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");

                    Link itemCard = new Link(urlName, linkName);

                    linkList.add(itemCard);
                }
            }
        }

    }

    private void createRecyclerView() {


        rLayoutManger = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new LinkAdaptor(linkList);
        LinkClickListener itemClickListener = new LinkClickListener() {
            @Override
            public void onLinkClick(int position) {
                //attributions bond to the item has been changed
                linkList.get(position).onLinkClick(position);

                rviewAdapter.notifyItemChanged(position);
            }
        };
        rviewAdapter.setOnItemClickListener(itemClickListener);

        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }

    /*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = linkList == null ? 0 : linkList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", linkList.get(i).getUrl());
            outState.putString(KEY_OF_INSTANCE + i + "1", linkList.get(i).getLinkName());
        }
        super.onSaveInstanceState(outState);

    }
    */
}
