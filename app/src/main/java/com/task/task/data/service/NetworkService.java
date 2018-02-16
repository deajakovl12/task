package com.task.task.data.service;


import com.task.task.data.api.models.response.MovieApiResponse;

import java.util.List;

import io.reactivex.Single;

public interface NetworkService {

    Single<List<MovieApiResponse>> movieInfo();
}
