package com.task.task.data.api.converter;


import com.task.task.data.api.models.response.MovieApiResponse;
import com.task.task.domain.model.MovieInfo;

import java.util.List;


public interface MovieAPIConverter {

    List<MovieInfo> convertToMovieInfo(List<MovieApiResponse> movieApiResponse);

}
