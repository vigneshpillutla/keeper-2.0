package com.keeper.api.dao;

import com.keeper.api.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Integer> {

    List<Note> findByUser_Username(String username);
    @Modifying
    @Query("update Note n set n.title = ?1, n.content = ?2 where n.id = ?3")
    void setNoteById(String title,String content,int id);

    void deleteById(int id);

}
