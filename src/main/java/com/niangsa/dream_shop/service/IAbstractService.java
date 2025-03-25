package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.entities.User;

import java.util.List;

public interface IAbstractService <T>{
    T getById(Long id);
    void delete(Long id);
    T update(Long id,T t);
    List<T> getAll();
}
