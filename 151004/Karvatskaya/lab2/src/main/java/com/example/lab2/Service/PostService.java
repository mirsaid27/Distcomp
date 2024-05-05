package com.example.lab2.Service;

import com.example.lab2.DTO.PostRequestTo;
import com.example.lab2.DTO.PostResponseTo;
import com.example.lab2.Exception.NotFoundException;
import com.example.lab2.Mapper.PostListMapper;
import com.example.lab2.Mapper.PostMapper;
import com.example.lab2.Model.Post;
import com.example.lab2.Repository.IssueRepository;
import com.example.lab2.Repository.PostRepository;
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

@Service
@Validated
public class PostService {
    @Autowired
    //PostDao postDao;
    PostRepository postRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostListMapper postListMapper;

    public PostResponseTo create(@Valid PostRequestTo postRequestTo){
        Post post = postMapper.postRequestToPost(postRequestTo);
        if(postRequestTo.getIssueId() != 0){
            post.setIssue(issueRepository.findById(postRequestTo.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found", 404)));
        }
        return postMapper.postToPostResponse(postRepository.save(post));
    }

    public List<PostResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction){
        Pageable p;
        if(direction != null && direction.equals("asc"))
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).ascending());
        else
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).descending());

        Page<Post> res = postRepository.findAll(p);
        return postListMapper.toPostResponseList(res.toList());
    }

    public PostResponseTo read(@Min(0) int id) throws NotFoundException {
        if(postRepository.existsById(id)){
            PostResponseTo postResponseTo = postMapper.postToPostResponse(postRepository.getReferenceById(id));
            return postResponseTo;
        }
        else
            throw new NotFoundException("Post not found", 404);
    }
    public PostResponseTo update(@Valid PostRequestTo postRequestTo, @Min(0) int id) throws NotFoundException {
        if(postRepository.existsById(id)){
            Post post = postMapper.postRequestToPost(postRequestTo);
            post.setId(id);
            if(postRequestTo.getIssueId() != 0){
                post.setIssue(issueRepository.findById(postRequestTo.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found", 404)));
            }
            return postMapper.postToPostResponse(postRepository.save(post));
        }
        else
            throw new NotFoundException("Post not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException {
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("Post not found", 404);
    }

}
