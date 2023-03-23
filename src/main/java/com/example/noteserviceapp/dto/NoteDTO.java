package com.example.noteserviceapp.dto;

import com.example.noteserviceapp.domain.Note;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
public class NoteDTO {
    private String id;
    private String content;
    private Set<String> likedBy;
    private LocalDateTime createdDate;

    public static NoteDTO of(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setContent(note.getContent());
        dto.setLikedBy(note.getLikedBy());
        dto.setCreatedDate(note.getCreatedDate());
        return dto;
    }
}
