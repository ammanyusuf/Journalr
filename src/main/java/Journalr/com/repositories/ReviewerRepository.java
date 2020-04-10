package Journalr.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Journalr.com.model.Reviewer;
import Journalr.com.model.User;

import java.util.List;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {

}