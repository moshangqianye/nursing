package com.jqsoft.nursing.utils3.service.impl;


import com.jqsoft.nursing.utils3.entity.CacheObject;
import com.jqsoft.nursing.utils3.service.CacheFullRemoveType;

/**
 * Remove type when cache is full.<br/>
 * when cache is full, compare enter time of object in cache, if time is smaller remove it first. also LIFO<br/>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-12-26
 */
public class RemoveTypeEnterTimeLast<T> implements CacheFullRemoveType<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(CacheObject<T> obj1, CacheObject<T> obj2) {
        return (obj2.getEnterTime() > obj1.getEnterTime()) ? 1
                : ((obj2.getEnterTime() == obj1.getEnterTime()) ? 0 : -1);
    }
}
