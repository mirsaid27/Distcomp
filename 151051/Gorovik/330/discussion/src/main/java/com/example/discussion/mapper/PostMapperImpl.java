package com.example.discussion.mapper;

import com.example.discussion.model.Post;
import com.example.discussion.request.PostRequestTo;
import com.example.discussion.response.PostResponseTo;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostMapperImpl implements PostMapper{
    private static final SecureRandom random;

    static {
        SecureRandom randomInstance;
        try {
            randomInstance = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException ex) {
            randomInstance = new SecureRandom();
        }
        random = randomInstance;
    }
    private static Long getTimeBasedId(){
        return (((System.currentTimeMillis() << 16) | (random.nextLong() & 0xFFFF)));
    }
    @Override
    public PostRequestTo postToRequestTo(Post post) {
        return new PostRequestTo(
                post.getId().longValue(),
                post.getNewsId().longValue(),
                post.getContent()
        );
    }

    @Override
    public List<PostRequestTo> postToRequestTo(Iterable<Post> posts) {
        return StreamSupport.stream(posts.spliterator(), false)
                .map(this::postToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Post dtoToEntity(PostRequestTo postRequestTo, String country) {
        if(postRequestTo.id() == null)
            return new Post(
                    null,
                    new BigInteger(postRequestTo.newsId().toString()),
                    country,
                    postRequestTo.content()
            );
        else
            return new Post(
                    new BigInteger(postRequestTo.id().toString()),
                    new BigInteger(postRequestTo.newsId().toString()),
                    country,
                    postRequestTo.content()
            );
    }

    @Override
    public PostResponseTo postToResponseTo(Post post) {
        return new PostResponseTo(
                post.getId().longValue(),
                post.getNewsId().longValue(),
                post.getContent());
    }

    @Override
    public List<PostResponseTo> postToResponseTo(Iterable<Post> posts) {
        return StreamSupport.stream(posts.spliterator(), false)
                .map(this::postToResponseTo)
                .collect(Collectors.toList());
    }

}