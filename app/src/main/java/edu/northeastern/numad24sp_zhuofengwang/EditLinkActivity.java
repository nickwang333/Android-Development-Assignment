package edu.northeastern.numad24sp_zhuofengwang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.northeastern.numad24sp_zhuofengwang.linkActivity.LinkActivity;

public class EditLinkActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText tName, tUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlink);

        submitButton = findViewById(R.id.linkSubmitButton2);
        tName = findViewById(R.id.editNameText2);
        tName.setText(getIntent().getExtras().getString("selecName"));
        tUrl = findViewById(R.id.editUrl2);
        tUrl.setText(getIntent().getExtras().getString("selecUrl"));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), LinkActivity.class);
                Bundle extras = getIntent().getExtras();
                int posi = getIntent().getExtras().getInt("posi");
                if (extras != null) {
                    // Use the value
                    ArrayList<String> name = getIntent().getStringArrayListExtra("name");
                    ArrayList<String> urls = getIntent().getStringArrayListExtra("urls");
                    name.set(posi, tName.getText().toString());
                    urls.set(posi, tUrl.getText().toString());

                    intent.putStringArrayListExtra("name", name);
                    intent.putStringArrayListExtra("urls", urls);
                }
                intent.putExtra("Mode", "Edit");
                startActivity(intent);
            }
        });
    }
}
