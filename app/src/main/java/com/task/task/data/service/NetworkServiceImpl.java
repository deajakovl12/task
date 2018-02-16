package com.task.task.data.service;


import com.task.task.data.api.models.response.MovieApiResponse;

import java.util.List;

import io.reactivex.Single;

public final class NetworkServiceImpl implements NetworkService {

    private final TemplateAPI templateAPI;

    public NetworkServiceImpl(final TemplateAPI templateAPI) {
        this.templateAPI = templateAPI;
    }


    @Override
    public Single<List<MovieApiResponse>> movieInfo() {
        return Single.defer(() -> templateAPI.movieInfo());
    }


}
