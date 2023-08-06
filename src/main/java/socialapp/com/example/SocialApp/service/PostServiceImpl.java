package socialapp.com.example.SocialApp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import socialapp.com.example.SocialApp.dtos.CommentDto;
import socialapp.com.example.SocialApp.dtos.PostDto;
import socialapp.com.example.SocialApp.entity.Comment;
import socialapp.com.example.SocialApp.entity.Post;
import socialapp.com.example.SocialApp.entity.SocialMediaUser;
import socialapp.com.example.SocialApp.exception.SocialMediaUserException;
import socialapp.com.example.SocialApp.repository.CommentRepository;
import socialapp.com.example.SocialApp.repository.PostRepository;
import socialapp.com.example.SocialApp.repository.SocialMediaUserRepo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    SocialMediaUserRepo userRepository;

    @Autowired
    CommentRepository commentRepository;

    ModelMapper mapper;

    @Override
    public Post savePost(PostDto postDto) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setDateCreated(Instant.now().toString());
        //  post.setDateUpdated(LocalDateTime.now().toString());
        //     post.setImageUrl(postDto.getImageFile());
        post.setVideoUrl(postDto.getVideoUrl());

        SocialMediaUser user = userRepository.findByUserNameIgnoreCase(postDto.getUserName()).get();

        post.setPostUser(user.getUserName());

        postRepository.save(post);

        user.getPostList().add(post);

        userRepository.save(user);

        return post;
    }

    @Override
    public Page<Post> findAllPosts(Pageable pageable) {


        return postRepository.findAll(pageable);
    }


    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new SocialMediaUserException("Post does not exist"));

    }

    @Override
    public void deletePostById(Long postId) {
        Post post = postRepository.findById(postId).get();
    }

    @Override
    public Post addCommentToPost(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new SocialMediaUserException("Post not found"));
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setDateCreated(Instant.now().toString());
        comment.setCommenter(userRepository.findByUserNameIgnoreCase(commentDto.getCommenter()).get().getUserName());
        comment.setPost(post);

        commentRepository.save(comment);

        post.getComments().add(comment);

        return postRepository.save(post);
    }

    @Override
    public void deleteComment(Long comment_id) {
        commentRepository.deleteById(comment_id);

    }

    @Override
    public Post likePost(Long id, String userName) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SocialMediaUserException("Post author not found"));;
        post.getNumberOfLikes().add(userName);
        return postRepository.save(post);
    }

    @Override
    public Post unLikePost(Long id, String userName) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SocialMediaUserException("Post author not found"));;
        post.getNumberOfLikes().remove(userName);
        return postRepository.save(post);

    }

    @Override
    public void deleteAllPost() {
        postRepository.deleteAll();
    }

    @Override
    public List<Post> findPostByUser(String userName) {
        return postRepository.findByPostUserIgnoreCaseOrderByDateCreated(userName);
    }

    @Override
    public Post updatePost(PostDto postDto) {
        Post post = postRepository.findById(postDto.getPostId())
                .orElseThrow(() -> new SocialMediaUserException("Post not found"));;
        mapper.map(postDto, post);
        post.setDateUpdated(LocalDateTime.now().toString());
        return postRepository.save(post);
    }
}
