package com.niangsa.dream_shop.service;
import  java.util.List;
public interface AbstractService<T> {
    T getById(Long id);
    List<T> getAll();
    void add(T t);
    void delete(Long id);
}
