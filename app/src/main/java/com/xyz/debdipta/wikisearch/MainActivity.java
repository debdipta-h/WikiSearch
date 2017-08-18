package com.xyz.debdipta.wikisearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE="com.xyz.debdipta.wikisearch.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void findTerm(View view){
        Intent intent= new Intent(this,SearchDisplayActivity.class);
        EditText searchText=(EditText) findViewById(R.id.editText);
        intent.putExtra(EXTRA_MESSAGE,searchText.getText().toString());
        startActivity(intent);

    }
}
