package com.example.noteserviceapp.service;

import com.example.noteserviceapp.domain.Note;
import com.example.noteserviceapp.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findAllByOrderByCreatedDateDesc() {
        return noteRepository.findAllByOrderByCreatedDateDesc();
    }

    public Note createNote(String content) {
        Note note = new Note(content);
        return noteRepository.save(note);
    }

    public Optional<Note> getNoteById(String id) {
        return noteRepository.findById(id);
    }

    public Note editNote(Note note, String newContent) {
        note.setContent(newContent);
        return noteRepository.save(note);
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    public Note addLike(Note note, String username) {
        note.addLike(username);
        return noteRepository.save(note);
    }

    public Note unlikeNote(Note note, String username) {
        note.removeLike(username);
        return noteRepository.save(note);
    }

}