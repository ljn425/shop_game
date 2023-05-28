package shop.game.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import shop.game.dto.GoodsRegisterListDto;
import shop.game.dto.QGoodsRegisterListDto;
import shop.game.enums.ImageType;

import javax.persistence.EntityManager;
import java.util.List;

import static shop.game.domain.QGame.game;
import static shop.game.domain.QGameImage.gameImage;

@Slf4j
@Repository
public class GameQueryRepository {
    private final JPAQueryFactory query;

    public GameQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Page<GoodsRegisterListDto> searchGoodsPage(Long partnerId, Pageable pageable) {
        List<GoodsRegisterListDto> content = query.select(new QGoodsRegisterListDto(gameImage.storedName, game.name, game.developer, game.publisher, game.createdDate))
                .from(game)
                .join(game.gameImages, gameImage)
                .where(partnerIdEq(partnerId), imageTypeEq())
                .orderBy(game.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(game.count())
                .from(game)
                .join(game.gameImages, gameImage)
                .where(imageTypeEq())
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, count::longValue);
    }

    private BooleanExpression partnerIdEq(Long partnerId) {
        return game.partner.id.eq(partnerId);
    }

    private BooleanExpression imageTypeEq() {
        return gameImage.imageType != null ? gameImage.imageType.eq(ImageType.COVER) : null;
    }
}
