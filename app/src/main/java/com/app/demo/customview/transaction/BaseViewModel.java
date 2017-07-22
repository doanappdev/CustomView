package com.app.demo.customview.transaction;


import java.io.Serializable;

public abstract class BaseViewModel implements Serializable {
    public abstract int getUniqueId();

    public abstract boolean isValid();
}
