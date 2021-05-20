package com.sns.zuzuclub.domain.post.service;

import com.sns.zuzuclub.controller.post.dto.CreatePostRequestDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.PostedStockRepository;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.util.S3Uploader;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;
  private final PostedStockRepository postedStockRepository;
  private final StockRepository stockRepository;
  private final S3Uploader s3Uploader;

  @Transactional
  public Post createPost(User user, CreatePostRequestDto createPostRequestDto,
      MultipartFile multipartFile){
    // Post 생성 전에, PostedStockList 를 연결할 수가 없음.
    // 코드 응집성을 높이기 위해서 만듦.
    // 파라미터로 RequestDto 를 받는 것은, 어차피 Post의 생성이 dto에 의존적일 수 밖에 없기때문.
    // 뭔가 애매한데.... 리팩토링필요
    Post newPostEntity = createPostRequestDto.toPostEntity(user, s3Uploader, multipartFile);
    postRepository.save(newPostEntity);

    List<Stock> requestStockList = stockRepository.findAllById(createPostRequestDto.getRequestStockIdList());
    List<PostedStock> postedStockList = requestStockList.stream()
                                                        .map(stock -> new PostedStock(stock, newPostEntity))
                                                        .collect(Collectors.toList());
    postedStockRepository.saveAll(postedStockList);
    return newPostEntity;
  }
}
