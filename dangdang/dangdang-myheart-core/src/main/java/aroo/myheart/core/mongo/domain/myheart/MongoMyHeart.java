package aroo.myheart.core.mongo.domain.myheart;

import aroo.myheart.core.common.CommonDocument;
import aroo.myheart.core.mongo.domain.myheart.brand.BrandHeart;
import aroo.myheart.core.mongo.domain.myheart.content.ContentHeart;
import aroo.myheart.core.mongo.domain.myheart.product.ProductHeart;
import aroo.myheart.core.enums.HeartType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "myheart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MongoMyHeart extends CommonDocument {
    @Id
    private String id;
    private Long custNo;
    private HeartType heartType;
    private ProductHeart product;
    private BrandHeart brand;
    private ContentHeart content;
    private Long migrationIdx;

    @Builder
    public MongoMyHeart(Long custNo, HeartType heartType, ProductHeart product, BrandHeart brand, ContentHeart content, Long migrationIdx) {
        this.custNo = custNo;
        this.heartType = heartType;
        this.product = product;
        this.brand = brand;
        this.content = content;
        this.migrationIdx = migrationIdx;
    }
}