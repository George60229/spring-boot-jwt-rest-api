package com.alibou.security.converter;


import com.alibou.security.dto.request.TagRequestDTO;
import com.alibou.security.dto.response.TagResponseDTO;
import com.alibou.security.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagConverter {

    public Page<TagResponseDTO> convert(Page<Tag> tags) {
        return listToPage(tags.stream()
                .map(this::convertOneToDTO)
                .collect(Collectors.toList()));

    }

    public List<TagResponseDTO> convertWithList(List<Tag> tags) {
        return tags.stream()
                .map(this::convertOneToDTO)
                .collect(Collectors.toList());

    }

    public TagResponseDTO convertOneToDTO(Tag tag) {
        TagResponseDTO tagResponseDTO = new TagResponseDTO();
        tagResponseDTO.setId(tag.getId());
        tagResponseDTO.setName(tag.getName());
        return tagResponseDTO;
    }

    public Tag convertDTOtoModel(TagRequestDTO tagDTO) {
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        return tag;
    }

    public Page<TagResponseDTO> listToPage(List<TagResponseDTO> tagResponseDto) {

        return new PageImpl<>(tagResponseDto);
    }
}
