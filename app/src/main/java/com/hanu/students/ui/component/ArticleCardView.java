package com.hanu.students.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentArticleCardBinding;
import com.hanu.students.model.Article;

public class ArticleCardView extends ConstraintLayout {

    private ComponentArticleCardBinding binding;

    public ArticleCardView(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.component_article_card, this, true);
    }

    public ArticleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.component_article_card, this, true);
    }

    public ArticleCardView setArticle(Article article) {
        binding.setArticle(article);
        return this;
    }
}
