package com.task.task.domain.usecase;


import com.task.task.data.api.models.response.MovieApiResponse;
import com.task.task.data.service.NetworkService;
import com.task.task.data.storage.TemplatePreferences;

import java.util.List;

import io.reactivex.Single;

public class MovieUseCaseImpl implements MovieUseCase {

    private final NetworkService networkService;

    private final TemplatePreferences preferences;


    public MovieUseCaseImpl(NetworkService networkService, TemplatePreferences preferences) {
        this.networkService = networkService;
        this.preferences = preferences;
    }

    @Override
    public Single<List<MovieApiResponse>> getMovieInfo() {
        return Single
                .defer(() -> networkService.movieInfo());
    }

}
