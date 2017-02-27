package com.hxline.repository;

import com.hxline.domain.Thumbs;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Thumbs entity.
 */
@SuppressWarnings("unused")
public interface ThumbsRepository extends JpaRepository<Thumbs,Long> {

}
