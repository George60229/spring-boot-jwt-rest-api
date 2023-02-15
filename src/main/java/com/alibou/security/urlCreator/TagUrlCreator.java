package com.alibou.security.urlCreator;

import com.alibou.security.controller.TagController;
import com.alibou.security.dto.request.TagRequestDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagUrlCreator {
    public Link getAllTags() {
        Pageable pageable = PageRequest.of(0, 5);
        return linkTo(methodOn(TagController.class)
                .getAllTags(pageable)).withRel("allUsers");
    }

    public Link getTag(int id) {

        return linkTo(methodOn(TagController.class).getTagByID(id)).withRel("getById");

    }

    public Link addTag(TagRequestDTO tagRequestDTO){
        return linkTo(methodOn(TagController.class).addTag(tagRequestDTO)).withRel("addTag");
    }


}
