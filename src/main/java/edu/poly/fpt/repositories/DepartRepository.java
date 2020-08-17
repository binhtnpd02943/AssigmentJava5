package edu.poly.fpt.repositories;

import edu.poly.fpt.models.Depart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartRepository extends JpaRepository<Depart, Integer> {
    List<Depart> findByNameLikeOrderByName(String name);
}
