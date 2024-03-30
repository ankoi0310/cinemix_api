package vn.edu.hcmuaf.fit.cinemix_api.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.cinemix_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.cinemix_api.dto.genre.*;
import vn.edu.hcmuaf.fit.cinemix_api.entity.Genre;
import vn.edu.hcmuaf.fit.cinemix_api.mapper.GenreMapper;
import vn.edu.hcmuaf.fit.cinemix_api.repository.genre.GenreRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreDTO> searchGenres(GenreSearch genreSearch) {
        return genreMapper.toDTOs(genreRepository.search(genreSearch));
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return genreMapper.toDTOs(genreRepository.findAll());
    }

    @Override
    public GenreDTO getGenreById(Long id) throws BaseException {
        return genreMapper.toDTO(genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Genre not found")));
    }

    @Override
    public GenreDTO createGenre(GenreCreate genreCreate) throws BaseException {
        Genre genre = genreRepository.findByName(genreCreate.getName()).orElse(null);

        if (genre != null) {
            throw new BadRequestException("Genre already exists");
        }

        Genre newGenre = Genre.builder()
                .name(genreCreate.getName())
                .description(genreCreate.getDescription())
                .build();

        genreRepository.save(newGenre);

        return genreMapper.toDTO(newGenre);
    }

    @Override
    public GenreDTO updateGenre(GenreUpdate genreUpdate) throws BaseException {
        Genre genre = genreRepository.findById(genreUpdate.getId()).orElseThrow(() -> new NotFoundException("Genre not found"));

        // Check if genre name is already taken
        Genre genreCheck = genreRepository.findByName(genreUpdate.getName()).orElse(null);

        if (genreCheck != null && !genreCheck.getId().equals(genreUpdate.getId())) {
            throw new BadRequestException("Genre name already exists");
        }

        genre.setName(genreUpdate.getName());
        genre.setDescription(genreUpdate.getDescription());

        genreRepository.save(genre);

        return genreMapper.toDTO(genre);
    }
}
