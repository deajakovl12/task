package com.task.task.ui.base.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.task.task.application.TaskApplication;
import com.task.task.injection.ComponentFactory;
import com.task.task.injection.component.ActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TaskApplication taskApplication = (TaskApplication) getApplication();
        initActivityComponent(taskApplication);
        inject(activityComponent);
    }

    public final ActivityComponent getActivityComponent(final TaskApplication taskApplication) {
        if (activityComponent == null) {
            initActivityComponent(taskApplication);
        }
        return activityComponent;
    }

    private void initActivityComponent(final TaskApplication taskApplication) {
        activityComponent = ComponentFactory.createActivityComponent(taskApplication, this);
    }

    protected abstract void inject(ActivityComponent activityComponent);
}
