package com.alibou.security.controller;



import com.alibou.security.dto.request.TagRequestDTO;
import com.alibou.security.dto.response.TagResponseDTO;
import com.alibou.security.exception.BadRequestException;
import com.alibou.security.exception.ErrorCode;
import com.alibou.security.service.TagService;
import com.alibou.security.urlCreator.TagUrlCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/tag")
public class TagController {
    @Autowired
    TagUrlCreator tagUrlCreator;


@Autowired
    private TagService tagServiceImpl;



    @GetMapping("/getTagById/{id}")
    public CollectionModel<TagResponseDTO> getTagByID(@PathVariable(value = "id") int id) {
        List<TagResponseDTO> list = new ArrayList<>();

        list.add(tagServiceImpl.getById(id));

        List<Link> links = new ArrayList<>();
        links.add(tagUrlCreator.getAllTags());


        return CollectionModel.of(list, links);
    }


    @PostMapping("/addTag")
    public CollectionModel<TagResponseDTO> addTag(@RequestBody TagRequestDTO tag) {
        List<TagResponseDTO> list = new ArrayList<>();

        list.add(tagServiceImpl.createTag(tag));

        List<Link> links = new ArrayList<>();
        links.add(tagUrlCreator.getAllTags());
        links.add(tagUrlCreator.getTag(list.get(0).getId()));


        return CollectionModel.of(list, links);
    }

    @GetMapping("/getAllTags")
    public CollectionModel<TagResponseDTO> getAllTags(@PageableDefault Pageable pageable) {


        Page<TagResponseDTO> tags = tagServiceImpl.getAllTags(pageable);

        List<Link> links = new ArrayList<>();
        links.add(tagUrlCreator.getAllTags());


        return CollectionModel.of(tags, links);
    }

    @GetMapping("/getAllTags/{page}")
    public CollectionModel<TagResponseDTO> getAllTagsWithPage(@PathVariable(value = "page") int page) {
        if (page <= 0) {
            throw new BadRequestException("Page must be positive", ErrorCode.BAD_REQUEST_ERROR);
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<TagResponseDTO> list = tagServiceImpl.getAllTags(pageable);
        List<Link> links = new ArrayList<>();


        return CollectionModel.of(list, links);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTagById(@PathVariable(value = "id") int id) {
        tagServiceImpl.deleteById(id);
    }


}
