package com.hanu.students.viewmodel;

import com.hanu.students.base.ListViewModel;
import com.hanu.students.model.Article;
import com.hanu.students.repository.ArticleRepository;

public class DashboardViewModel extends ListViewModel<Article> {

//    private MutableLiveData<List<Article>> articles;
//
//    public DashboardViewModel() {
//        articles = new MutableLiveData<>();
//        articles.setValue(new LinkedList<>());
//    }
//
//    public LiveData<List<Article>> getArticles() {
//        return articles;
//    }
//
//    public DashboardViewModel addArticles(Article article) {
//        articles.getValue().add(article);
//        articles.setValue(articles.getValue());
//        return this;
//    }
//
//    public DashboardViewModel addArticles(Collection<Article> articles) {
//        this.articles.getValue().addAll(articles);
//        this.articles.setValue(this.articles.getValue());
//        return this;
//    }

    public DashboardViewModel() {
        super();
        setRepository(new ArticleRepository());
    }
}