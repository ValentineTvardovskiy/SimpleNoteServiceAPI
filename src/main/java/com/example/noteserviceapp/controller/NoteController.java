package com.example.noteserviceapp.controller;

import com.example.noteserviceapp.domain.Note;
import com.example.noteserviceapp.dto.NoteDTO;
import com.example.noteserviceapp.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<NoteDTO> sortedNotes = noteService.findAllByOrderByCreatedDateDesc().stream()
                .map(NoteDTO::of)
                .collect(Collectors.toList());
        return new ResponseEntity<>(sortedNotes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO note) {
        Note createdNote = noteService.createNote(note.getContent());
        return new ResponseEntity<>(NoteDTO.of(createdNote), HttpStatus.CREATED);
    }

    // не зовсім зрозумів умови щодо цього "CRUD для користувача (отримати список, створити, відредагувати, видалити)"
    // тому просто додав умовний ендпоінт
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable String id) {
        Optional<Note> optionalNote = noteService.getNoteById(id);
        return optionalNote
                .map(note -> new ResponseEntity<>(NoteDTO.of(note), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // не зовсім зрозумів умови щодо цього "CRUD для користувача (отримати список, створити, відредагувати, видалити)"
    // тому просто додав умовний ендпоінт
    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> editNoteById(@PathVariable String id, @RequestBody NoteDTO note) {
        Optional<Note> optionalNote = noteService.getNoteById(id);
        if (optionalNote.isPresent()) {
            Note updatedNote = noteService.editNote(optionalNote.get(), note.getContent());
            return new ResponseEntity<>(NoteDTO.of(updatedNote), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // не зовсім зрозумів умови щодо цього "CRUD для користувача (отримати список, створити, відредагувати, видалити)"
    // тому просто додав умовний ендпоінт
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNoteById(@PathVariable String id) {
        Optional<Note> optionalNote = noteService.getNoteById(id);
        if (optionalNote.isPresent()) {
            noteService.deleteNote(optionalNote.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<NoteDTO> likeNote(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Note> optionalNote = noteService.getNoteById(id);
        if (optionalNote.isPresent()) {
            Note updatedNote = noteService.addLike(optionalNote.get(), auth.getName());
            return new ResponseEntity<>(NoteDTO.of(updatedNote), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<NoteDTO> unlikeNote(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Note> optionalNote = noteService.getNoteById(id);
        if (optionalNote.isPresent()) {
            Note updatedNote = noteService.unlikeNote(optionalNote.get(), auth.getName());
            return new ResponseEntity<>(NoteDTO.of(updatedNote), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}