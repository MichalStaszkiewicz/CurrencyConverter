package com.repository;

public class Repository {
    private static Repository instance;

    public static Repository getInstance() {
        if(instance == null){
            instance = new Repository();
        }
        return instance;

    }
}
