package com.niangsa.dream_shop.service;

import java.util.List;

public interface IAbstractService <T>{
    T save(T t);
    T getById(Long id);
    void delete(Long id);
    T update(Long id,T t);
    List<T> getAll();
}
