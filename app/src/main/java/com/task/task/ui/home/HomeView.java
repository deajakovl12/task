package com.task.task.ui.home;

import com.task.task.domain.model.MovieInfo;

import java.util.List;


public interface HomeView {

    void showData(List<MovieInfo> movieInfo);
}
