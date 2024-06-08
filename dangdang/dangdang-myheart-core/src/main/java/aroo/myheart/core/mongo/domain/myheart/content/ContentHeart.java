package aroo.myheart.core.mongo.domain.myheart.content;

import aroo.myheart.core.enums.ContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentHeart {

    private ContentType contentType;
    private String contentId;
}
