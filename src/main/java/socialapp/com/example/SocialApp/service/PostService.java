package socialapp.com.example.SocialApp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import socialapp.com.example.SocialApp.dtos.CommentDto;
import socialapp.com.example.SocialApp.dtos.PostDto;
import socialapp.com.example.SocialApp.entity.Post;

import java.util.List;

public interface PostService {

    Post savePost(PostDto postDto);

    Page<Post> findAllPosts(Pageable pageable);

   // List<Post> findAllPostsWithoutSort();

    Post findById(Long id);

    void deletePostById(Long id);

    Post addCommentToPost(CommentDto commentDto);

    void deleteComment(Long comment_id);

    Post likePost(Long id, String userName);

    Post unLikePost(Long id, String userName);

    void deleteAllPost();

    List<Post> findPostByUser(String userName);

    Post updatePost(PostDto postDto);

}
