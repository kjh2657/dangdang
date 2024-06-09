package aroo.myheart.core.response;


import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPageResponse {
    private int size;
    private long totalElements;
    private int totalPages;
    private int number;
    private Object content;

    public static CommonPageResponse toPage(PageImpl<?> page) {
        return CommonPageResponse.builder()
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(page.getNumber() + 1)
                .content(page.getContent())
                .build();
    }

    public static CommonPageResponse toPage(Page page) {
        return CommonPageResponse.builder()
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(page.getNumber() + 1)
                .content(page.getContent())
                .build();
    }
}
