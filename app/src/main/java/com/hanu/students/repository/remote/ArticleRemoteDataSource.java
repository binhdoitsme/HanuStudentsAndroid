package com.hanu.students.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.model.Article;

import java.util.LinkedList;
import java.util.List;

import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRemoteDataSource extends RemoteDataSource<Article> {

    public ArticleRemoteDataSource() {
        super();
    }

    @Override
    public LiveData<List<Article>> getList(Object... args) {
        var data = new MutableLiveData<List<Article>>(new LinkedList<>());
        super.getConnector().getArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                data.getValue().addAll(response.body());
                data.setValue(data.getValue());
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }

    @Override
    public LiveData<Article> getSingle(Object... args) {
        throw new UnsupportedOperationException();
    }
}
