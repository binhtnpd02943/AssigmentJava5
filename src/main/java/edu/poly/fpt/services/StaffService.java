package edu.poly.fpt.services;

import edu.poly.fpt.models.Depart;
import edu.poly.fpt.models.Record;
import edu.poly.fpt.models.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StaffService {

    List<Depart> findAllDeparts();

    List<Staff> findAll();

    Staff save(Staff entity);

    Optional<Staff> findById(Long id);

    boolean existsById(Long id);

    long count();

    void deleteById(Long id);

    void delete(Staff staff);

    Page<Staff> findAll(Pageable pageable);

    List<Record> getRecordsByStaffIdOrderByDate(Long id);

    List<int[]> getCountTypeStaff(Long id);
}
