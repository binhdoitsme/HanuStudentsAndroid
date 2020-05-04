package com.hanu.students.model.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanu.students.base.JSONStringMapper;
import com.hanu.students.model.Article;

import lombok.SneakyThrows;

public class ArticleMapper extends JSONStringMapper<Article> {

    public ArticleMapper() {
        super(ArticleMapper::fromJSONString, ArticleMapper::toJSONString);
    }

    private static String toJSONString(Article article) {
        return "{}";
    }

    @SneakyThrows
    private static Article fromJSONString(String jsonString) {
        return new ObjectMapper().readValue(jsonString, Article.class);
    }
}
