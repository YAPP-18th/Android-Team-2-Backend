package com.sns.zuzuclub.domain.stock.model;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.global.AuditEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PostedStock extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Stock stock;

  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  public PostedStock(Stock stock, Post post) {
    this.initStock(stock);
    this.initPost(post);
  }

  private void initStock(Stock stock){
    this.stock = stock;
    stock.getPostedStockList().add(this);
  }

  private void initPost(Post post){
    this.post = post;
    post.getPostedStockList().add(this);
  }

  public void deleteStock() {
    this.stock.getPostedStockList().remove(this);
    this.stock = null;
  }
}
