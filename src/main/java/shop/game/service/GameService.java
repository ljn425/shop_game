package shop.game.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.game.common.file.FileStore;
import shop.game.common.file.UploadFile;
import shop.game.domain.*;
import shop.game.dto.*;
import shop.game.enums.CategoryType;
import shop.game.enums.GameType;
import shop.game.enums.ImageType;
import shop.game.repository.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final GameQueryRepository gameQueryRepository;
    private final PartnerRepository partnerRepository;
    private final CategoryRepository categoryRepository;
    private final GameImageRepository gameImageRepository;
    private final SpecRepository specRepository;

    private final MovieRepository movieRepository;
    private final FileStore fileStore;

    /**
     * 게임 기본 정보 등록
     * @param registerFormDto
     * @param sessionLoginDto
     * @throws IOException
     */
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
        gameRepository.save(game);

        GameImage gameImage = buildGameImage(game, uploadFile, ImageType.COVER);
        game.getGameImages().add(gameImage);

        for (CategoryType categoryType : registerFormDto.getCategoryTypes()) {
            Category category = Category.builder()
                    .game(game)
                    .categoryType(categoryType)
                    .build();
            game.getCategories().add(category);
        }

        //gameRepository.save(game);
    }

    public Map<String, String> createMapFromCategoryEnums() {
        Map<String, String> categories = new LinkedHashMap<>();
        for (CategoryType categoryType : CategoryType.values()) {
            categories.put(categoryType.name(), categoryType.getDisplayName());
        }

        return categories;
    }

    public Page<GoodsRegisterListDto> goodsPaging(Long partnerId, Pageable pageable) {
        return gameQueryRepository.searchGoodsPage(partnerId, pageable);
    }

    /**
     * 게임 상세 정보 조회
     * @param gameId
     * @return
     */
    public GoodsDetailDto findGoodsDetail(Long gameId) {
        GoodsDetailDto goodsDetail = gameQueryRepository.findGoodsDetail(gameId);
        List<Category> categories = categoryRepository.findByGameId(gameId);
        List<GameImage> gameImages = gameImageRepository.findByGameIdAndImageType(gameId, ImageType.SCREENSHOT);
        List<Movie> movies = movieRepository.findByGameId(gameId);
        List<Spec> specs = specRepository.findByGameId(gameId);

        goodsDetail.setCategories(categories);
        goodsDetail.setGameImages(gameImages);
        goodsDetail.setGameMovies(movies);
        goodsDetail.setSpecs(specs);

        return goodsDetail;
    }

    /**
     * FilePondProcess(이미지업로드) 처리
     * @param file
     * @param gameId
     * @return
     * @throws IOException
     */
    @Transactional
    public FilePondProcessDto uploadFile(MultipartFile file, Long gameId) throws IOException{
        GameImage findStoreName = gameImageRepository.findByStoredName(file.getOriginalFilename());

        String storeName = "";

        if (findStoreName == null) {
            UploadFile uploadFile = fileStore.storeFile(file);
            Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("Game not found"));

            GameImage gameImage = buildGameImage(game, uploadFile, ImageType.SCREENSHOT);

            gameImageRepository.save(gameImage);

            storeName = uploadFile.getStoreFileName();

        }

        storeName = file.getOriginalFilename();

        return FilePondProcessDto.builder()
                .filename(storeName)
                .build();
    }

    private GameImage buildGameImage(Game game, UploadFile uploadFile, ImageType imageType) {
        GameImage gameImage = GameImage.builder()
                .game(game)
                .originalName(uploadFile.getUploadFileName())
                .storedName(uploadFile.getStoreFileName())
                .extension(uploadFile.getExt())
                .size(uploadFile.getFileSize())
                .imageType(imageType)
                .build();
        return gameImage;
    }

    /**
     * FilePondProcess(이미지삭제) 처리
     * @param filename
     */
    @Transactional
    public void deleteFile(String filename) throws IOException {
        GameImage gameImage = gameImageRepository.findByStoredName(filename);
        gameImageRepository.delete(gameImage);
        fileStore.deleteFile(filename);
    }

    public List<FilePondProcessDto> findGameImages(Long gameId, ImageType imageType) {
        List<GameImage> gameImages = gameImageRepository.findByGameIdAndImageType(gameId, imageType);
        List<FilePondProcessDto> result = gameImages.stream()
                .map(gameImage -> FilePondProcessDto.builder()
                        .filename(gameImage.getStoredName())
                        .build())
                .collect(Collectors.toList());

        return result;
    }


}
