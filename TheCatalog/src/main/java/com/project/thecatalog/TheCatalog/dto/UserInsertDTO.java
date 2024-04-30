package com.project.thecatalog.TheCatalog.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class UserInsertDTO extends UserDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    private String password;

    UserInsertDTO() {
        super();
    }


}
