package ru.javawebinar.topjava.util;

public  class Pair<A, B>  {
    private A argA;
    private B argB;

    public Pair() {
    }

    public Pair(A arg1, B arg2) {
        this.argA = arg1;
        this.argB = arg2;
    }

    public A getArgA() {
        return argA;
    }

    public void setArgA(A argA) {
        this.argA = argA;
    }

    public B getArgB() {
        return argB;
    }

    public void setArgB(B argB) {
        this.argB = argB;
    }
}