package com.mkv.devcatalog.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInsertDTO extends UserDTO{

    private String password;

    public UserInsertDTO() {
        super();
    }
}
