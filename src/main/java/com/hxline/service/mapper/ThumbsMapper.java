package com.hxline.service.mapper;

import com.hxline.domain.*;
import com.hxline.service.dto.ThumbsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Thumbs and its DTO ThumbsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThumbsMapper {

    ThumbsDTO thumbsToThumbsDTO(Thumbs thumbs);

    List<ThumbsDTO> thumbsToThumbsDTOs(List<Thumbs> thumbs);

    Thumbs thumbsDTOToThumbs(ThumbsDTO thumbsDTO);

    List<Thumbs> thumbsDTOsToThumbs(List<ThumbsDTO> thumbsDTOs);
}
