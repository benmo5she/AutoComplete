package com.bottomline.auto.complete.service;

import java.util.List;


/*
    Provides a base for services to be included in the application and the required criteria to implement in its implementation.
 */
public abstract class BaseService {
    abstract List<String> getAll();
}
