package com.sns.zuzuclub.domain.user.model;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sns.zuzuclub.domain.stock.model.Stock;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserStockScrap {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Stock stock;

  public UserStockScrap(User user, Stock stock) {
    this();
    updateUser(user);
    this.stock = stock;
  }

  public static List<UserStockScrap> toListFrom(User user, List<Stock> stockList) {
    return stockList.stream()
                    .map(stock -> new UserStockScrap(user, stock))
                    .collect(Collectors.toList());
  }

  public void updateUser(User user){
    if(this.user != null){
      this.user.decreaseUserStockScrapCount();
      this.user.getUserStockScrapList().remove(this);
    }
    this.user = user;
    user.getUserStockScrapList().add(this);
    user.increaseUserStockScrapCount();
  }

  public void deleteUser(){
    user.decreaseUserStockScrapCount();
    this.user.getUserStockScrapList().remove(this);
    this.user = null;
  }

  public void deleteStock(){
    this.stock = null;
  }
}
