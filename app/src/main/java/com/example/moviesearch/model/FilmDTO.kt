package com.example.moviesearch.model

data class FilmDTO(
    val genres: List<GenresDTO?>,
    val title: String?,
    val id: Long?,
    val release_date: String?,
    val overview: String?,
    val poster_path: String?,
    val adult: Boolean,
    val production_countries:List<CountriesDTO?>
) {
    data class GenresDTO(
        val id: Long?,
        val name: String?
    )

    data class CountriesDTO(
        val iso_3166_1: String?,
        val name: String?
    )
}