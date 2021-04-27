package com.test.protey.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonWithStatusDTO {

    private Long personId;
    private String oldStatus;
    private String newStatus;
}
