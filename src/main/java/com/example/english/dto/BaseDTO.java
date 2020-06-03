package com.example.english.dto;

import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseDTO implements Serializable {
    private Long id;
    private Boolean active;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;

    public BaseDTO(Long id, Boolean active) {
        this.id = id;
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof BaseDTO)) {
            return false;
        }

        BaseDTO dto = (BaseDTO) obj;

        if (dto.getId() == null) {
            return false;
        }

        return dto.getId().equals(getId());
    }
}
