package com.example.noteserviceapp.repository;

import com.example.noteserviceapp.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findAllByOrderByCreatedDateDesc();
}