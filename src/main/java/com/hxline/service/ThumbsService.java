package com.hxline.service;

import com.hxline.service.dto.ThumbsDTO;
import java.util.List;

/**
 * Service Interface for managing Thumbs.
 */
public interface ThumbsService {

    /**
     * Save a thumbs.
     *
     * @param thumbsDTO the entity to save
     * @return the persisted entity
     */
    ThumbsDTO save(ThumbsDTO thumbsDTO);

    /**
     *  Get all the thumbs.
     *  
     *  @return the list of entities
     */
    List<ThumbsDTO> findAll();

    /**
     *  Get the "id" thumbs.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ThumbsDTO findOne(Long id);

    /**
     *  Delete the "id" thumbs.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
