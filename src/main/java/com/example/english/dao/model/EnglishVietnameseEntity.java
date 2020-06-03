package com.example.english.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "english_vietnamese")
@Where(clause = "deleted=0")
@Setter
@Getter
public class EnglishVietnameseEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "english_vietnamese_generator")
    @SequenceGenerator(
            name = "english_vietnamese_generator",
            sequenceName = "english_vietnamese_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "new_word", columnDefinition = "TEXT")
    private String newword;

    @Column(name = "category", columnDefinition = "TEXT")
    private String category;

    @Column(name = "spelling", columnDefinition = "TEXT")
    private String spelling;

    @Column(name = "mean", columnDefinition = "TEXT")
    private String mean;

}
