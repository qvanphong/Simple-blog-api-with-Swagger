package tech.qvanphong.blog.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import tech.qvanphong.blog.model.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
}
