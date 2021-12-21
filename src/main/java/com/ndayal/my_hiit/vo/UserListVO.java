package com.ndayal.my_hiit.vo;

import com.ndayal.my_hiit.dto.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserListVO {
    private List<User> users;

    @Override
    public String toString() {
        return "UserListVO{" +
                "users=" + users +
                '}';
    }
}
