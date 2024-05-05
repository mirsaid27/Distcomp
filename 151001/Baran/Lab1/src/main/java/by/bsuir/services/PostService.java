package by.bsuir.services;

import by.bsuir.dao.PostDao;
import by.bsuir.dto.PostRequestTo;
import by.bsuir.dto.PostResponseTo;
import by.bsuir.entities.Post;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.PostListMapper;
import by.bsuir.mapper.PostMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class PostService {
    @Autowired
    PostMapper PostMapper;
    @Autowired
    PostDao PostDao;
    @Autowired
    PostListMapper PostListMapper;

    public PostResponseTo getPostById(@Min(0) Long id) throws NotFoundException {
        Optional<Post> Post = PostDao.findById(id);
        return Post.map(value -> PostMapper.PostToPostResponse(value)).orElseThrow(() -> new NotFoundException("Post not found!", 40004L));
    }

    public List<PostResponseTo> getPosts() {
        return PostListMapper.toPostResponseList(PostDao.findAll());
    }

    public PostResponseTo savePost(@Valid PostRequestTo Post) {
        Post PostToSave = PostMapper.PostRequestToPost(Post);
        return PostMapper.PostToPostResponse(PostDao.save(PostToSave));
    }

    public void deletePost(@Min(0) Long id) throws DeleteException {
        PostDao.delete(id);
    }

    public PostResponseTo updatePost(@Valid PostRequestTo Post) throws UpdateException {
        Post PostToUpdate = PostMapper.PostRequestToPost(Post);
        return PostMapper.PostToPostResponse(PostDao.update(PostToUpdate));
    }

    public PostResponseTo getPostByIssueId(@Min(0) Long issueId) throws NotFoundException {
        Optional<Post> Post = PostDao.getPostByIssueId(issueId);
        return Post.map(value -> PostMapper.PostToPostResponse(value)).orElseThrow(() -> new NotFoundException("Post not found!", 40004L));
    }
}
