package com.task.task.injection;


import com.task.task.application.TaskApplication;
import com.task.task.injection.component.ActivityComponent;
import com.task.task.injection.component.ApplicationComponent;
import com.task.task.ui.base.activities.BaseActivity;

public final class ComponentFactory {

    private ComponentFactory() { }

    public static ApplicationComponent createApplicationComponent(final TaskApplication application) {
        return ApplicationComponent.Initializer.init(application);
    }

    public static ActivityComponent createActivityComponent(final TaskApplication application, final BaseActivity activity) {
        return ActivityComponent.Initializer.init(application.getApplicationComponent(), activity);
    }
}
