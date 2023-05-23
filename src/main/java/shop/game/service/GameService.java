package shop.game.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.game.common.file.FileStore;
import shop.game.common.file.UploadFile;
import shop.game.domain.Game;
import shop.game.domain.GameImage;
import shop.game.domain.Partner;
import shop.game.dto.GoodsRegisterFormDto;
import shop.game.dto.SessionLoginDto;
import shop.game.repository.GameImageRepository;
import shop.game.repository.GameRepository;
import shop.game.repository.PartnerRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final PartnerRepository partnerRepository;

    private final GameImageRepository gameImageRepository;
    private final FileStore fileStore;

    public void save(GoodsRegisterFormDto registerFormDto) {
        Game game = Game.builder()
                .name(registerFormDto.getGameName())
                .developer(registerFormDto.getDeveloper())
                .publisher(registerFormDto.getPublisher())
                .description(registerFormDto.getDescription())
                .build();
        gameRepository.save(game);
    }
    @Transactional
    public void register(GoodsRegisterFormDto registerFormDto, SessionLoginDto sessionLoginDto) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(registerFormDto.getThumbnail());

        Partner partner = partnerRepository.findById(sessionLoginDto.getId()).orElseThrow(() -> new IllegalArgumentException("Partner not found"));

        Game game = Game.builder()
                .partner(partner)
                .name(registerFormDto.getGameName())
                .developer(registerFormDto.getDeveloper())
                .publisher(registerFormDto.getPublisher())
                .description(registerFormDto.getDescription())
                .build();
        gameRepository.save(game);

        GameImage gameImage = GameImage.builder()
                .game(game)
                .originalName(uploadFile.getUploadFileName())
                .storedName(uploadFile.getStoreFileName())
                .build();
        gameImageRepository.save(gameImage);
    }
}
