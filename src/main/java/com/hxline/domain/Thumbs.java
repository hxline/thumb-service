package com.hxline.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Thumbs.
 */
@Entity
@Table(name = "thumbs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Thumbs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "thread_id", nullable = false)
    private String thread_id;

    @NotNull
    @Column(name = "thumb_up", nullable = false)
    private Integer thumb_up;

    @NotNull
    @Column(name = "thumb_down", nullable = false)
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

    public Thumbs thread_id(String thread_id) {
        this.thread_id = thread_id;
        return this;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public Integer getThumb_up() {
        return thumb_up;
    }

    public Thumbs thumb_up(Integer thumb_up) {
        this.thumb_up = thumb_up;
        return this;
    }

    public void setThumb_up(Integer thumb_up) {
        this.thumb_up = thumb_up;
    }

    public Integer getThumb_down() {
        return thumb_down;
    }

    public Thumbs thumb_down(Integer thumb_down) {
        this.thumb_down = thumb_down;
        return this;
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
        Thumbs thumbs = (Thumbs) o;
        if (thumbs.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thumbs.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Thumbs{" +
            "id=" + id +
            ", thread_id='" + thread_id + "'" +
            ", thumb_up='" + thumb_up + "'" +
            ", thumb_down='" + thumb_down + "'" +
            '}';
    }
}
