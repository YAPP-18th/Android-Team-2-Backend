package com.sns.zuzuclub.domain.user.application;



import com.sns.zuzuclub.domain.user.dto.signup.SignupRequestDto;
import com.sns.zuzuclub.domain.user.dto.signup.StockResponseDto;
import com.sns.zuzuclub.domain.stock.model.SignupStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.SignupStockRepository;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.domain.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class SignupService {

  private final UserService userService;

  private final UserRepository userRepository;
  private final StockRepository stockRepository;
  private final SignupStockRepository signupStockRepository;

  public Boolean isDuplicatedNickname(String nickname) {
    return userRepository.existsByNickname(nickname);
  }

  public List<StockResponseDto> getStockList() {
    // 이거 정규화 하려고 이렇게 한건데, 너무 DB 생각만하고 객체는 무시하고 하는거같은데...?
    List<SignupStock> signupStockList = signupStockRepository.findAll();
    List<Stock> stockList = signupStockList.stream()
                                           .map(SignupStock::getStock)
                                           .collect(Collectors.toList());
    return StockResponseDto.toListFrom(stockList);
  }

  @Transactional
  public String registerUser(Long userId, SignupRequestDto signupRequestDto) {

    User user = UserHelper.findUserById(userRepository, userId);
    user.registerNickname(userService, signupRequestDto.getNickname());
    user.registerIntroduction(signupRequestDto.getIntroduction());

    List<Stock> stockList = stockRepository.findAllById(signupRequestDto.getScrapStockIdList());
    UserStockScrap.toListFrom(user, stockList);
    // 영속성 전이

    return user.getNickname();
  }
}
