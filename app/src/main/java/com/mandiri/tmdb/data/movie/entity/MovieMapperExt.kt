package com.mandiri.tmdb.data.movie.entity

import com.mandiri.tmdb.data.common.BASE_IMAGE_URL
import com.mandiri.tmdb.data.common.BASE_VIDEO_THUMBNAIL_URL
import com.mandiri.tmdb.data.common.PairResponse
import com.mandiri.tmdb.domain.movie.entity.Movie
import com.mandiri.tmdb.domain.movie.entity.MovieDetail
import com.mandiri.tmdb.domain.movie.entity.Review
import com.mandiri.tmdb.domain.movie.entity.Video

fun MovieResponse.toMovies() =
    Movie(
        id = id,
        title = title,
        originalTitle = originalTitle,
        posterPath = String.format(BASE_IMAGE_URL, posterPath)
    )

fun MovieResponse.toMovieDetail() =
    MovieDetail(
        movie = toMovies(),
        originalLanguage = originalLanguage,
        backdropPath = String.format(BASE_IMAGE_URL, backdropPath),
        genres = "${genres?.joinToString()}",
        tagline = tagline,
        overview = overview,
        status = status,
        revenue = revenue,
        runtime = runtime,
        year = releaseDate.split("-")[0]
    )

fun List<PairResponse>.joinToString() =
    joinToString { it.name }

fun List<MovieResponse>.toMovies() =
    map(MovieResponse::toMovies)

fun VideoResponse.toVideo() =
    Video(
        id = id,
        name = name,
        thumbnail = String.format(BASE_VIDEO_THUMBNAIL_URL, key)
    )

fun List<VideoResponse>.toVideos() =
    map(VideoResponse::toVideo)

fun ReviewResponse.toReview() =
    Review(
        id = id,
        author = author,
        name = authorDetails.name,
        avatarPath = String.format(BASE_IMAGE_URL, authorDetails.avatarPath),
        rating = authorDetails.rating,
        content = content
    )

fun List<ReviewResponse>.toReviews() =
    map(ReviewResponse::toReview)

fun List<PairResponse>.toPairs() =
    map { it.id to it.name }

