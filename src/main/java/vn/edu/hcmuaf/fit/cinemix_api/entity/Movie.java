package vn.edu.hcmuaf.fit.cinemix_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.cinemix_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.cinemix_api.core.shared.enums.MovieState;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie extends BaseEntity {
    private String title;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
    )
    private List<Genre> genres;

    private String synopsis;
    private String description;
    private String director;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> actors;

    private Integer duration;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    private String posterUrl;
    private String trailerUrl;

    @Enumerated(EnumType.STRING)
    private MovieState state;
}
