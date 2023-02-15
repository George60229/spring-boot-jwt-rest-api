package com.alibou.security.service;



import com.alibou.security.converter.TagConverter;
import com.alibou.security.dto.request.TagRequestDTO;
import com.alibou.security.dto.response.TagResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

public interface TagService {

    void setConverter(TagConverter tagConverter);

    TagResponseDTO createTag(@RequestBody TagRequestDTO tagDTO);

    Page<TagResponseDTO> getAllTags(Pageable pageable);

    void deleteById(int id);

    TagResponseDTO getById(int id);




}
