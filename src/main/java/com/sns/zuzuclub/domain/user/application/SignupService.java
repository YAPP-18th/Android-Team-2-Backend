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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class SignupService {

  private final UserRepository userRepository;
  private final UserInfoRepository userInfoRepository;
  private final StockRepository stockRepository;
  private final UserStockScrapRepository userStockScrapRepository;

  public Boolean hasDuplicateNickname(String nickname) {
    if(userInfoRepository.findByNickname(nickname).isPresent()){
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public List<StockListResponseDto> getStockList() {
    // 여기는 무슨 기준으로 정렬하지 ?
    return null;
  }

  @Transactional
  public String registerUser(Long userId, SignupRequestDto signupRequestDto) {

    User userEntity = UserHelper.findUserById(userRepository, userId);
    UserInfo userInfoEntity = userInfoRepository.save(signupRequestDto.createUserInfoEntity(userEntity));

    List<Long> stockIdList = signupRequestDto.getLikeStockIdList();
    List<Stock> stockList = stockRepository.findAllById(stockIdList);
    stockList.forEach(stock -> {
      UserStockScrap userStockScrap = new UserStockScrap(userEntity, stock);
      userStockScrapRepository.save(userStockScrap);
    });

    return userInfoEntity.getNickname();
  }
}
