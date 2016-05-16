package com.illucit.encryptzip.engine;

import java.util.Objects;

/**
 *
 * @author Daniel Wieth
 */
public abstract class NavigatablePresenter {

    protected Runnable onOk;
    protected Runnable onCancel;

    public NavigatablePresenter() {
        onOk = () -> {
        };
        onCancel = () -> {
        };
    }

    public void setOnOk(Runnable onOk) {
        this.onOk = Objects.requireNonNull(onOk);
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = Objects.requireNonNull(onCancel);
    }

    public void ok() {
        onOk.run();
    }

    public void cancel() {
        onCancel.run();
    }
}
