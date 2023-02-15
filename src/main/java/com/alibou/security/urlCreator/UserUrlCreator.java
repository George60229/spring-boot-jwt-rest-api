package com.alibou.security.urlCreator;

import com.alibou.security.controller.UserController;
import com.alibou.security.dto.request.BuyCertificatesRequestDTO;
import com.alibou.security.dto.request.UserRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserUrlCreator {
    public Link getAllUsers() {
        Pageable pageable = PageRequest.of(0, 5);
        return linkTo(methodOn(UserController.class)
                .getAllUsers(pageable)).withRel("allUsers");
    }

    public Link addOrder(int id, BuyCertificatesRequestDTO requestDTO) {

        return linkTo(methodOn(UserController.class).
                buyCertificate(requestDTO, id)).withRel("addOrder");
    }

    public Link addUser(UserRequestDto userRequestDto) {
        return linkTo(methodOn(UserController.class).addUser(userRequestDto)).withRel("addUser");
    }



    public Link getUserById(int id) {
        return linkTo(methodOn(UserController.class).getById(id)).withRel("getUserById");
    }

}
