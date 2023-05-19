package com.saurabh.fetchapplication.Service;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.saurabh.fetchapplication.MainActivity;
import com.saurabh.fetchapplication.R;

public class UtilityService {

    public static void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public static void toastMessage(MainActivity mainActivity, String message) {
        Toast.makeText(mainActivity, message,Toast.LENGTH_SHORT).show();
    }

}
