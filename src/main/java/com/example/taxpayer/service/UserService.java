package com.example.taxpayer.service;

import com.example.taxpayer.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> userList;

    public UserService() {
        userList = new ArrayList<>();

        User user1 = new User(1,"Ida","ida@mail.com","adress",0.0);
        User user2 = new User(2,"Ida","ida@mail.com","adress",0.0);
        User user3 = new User(3,"Ida","ida@mail.com","adress",0.0);
        User user4 = new User(4,"Ida","ida@mail.com","adress",0.0);
        User user5 = new User(5,"Ida","ida@mail.com","adress",0.0);

        userList.addAll(Arrays.asList(user1,user2,user3,user4,user5));
    }
    public Optional<User> getUser(Long id) {
        Optional optional = Optional.empty();
        for (User user : userList) {
            if (user.getId() == id) {
                optional = Optional.of(user);
                return optional;
            }
        }
        return optional;
    }
}
