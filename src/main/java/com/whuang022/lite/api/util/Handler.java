package com.whuang022.lite.api.util;

public abstract class Handler {

    private boolean mock = false;

    public Handler() {
    }

    public Handler(boolean mock) {
        this.mock = mock;
    }

    public Object process(Object... arguments) {
        if (isMock()) {
            return processMock(arguments);
        } else return processReal(arguments);
    }

    public abstract Object processMock(Object... arguments);

    public abstract Object processReal(Object... arguments);

    public boolean isMock() {
        return mock;
    }

    public void setMock(boolean mock) {
        this.mock = mock;
    }
}
