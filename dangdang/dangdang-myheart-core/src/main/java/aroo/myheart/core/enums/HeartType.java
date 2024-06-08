package aroo.myheart.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeartType {

    PRODUCT("상품"),
    BRAND("브랜드"),
    CONTENT("콘텐츠");

    private final String description;
}
