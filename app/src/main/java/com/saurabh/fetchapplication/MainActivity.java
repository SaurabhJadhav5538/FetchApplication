package com.saurabh.fetchapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.saurabh.fetchapplication.Activity.ListDetailsActivity;
import com.saurabh.fetchapplication.Adapter.MainAdapter;
import com.saurabh.fetchapplication.Model.User;
import com.saurabh.fetchapplication.Service.FetchData;
import com.saurabh.fetchapplication.Service.UtilityService;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private List<Integer> listId;
    private Map<Integer, List<User>> userDataList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);
        setContentView(R.layout.activity_main);
        if (!checkNetworkConnection()){
            UtilityService.toastMessage(this, "Please Check Network Connection");
            return;
        }
        recyclerView = findViewById(R.id.recyclerView);
        FetchData.fetchUserData(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateData(List<User> list) {
        if(list.isEmpty() || list == null) {
            UtilityService.toastMessage(this, "You have no items to view");
            return;
        }
        filterData(list);
        setLayout();
    }

    private void setLayout() {
        mainAdapter = new MainAdapter(this, listId);
        recyclerView.setAdapter(mainAdapter);
        UtilityService.runLayoutAnimation(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void filterData(List<User> list) {
        list.removeIf(user -> user.getUser_Name().equals("null") || user.getUser_Name().isEmpty());
        list.sort(Comparator.comparingInt(User::getUser_Id));
        Map<Integer, List<User>> groupedUsers = list.stream()
                .collect(Collectors.groupingBy(User::getUser_ItemId));

        userDataList = new HashMap<>(groupedUsers);
        listId = new ArrayList<>(groupedUsers.keySet());
    }


    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        List<User> list = userDataList.get(listId.get(position));
        Intent intent = new Intent(this, ListDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("List", (Serializable) list);
        intent.putExtra("List",bundle);
        startActivity(intent);
    }
}