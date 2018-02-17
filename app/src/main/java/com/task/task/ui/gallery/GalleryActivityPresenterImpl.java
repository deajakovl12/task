package com.task.task.ui.gallery;

import android.app.Activity;

import com.task.task.R;
import com.task.task.domain.usecase.LocalImagesUseCase;
import com.task.task.manager.StringManager;
import com.task.task.ui.base.presenter.BasePresenter;

import java.util.List;

import javax.inject.Named;

import io.reactivex.Scheduler;
import timber.log.Timber;

import static com.task.task.injection.module.ThreadingModule.OBSERVE_SCHEDULER;
import static com.task.task.injection.module.ThreadingModule.SUBSCRIBE_SCHEDULER;


public class GalleryActivityPresenterImpl extends BasePresenter implements GalleryActivityPresenter {

    private GalleryView view;

    private final Scheduler subscribeScheduler;

    private final Scheduler observeScheduler;

    private final StringManager stringManager;

    private final LocalImagesUseCase localImagesUseCase;

    public GalleryActivityPresenterImpl(@Named(SUBSCRIBE_SCHEDULER) final Scheduler subscribeScheduler,
                                        @Named(OBSERVE_SCHEDULER) final Scheduler observeScheduler,
                                        final StringManager stringManager,
                                        final LocalImagesUseCase localImagesUseCase) {
        this.subscribeScheduler = subscribeScheduler;
        this.observeScheduler = observeScheduler;
        this.stringManager = stringManager;
        this.localImagesUseCase = localImagesUseCase;

    }

    @Override
    public void setView(final GalleryView view) {
        this.view = view;
    }

    @Override
    public void getImagesPath(Activity activity) {
        if (view != null) {
            addDisposable(localImagesUseCase.getImagesPath(activity)
                    .subscribeOn(subscribeScheduler)
                    .observeOn(observeScheduler)
                    .subscribe(this::onGetLocalImagesSuccess, this::onGetLocalImagesFailure));
        }
    }

    private void onGetLocalImagesFailure(final Throwable throwable) {
        Timber.e(stringManager.getString(R.string.local_images_failure), throwable);
    }

    private void onGetLocalImagesSuccess(final List<String> imageList) {
        if (view != null) {
            view.showImagesPath(imageList);
        }
    }
}


