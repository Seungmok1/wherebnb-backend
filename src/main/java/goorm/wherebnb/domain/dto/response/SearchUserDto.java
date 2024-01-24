package goorm.wherebnb.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class SearchUserDto {
    private Long userId;
    private String userName;
    private String picture;
    private String explanation;

    @Builder
    public SearchUserDto(Long userId, String userName, String picture, String explanation) {
        this.userId = userId;
        this.userName = userName;
        this.picture = picture;
        this.explanation = explanation;
    }

}
