package com.hanu.students.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hanu.students.R;
import com.hanu.students.model.Article;
import com.hanu.students.ui.component.ArticleCardView;
import com.hanu.students.viewmodel.DashboardViewModel;

import java.util.logging.Logger;

import lombok.var;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public DashboardFragment() {
        dashboardViewModel = new DashboardViewModel();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // register data observer
        registerLiveDataListener();
        return root;
    }

    private void registerLiveDataListener() {
        dashboardViewModel.getData().observe(getViewLifecycleOwner(), articles -> {
            ScrollView wrapper = (ScrollView)getView();
            LinearLayout layout = wrapper.findViewById(R.id.f_dashboard_content);
            if (articles.size() > 0) {
                layout.removeAllViews();
                var context = layout.getContext();
                for (Article article : articles) {
                    ArticleCardView view = new ArticleCardView(context).setArticle(article);
                    layout.addView(view);
                }
            }
        });
    }

}
