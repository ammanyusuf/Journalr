package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Journalr.com.model.Reviewer;
import java.util.List;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
}