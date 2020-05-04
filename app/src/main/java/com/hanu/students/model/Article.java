package com.hanu.students.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    private final String title, content;
    private final Timestamp lastUpdate;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Article(@JsonProperty("title") String title,
                   @JsonProperty("content") String content,
                   @JsonProperty("lastUpdate") Timestamp lastUpdate) {
        this.title = title;
        this.content = content;
        this.lastUpdate = lastUpdate;
    }
}
