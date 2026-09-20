package com.example.homework2.repository;

import com.example.homework2.entity.Comment;
import com.example.homework2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
    select c.post.id, count(c.id)
    from Comment c
    where c.post.id in :postIds
      and c.deleted = false
    group by c.post.id
    """)
    List<Object[]> countByPostIds(List<Long> postIds);
}
