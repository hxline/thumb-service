package com.hxline.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Thumbs entity.
 */
public class ThumbsDTO implements Serializable {

    private Long id;

    @NotNull
    private String thread_id;

    @NotNull
    private Integer thumb_up;

    @NotNull
    private Integer thumb_down;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }
    public Integer getThumb_up() {
        return thumb_up;
    }

    public void setThumb_up(Integer thumb_up) {
        this.thumb_up = thumb_up;
    }
    public Integer getThumb_down() {
        return thumb_down;
    }

    public void setThumb_down(Integer thumb_down) {
        this.thumb_down = thumb_down;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThumbsDTO thumbsDTO = (ThumbsDTO) o;

        if ( ! Objects.equals(id, thumbsDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThumbsDTO{" +
            "id=" + id +
            ", thread_id='" + thread_id + "'" +
            ", thumb_up='" + thumb_up + "'" +
            ", thumb_down='" + thumb_down + "'" +
            '}';
    }
}
