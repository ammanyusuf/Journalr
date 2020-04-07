package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import Journalr.com.model.Editor;
//import Journalr.com.model.User;
//import java.util.List;
//import java.util.Optional;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Integer> {

}