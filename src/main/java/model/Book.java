package model;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.util.List;

public class Book {

    long id;
    public String name;
    public String isbn;
    public String lastName;
    public Integer pages;
    public boolean hot;
    public String language;
    public LocalDate publishDate;
    public FilterCondition filter;
    public List<FilterCondition> filters;
    public List<String> tags;
    public JsonNode extra;
    public boolean deleted;
}
