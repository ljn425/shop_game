package shop.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.game.domain.Game;
import shop.game.dto.GoodsRegisterFormDto;
import shop.game.repository.GameRepository;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public void save(GoodsRegisterFormDto registerFormDto) {
        Game game = Game.builder()
                .name(registerFormDto.getGameName())
                .developer(registerFormDto.getDeveloper())
                .publisher(registerFormDto.getPublisher())
                .description(registerFormDto.getDescription())
                .build();
        gameRepository.save(game);
    }
}
