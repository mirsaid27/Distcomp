package com.example.discussion.repository;

import com.example.discussion.model.Post;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface PostRepository extends CassandraRepository<Post, BigInteger> {
    @Query("SELECT * FROM tbl_post WHERE post_country = :post_country AND post_id = :id")
    Optional<Post> getById(@Param("post_country") String country, @Param("id") BigInteger id);

    @Query("DELETE FROM tbl_post WHERE post_country = :post_country AND post_id = :id")
    void removeById(@Param("post_country") String country, @Param("id") BigInteger id);

    @Query("INSERT INTO tbl_post (post_country, post_id, post_newsid, post_content) VALUES (:#{#post.post_country}, :#{#post.id}, :#{#post.newsId}, :#{#post.content})")
    void savePost(@Param("post")Post post);

    @Query("UPDATE tbl_post SET post_content = :#{#post.content} WHERE post_country = :#{#post.post_country} AND post_id = :#{#post.id} AND post_newsid = :#{#post.newsId}")
    void updatePost(@Param("post")Post post);

}