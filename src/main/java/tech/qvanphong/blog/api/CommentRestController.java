package tech.qvanphong.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.qvanphong.blog.model.Comment;
import tech.qvanphong.blog.repository.CommentRepository;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentRestController {
    private final CommentRepository repository;

    @Autowired
    public CommentRestController(CommentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/fetchCommentsByName/{name}")
    public Page<Comment> fetchCommentsByName(@PathVariable("name") String name, Pageable pageable) {
        return repository.findAllByName(name, pageable);
    }

    @GetMapping("/fetchCommentsByPost/{postId}")
    public Page<Comment> fetchCommentsByPost(@PathVariable("postId") Integer postId, Pageable pageable) {
        return repository.findAllByPostId(postId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newComment(@Validated @RequestBody Comment comment) {
        comment.setDate(new Date().toString());
        repository.save(comment);
    }


}
