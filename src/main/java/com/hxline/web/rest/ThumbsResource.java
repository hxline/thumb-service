package com.hxline.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hxline.service.ThumbsService;
import com.hxline.web.rest.util.HeaderUtil;
import com.hxline.service.dto.ThumbsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Thumbs.
 */
@RestController
@RequestMapping("/api")
public class ThumbsResource {

    private final Logger log = LoggerFactory.getLogger(ThumbsResource.class);

    private static final String ENTITY_NAME = "thumbs";
        
    private final ThumbsService thumbsService;

    public ThumbsResource(ThumbsService thumbsService) {
        this.thumbsService = thumbsService;
    }

    /**
     * POST  /thumbs : Create a new thumbs.
     *
     * @param thumbsDTO the thumbsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thumbsDTO, or with status 400 (Bad Request) if the thumbs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/thumbs")
    @Timed
    public ResponseEntity<ThumbsDTO> createThumbs(@Valid @RequestBody ThumbsDTO thumbsDTO) throws URISyntaxException {
        log.debug("REST request to save Thumbs : {}", thumbsDTO);
        if (thumbsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new thumbs cannot already have an ID")).body(null);
        }
        ThumbsDTO result = thumbsService.save(thumbsDTO);
        return ResponseEntity.created(new URI("/api/thumbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /thumbs : Updates an existing thumbs.
     *
     * @param thumbsDTO the thumbsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thumbsDTO,
     * or with status 400 (Bad Request) if the thumbsDTO is not valid,
     * or with status 500 (Internal Server Error) if the thumbsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/thumbs")
    @Timed
    public ResponseEntity<ThumbsDTO> updateThumbs(@Valid @RequestBody ThumbsDTO thumbsDTO) throws URISyntaxException {
        log.debug("REST request to update Thumbs : {}", thumbsDTO);
        if (thumbsDTO.getId() == null) {
            return createThumbs(thumbsDTO);
        }
        ThumbsDTO result = thumbsService.save(thumbsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, thumbsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /thumbs : get all the thumbs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of thumbs in body
     */
    @GetMapping("/thumbs")
    @Timed
    public List<ThumbsDTO> getAllThumbs() {
        log.debug("REST request to get all Thumbs");
        return thumbsService.findAll();
    }

    /**
     * GET  /thumbs/:id : get the "id" thumbs.
     *
     * @param id the id of the thumbsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thumbsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/thumbs/{id}")
    @Timed
    public ResponseEntity<ThumbsDTO> getThumbs(@PathVariable Long id) {
        log.debug("REST request to get Thumbs : {}", id);
        ThumbsDTO thumbsDTO = thumbsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(thumbsDTO));
    }

    /**
     * DELETE  /thumbs/:id : delete the "id" thumbs.
     *
     * @param id the id of the thumbsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/thumbs/{id}")
    @Timed
    public ResponseEntity<Void> deleteThumbs(@PathVariable Long id) {
        log.debug("REST request to delete Thumbs : {}", id);
        thumbsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
