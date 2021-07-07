package com.sns.zuzuclub.domain.post.application;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.controller.post.dto.CreatePostRequestDto;
import com.sns.zuzuclub.controller.post.dto.CreatePostResponseDto;
import com.sns.zuzuclub.controller.post.dto.ModifyPostRequestDto;
import com.sns.zuzuclub.controller.post.dto.PostDetailResponseDto;
import com.sns.zuzuclub.controller.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.post.helper.PostHelper;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.PostedStockRepository;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

  private final UserRepository userRepository;
  private final PostedStockRepository postedStockRepository;
  private final PostRepository postRepository;
  private final StockRepository stockRepository;


  @Transactional
  public CreatePostResponseDto createPost(Long userId, CreatePostRequestDto createPostRequestDto) {

    User userEntity = UserHelper.findUserById(userRepository, userId);
    Post newPostEntity = createPostRequestDto.toPostEntity(userEntity);

    List<String> requestStockNameList = createPostRequestDto.getRequestStockNameList();
    requestStockNameList.addAll(newPostEntity.extractStockNameFromContent());

    List<Stock> requestStockList = stockRepository.findAllByStockNameIn(requestStockNameList);
    requestStockList.forEach(stock -> stock.addPostEmotionInfo(createPostRequestDto.getPostEmotionType()));

    List<PostedStock> postedStockList = requestStockList.stream()
                                                        .map(stock -> new PostedStock(stock, newPostEntity))
                                                        .collect(Collectors.toList());
    postedStockRepository.saveAll(postedStockList);
    postRepository.save(newPostEntity);
    return new CreatePostResponseDto(newPostEntity);
  }

  public PostDetailResponseDto getPostDetail(Long postId, Long loginUserId) {
    Post postEntity = PostHelper.findPostById(postRepository, postId);
    return new PostDetailResponseDto(postEntity, loginUserId);
  }

  @Transactional
  public PostResponseDto modifyPost(Long userId, Long postId, ModifyPostRequestDto modifyPostRequestDto) {

    Post post = PostHelper.findPostById(postRepository, postId);

    List<PostedStock> result = post.deletePostedStock();
    postedStockRepository.deleteAll(result);
    post.getPostedStockList().clear();

    PostEmotionType newPostEmotionType = modifyPostRequestDto.getPostEmotionType();
    List<Stock> newStockList = stockRepository.findAllByStockNameIn(modifyPostRequestDto.getPostedStockNameList());
    newStockList.forEach(newStock -> newStock.addPostEmotionInfo(newPostEmotionType));
    List<PostedStock> newPostedStockList = newStockList.stream()
                                                       .map(stock -> new PostedStock(stock, post))
                                                       .collect(Collectors.toList());
    postedStockRepository.saveAll(newPostedStockList);

    post.modify(modifyPostRequestDto);
    return new PostResponseDto(post, userId);
  }

  @Transactional
  public void deletePost(Long postId) {
    Post post = PostHelper.findPostById(postRepository, postId);

    post.deleteUser();

    List<PostedStock> result = post.deletePostedStock();
    postedStockRepository.deleteAll(result);
    post.getPostedStockList().clear();

    post.deleteComment();

    postRepository.delete(post);
  }
}