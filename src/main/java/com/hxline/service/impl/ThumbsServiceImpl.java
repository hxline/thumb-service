package com.hxline.service.impl;

import com.hxline.service.ThumbsService;
import com.hxline.domain.Thumbs;
import com.hxline.repository.ThumbsRepository;
import com.hxline.service.dto.ThumbsDTO;
import com.hxline.service.mapper.ThumbsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Thumbs.
 */
@Service
@Transactional
public class ThumbsServiceImpl implements ThumbsService{

    private final Logger log = LoggerFactory.getLogger(ThumbsServiceImpl.class);
    
    private final ThumbsRepository thumbsRepository;

    private final ThumbsMapper thumbsMapper;

    public ThumbsServiceImpl(ThumbsRepository thumbsRepository, ThumbsMapper thumbsMapper) {
        this.thumbsRepository = thumbsRepository;
        this.thumbsMapper = thumbsMapper;
    }

    /**
     * Save a thumbs.
     *
     * @param thumbsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ThumbsDTO save(ThumbsDTO thumbsDTO) {
        log.debug("Request to save Thumbs : {}", thumbsDTO);
        Thumbs thumbs = thumbsMapper.thumbsDTOToThumbs(thumbsDTO);
        thumbs = thumbsRepository.save(thumbs);
        ThumbsDTO result = thumbsMapper.thumbsToThumbsDTO(thumbs);
        return result;
    }

    /**
     *  Get all the thumbs.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ThumbsDTO> findAll() {
        log.debug("Request to get all Thumbs");
        List<ThumbsDTO> result = thumbsRepository.findAll().stream()
            .map(thumbsMapper::thumbsToThumbsDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one thumbs by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ThumbsDTO findOne(Long id) {
        log.debug("Request to get Thumbs : {}", id);
        Thumbs thumbs = thumbsRepository.findOne(id);
        ThumbsDTO thumbsDTO = thumbsMapper.thumbsToThumbsDTO(thumbs);
        return thumbsDTO;
    }

    /**
     *  Delete the  thumbs by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Thumbs : {}", id);
        thumbsRepository.delete(id);
    }
}
