package com.sns.zuzuclub.domain.user.application;



import com.sns.zuzuclub.controller.signup.dto.SignupRequestDto;
import com.sns.zuzuclub.controller.signup.dto.StockListResponseDto;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.domain.user.repository.UserStockScrapRepository;
import com.sns.zuzuclub.domain.user.service.UserInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class SignupService {

  private final UserInfoService userInfoService;

  private final UserRepository userRepository;
  private final UserInfoRepository userInfoRepository;
  private final StockRepository stockRepository;
  private final UserStockScrapRepository userStockScrapRepository;

  public Boolean hasDuplicateNickname(String nickname) {
    return userInfoService.hasDuplicatedNickname(nickname);
  }

  public List<StockListResponseDto> getStockList() {
    // 여기는 무슨 기준으로 정렬하지 ?
    return null;
  }

  @Transactional
  public String registerUser(Long userId, SignupRequestDto signupRequestDto) {

    User userEntity = UserHelper.findUserById(userRepository, userId);
    UserInfo newUserInfo = signupRequestDto.toUserInfoEntity(userEntity);
    userInfoRepository.save(newUserInfo);

    List<Long> scrapStockIdList = signupRequestDto.getScrapStockIdList();
    List<Stock> scrapStockList = stockRepository.findAllById(scrapStockIdList);

    scrapStockList.forEach(
        stock -> userStockScrapRepository.save(new UserStockScrap(userEntity, stock))
    );

    return newUserInfo.getNickname();
  }
}
