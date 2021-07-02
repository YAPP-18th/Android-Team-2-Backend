package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostReactionType;
import com.sns.zuzuclub.domain.post.model.PostReaction;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ReactionDto {

  private int sympathyCount;
  private int funnyCount;
  private int angryCount;
  private int sadCount;
  private int fightingCount;
  private int enviousCount;
  private int thinkCount;

  private List<ReactionUserDto> reactionUserDtoList = new ArrayList<>();

  public ReactionDto(List<PostReaction> postReactionList) {
    this.sympathyCount = 0;
    this.funnyCount = 0;
    this.angryCount = 0;
    this.sadCount = 0;
    this.fightingCount = 0;
    this.enviousCount = 0;
    this.thinkCount = 0;
    postReactionList.forEach(postReaction -> {
      calculateReactionCount(postReaction);
      reactionUserDtoList.add(new ReactionUserDto(postReaction));
    });
  }

  private void calculateReactionCount(PostReaction postReaction){
    PostReactionType postReactionType = postReaction.getReactionType();
    switch (postReactionType){
      case SYMPATHY:
        this.sympathyCount += 1;
        break;
      case FUNNY:
        this.funnyCount += 1;
        break;
      case ANGRY:
        this.angryCount += 1;
        break;
      case SAD:
        this.sadCount += 1;
        break;
      case FIGHTING:
        this.fightingCount += 1;
        break;
      case ENVIOUS:
        this.enviousCount += 1;
        break;
      case THINK:
        this.thinkCount += 1;
        break;
    }
  }
}
