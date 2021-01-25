package tech.qvanphong.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import tech.qvanphong.blog.model.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {
    Page<Comment> findAllByName(String name, Pageable pageable);
    Page<Comment> findAllByPostId(int postId, Pageable pageable);
}
