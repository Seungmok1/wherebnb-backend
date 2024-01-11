package goorm.wherebnb.domain.dto.request.manageYourSpace;

import lombok.Getter;

@Getter
public class UpdateStatusRequestDto {
    private Long propertyId;
    private boolean status;
}
