package com.task.task.domain.usecase;


import com.task.task.data.api.models.response.MovieApiResponse;

import java.util.List;

import io.reactivex.Single;

public interface MovieUseCase {

    Single<List<MovieApiResponse>> getMovieInfo();

}
