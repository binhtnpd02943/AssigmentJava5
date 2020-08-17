package edu.poly.fpt.repositories;

import edu.poly.fpt.models.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long>, PagingAndSortingRepository<Staff, Long> {

}
