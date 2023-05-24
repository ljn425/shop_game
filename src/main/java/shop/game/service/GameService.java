package shop.game.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.game.common.file.FileStore;
import shop.game.common.file.UploadFile;
import shop.game.domain.Category;
import shop.game.domain.Game;
import shop.game.domain.GameImage;
import shop.game.domain.Partner;
import shop.game.dto.GoodsRegisterFormDto;
import shop.game.dto.SessionLoginDto;
import shop.game.enums.CategoryType;
import shop.game.enums.GameType;
import shop.game.enums.ImageType;
import shop.game.repository.CategoryRepository;
import shop.game.repository.GameImageRepository;
import shop.game.repository.GameRepository;
import shop.game.repository.PartnerRepository;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final PartnerRepository partnerRepository;
    private final FileStore fileStore;

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
                .gameType(GameType.GAME)
                .build();
        //gameRepository.save(game);

        GameImage gameImage = GameImage.builder()
                .game(game)
                .originalName(uploadFile.getUploadFileName())
                .storedName(uploadFile.getStoreFileName())
                .extension(uploadFile.getExt())
                .size(uploadFile.getFileSize())
                .imageType(ImageType.COVER)
                .build();
        game.getGameImages().add(gameImage);

        for (CategoryType categoryType : registerFormDto.getCategoryTypes()) {
            Category category = Category.builder()
                    .game(game)
                    .categoryType(categoryType)
                    .build();
            game.getCategories().add(category);
        }

        gameRepository.save(game);
    }

    public Map<String, String> createMapFromCategoryEnums() {
        Map<String, String> categories = new LinkedHashMap<>();
        for (CategoryType categoryType : CategoryType.values()) {
            categories.put(categoryType.name(), categoryType.getDisplayName());
        }

        return categories;
    }
}
