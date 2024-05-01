package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.movie.MovieRating;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.movie.MovieState;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie extends BaseEntity {
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String directors;

    private String actors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @Temporal(TemporalType.DATE)
    private LocalDate releasedDate;

    private int duration;
    private String country; // USA, UK, France, ...
    private String language; // English, French, ...
    private String localizations; // Phụ đề Tiếng Việt, Lồng Tiếng Việt, ...

    @Enumerated(EnumType.STRING)
    private MovieRating rating;

    private String posterUrl;
    private String trailerUrl;

    @Convert(converter = MovieState.Converter.class)
    private MovieState state;

    @OneToMany(mappedBy = "movie")
    private List<Showtime> showtimes;
}
