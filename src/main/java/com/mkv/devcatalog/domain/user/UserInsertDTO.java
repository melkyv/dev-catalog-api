package com.mkv.devcatalog.domain.user;

import com.mkv.devcatalog.domain.user.validation.UserInsertValid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@UserInsertValid
public class UserInsertDTO extends UserDTO{

    private String password;

    public UserInsertDTO() {
        super();
    }
}
