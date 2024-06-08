package aroo.myheart.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentType {

    I("기존 기획전"),
    N("신규 기획전");

    private final String description;
}
