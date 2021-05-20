package com.sns.zuzuclub.domain.comment.application;

import com.sns.zuzuclub.controller.comment.dto.CreateCommentRequestDto;
import com.sns.zuzuclub.controller.comment.dto.CreateCommentResponseDto;
import com.sns.zuzuclub.domain.comment.helper.CommentHelper;
import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.comment.repository.CommentRepository;
import com.sns.zuzuclub.domain.post.helper.PostHelper;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    //private final ModelMapper modelMapper;

    @Transactional
    public CreateCommentResponseDto createComment(Long userId, Long postId, CreateCommentRequestDto createCommentRequestDto) {
        User userEntity = UserHelper.findUserById(userRepository, userId);
        Post postEntity = PostHelper.findPostById(postRepository, postId);
        Comment newComment = createCommentRequestDto.toEntity(commentRepository, userEntity, postEntity);
        commentRepository.save(newComment);
        return new CreateCommentResponseDto(newComment);
    }

//    public List<CommentResponseDto> findAll() {
//        List<Comment> comments = commentRepository.findAll();
//        return comments.stream().map(c -> modelMapper.map(c, CommentResponseDto.class))
//            .collect(Collectors.toList());
//    }
//
//    public CommentResponseDto findById(Long commentId) {
//        Comment comment = commentRepository.findById(commentId)
//            .orElseThrow(() -> new NullPointerException());
//        CommentResponseDto commentDto = modelMapper.map(comment, CommentResponseDto.class);
//        return commentDto;
//    }
//
//    public Comment save(CommentRequestDto commentRequestDto) {
//        Post post = postRepository.findById(commentRequestDto.getPostId())
//            .orElseThrow(() -> new NullPointerException());
//        Comment comment = Comment.builder()
////                       .user() userService 작성후
//            .content(commentRequestDto.getContent())
//            .post(post)
//            .build();
//        commentRepository.save(comment);
//        return comment;
//    }
//
//    public List<CommentResponseDto> findByPostId(Long postId){
//        Post post = postRepository.findById(postId)
//            .orElseThrow(() -> new NullPointerException());
//            List<Comment> comments = commentRepository.findByPost(post);
//        return comments.stream().map(c -> modelMapper.map(c, CommentResponseDto.class))
//            .collect(Collectors.toList());
//    }
//
//    public Comment saveChildComment(ChildCommentDto childCommentDto){
//        Post post = postRepository.findById(childCommentDto.getPostId())
//            .orElseThrow(() -> new NullPointerException());
//        Comment parentComment = commentRepository.findById(childCommentDto.getParentId())
//            .orElseThrow(() -> new NullPointerException());
//        Comment childComment = Comment.builder()
////                       .user() userService 작성후
//            .content(childCommentDto.getContent())
//            .parentComment(parentComment)
//            .post(post)
//            .build();
//        return childComment;
//    }



}
