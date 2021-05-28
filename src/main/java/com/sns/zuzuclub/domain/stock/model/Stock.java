package com.sns.zuzuclub.domain.stock.model;

import com.sns.zuzuclub.domain.post.model.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String stockName;

  @OneToMany(mappedBy = "stock")
  private List<PostedStock> postedStockList = new ArrayList<>();

  public List<Post> getPostList(){
    return this.postedStockList.stream()
                               .map(PostedStock::getPost)
                               .collect(Collectors.toList());
  }
}
