package com.example.noteserviceapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private String content;
    private Set<String> likedBy;
    private LocalDateTime createdDate;

    public Note(String content) {
        this.content = content;
        this.likedBy = new HashSet<>();
        this.createdDate = LocalDateTime.now();
    }

    public void addLike(String username) {
        likedBy.add(username);
    }

    public void removeLike(String username) {
        likedBy.remove(username);
    }
}