package aroo.myheart.core.ds.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "MYHEART")
public class DsMyHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MYHEART_ID")
    private Long myheartId;

    @Column(name = "CUST_NO")
    private Long custNo;

    @Column(name = "PRODUCT_CD")
    private String productCd;

    @Column(name = "BRAND_CD")
    private String brandCd;

    @Column(name = "CONTENT_CD")
    private String contentCd;

    @Builder
    public DsMyHeart(Long custNo, String productCd, String brandCd, String contentCd) {
        this.custNo = custNo;
        this.productCd = productCd;
        this.brandCd = brandCd;
        this.contentCd = contentCd;
    }
}
