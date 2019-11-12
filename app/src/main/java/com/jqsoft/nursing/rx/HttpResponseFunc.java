package com.jqsoft.nursing.rx;

import rx.Observable;
import rx.functions.Func1;

public class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override public Observable<T> call(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }