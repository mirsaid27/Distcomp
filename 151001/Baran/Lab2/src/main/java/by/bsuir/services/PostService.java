package by.bsuir.services;

import by.bsuir.dto.PostRequestTo;
import by.bsuir.dto.PostResponseTo;
import by.bsuir.entities.Post;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.PostListMapper;
import by.bsuir.mapper.PostMapper;
import by.bsuir.repository.PostRepository;
import by.bsuir.repository.IssueRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class PostService {
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostRepository postDao;
    @Autowired
    PostListMapper postListMapper;
    @Autowired
    IssueRepository issueRepository;

    public PostResponseTo getPostById(@Min(0) Long id) throws NotFoundException {
        Optional<Post> post = postDao.findById(id);
        return post.map(value -> postMapper.postToPostResponse(value)).orElseThrow(() -> new NotFoundException("Post not found!", 40004L));
    }

    public List<PostResponseTo> getPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder!=null && sortOrder.equals("asc")){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else{
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Post> posts = postDao.findAll(pageable);
        return postListMapper.toPostResponseList(posts.toList());
    }

    public PostResponseTo savePost(@Valid PostRequestTo post) {
        Post postToSave = postMapper.postRequestToPost(post);
        if (post.getIssueId()!=null) {
            postToSave.setIssue(issueRepository.findById(post.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found!", 40004L)));
        }
        return postMapper.postToPostResponse(postDao.save(postToSave));
    }

    public void deletePost(@Min(0) Long id) throws DeleteException {
        if (!postDao.existsById(id)) {
            throw new DeleteException("Post not found!", 40004L);
        } else {
            postDao.deleteById(id);
        }
    }

    public PostResponseTo updatePost(@Valid PostRequestTo post) throws UpdateException {
        Post postToUpdate = postMapper.postRequestToPost(post);
        if (!postDao.existsById(post.getId())) {
            throw new UpdateException("Post not found!", 40004L);
        } else {
            if (post.getIssueId()!=null) {
                postToUpdate.setIssue(issueRepository.findById(post.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found!", 40004L)));
            }
            return postMapper.postToPostResponse(postDao.save(postToUpdate));
        }
    }

    public List<PostResponseTo> getPostByIssueId(@Min(0) Long issueId) throws NotFoundException {
        List<Post> post = postDao.findPostsByIssue_Id(issueId);
        if (post.isEmpty()){
            throw new NotFoundException("Post not found!", 40004L);
        }
        return postListMapper.toPostResponseList(post);
    }
}
