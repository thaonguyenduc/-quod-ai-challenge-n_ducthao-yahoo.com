package com.company.analyzer;

public interface Execute<Input> {
    Object apply(Input inputT) throws Exception;
}
