package by.bsuir.dc.impl.author;
import by.bsuir.dc.api.RestService;
import by.bsuir.dc.impl.author.model.Author;
import by.bsuir.dc.impl.author.model.AuthorRequest;
import by.bsuir.dc.impl.author.model.AuthorResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
@AllArgsConstructor
public class AuthorService implements RestService<AuthorRequest, AuthorResponse> {
    private AuthorMapper authorMapper;
    private AuthorRepository authorCrudRepository;

    @Override
    public List<AuthorResponse> findAll() {
        Iterable<Author> authors = authorCrudRepository.getAll();
        return authorMapper.toAuthorResponseDto(authors);
    }
    @Override
    public AuthorResponse findById(long id) {
        Author author = authorCrudRepository.getBy(id).orElseThrow();
        return authorMapper.toAuthorResponseDto(author);
    }
    @Override
    public AuthorResponse create(AuthorRequest request) {
        return authorCrudRepository
                .create(authorMapper.toAuthorDto(request))
                .map(authorMapper::toAuthorResponseDto)
                .orElseThrow();
    }
    @Override
    public AuthorResponse update(AuthorRequest request) {
        return authorCrudRepository
                .update(authorMapper.toAuthorDto(request))
                .map(authorMapper::toAuthorResponseDto)
                .orElseThrow();
    }
    @Override
    public boolean removeById(long id) {
        authorCrudRepository.deleteById(id);
        return true;
    }
}
