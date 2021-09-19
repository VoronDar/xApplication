package com.astery.xapplication.ui;

import com.astery.xapplication.repository.listeners.JobListener;

public abstract class ResultListener implements JobListener {
    private JobListener listener;
    public abstract void success();

    @Override
    public void done(boolean success) {
        listener.done(success);
        if (success)
            success();
    }

    public ResultListener() {
    }

    public ResultListener setListener(JobListener listener) {
        this.listener = listener;
        return this;
    }
}
