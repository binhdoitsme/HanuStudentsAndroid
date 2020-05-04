package com.hanu.students.repository;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.base.Repository;
import com.hanu.students.model.Article;
import com.hanu.students.repository.remote.ArticleRemoteDataSource;

import java.util.List;

public class ArticleRepository implements Repository<Article> {
    private RemoteDataSource<Article> remoteDataSource;

    public ArticleRepository() {
        remoteDataSource = new ArticleRemoteDataSource();
    }

    public LiveData<List<Article>> findAll(Object... args) {
        return remoteDataSource.getList();
    }

    @Override
    public LiveData<Article> findOne(Object... args) {
        throw new UnsupportedOperationException();
    }
}
