package com.company.core;

import com.company.model.GitEvent;

import java.util.List;

public interface Execute<T extends Number>{
    T calculate(List<GitEvent> gitEvents) throws Exception;
}
