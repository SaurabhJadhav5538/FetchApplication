package com.saurabh.fetchapplication.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.saurabh.fetchapplication.Adapter.ListDetailsAdapter;
import com.saurabh.fetchapplication.Adapter.MainAdapter;
import com.saurabh.fetchapplication.Model.User;
import com.saurabh.fetchapplication.R;
import com.saurabh.fetchapplication.Service.UtilityService;

import java.util.List;

public class ListDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ListDetailsAdapter detailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        setContentView(R.layout.activity_list_details);
        Bundle bundle = getIntent().getBundleExtra("List");
        List<User> list = (List<User>) bundle.getSerializable("List");
        recyclerView = findViewById(R.id.list_details_recyclerview);
        detailsAdapter = new ListDetailsAdapter(list);
        recyclerView.setAdapter(detailsAdapter);
        UtilityService.runLayoutAnimation(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}