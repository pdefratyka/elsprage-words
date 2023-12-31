package com.elsprage.words.persistance.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "packets")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Packet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "value_language_id")
    private Long valueLanguageId;
    @Column(name = "translation_language_id")
    private Long translationLanguageId;
    @ManyToOne
    @JoinColumn(name = "translation_language_id", nullable = false, insertable = false, updatable = false)
    private Language translationLanguage;
    @ManyToOne
    @JoinColumn(name = "value_language_id", nullable = false, insertable = false, updatable = false)
    private Language valueLanguage;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "packets_words",
            joinColumns = {@JoinColumn(name = "packet_id", referencedColumnName = "id"),},
            inverseJoinColumns = {@JoinColumn(name = "word_id", referencedColumnName = "id")}
    )
    private Set<Word> words;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void beforeSave() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        createdAt = LocalDateTime.now();
    }

}

