package com.mkv.devcatalog.domain.user;

import com.mkv.devcatalog.domain.user.validation.UserUpdateValid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@UserUpdateValid
public class UserUpdateDTO extends UserDTO{
}
