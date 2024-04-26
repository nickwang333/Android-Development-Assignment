package edu.northeastern.numad24sp_zhuofengwang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.northeastern.numad24sp_zhuofengwang.linkActivity.LinkActivity;

public class AddLinkActivity extends AppCompatActivity {

    private Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlink);

        submitButton = findViewById(R.id.linkSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText t1 = (EditText) findViewById(R.id.editNameText);
                EditText tUrl = (EditText) findViewById(R.id.editUrl);

                Intent intent = new Intent(getBaseContext(), LinkActivity.class);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    // Use the value
                    ArrayList<String> name = getIntent().getStringArrayListExtra("name");
                    ArrayList<String> urls = getIntent().getStringArrayListExtra("urls");

                    intent.putStringArrayListExtra("name", name);
                    intent.putStringArrayListExtra("urls", urls);
                }
                intent.putExtra("Mode", "Add");
                intent.putExtra("linkName", t1.getText().toString());
                intent.putExtra("linkUrl", tUrl.getText().toString());
                startActivity(intent);
            }
        });
    }
}
