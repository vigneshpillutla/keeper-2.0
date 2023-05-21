package com.keeper.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String content;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "username",referencedColumnName = "username")
    private User user;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(int id, String title, String content) {
        this(title,content);
        this.id = id;
    }
}
