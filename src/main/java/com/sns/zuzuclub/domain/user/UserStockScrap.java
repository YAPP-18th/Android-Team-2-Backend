package com.sns.zuzuclub.domain.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sns.zuzuclub.domain.stock.Stock;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "stock_scrap")
@Getter
public class UserStockScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_scrap_id")
    private Long userStockScrapId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "stockScrap")
    private List<Stock> stock;

    @Builder
    public UserStockScrap(Long userStockScrapId, User user, List<Stock> stock) {
        this.userStockScrapId = userStockScrapId;
        this.user = user;
        this.stock = stock;
    }
}
