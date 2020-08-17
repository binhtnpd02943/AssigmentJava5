package edu.poly.fpt.services;

import edu.poly.fpt.models.Depart;
import edu.poly.fpt.models.Record;
import edu.poly.fpt.models.Staff;
import edu.poly.fpt.repositories.DepartRepository;
import edu.poly.fpt.repositories.RecordRepository;
import edu.poly.fpt.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private DepartRepository departRepository;

    @Autowired
    private RecordRepository recordRepository;


    public List<Depart> findAllDeparts(){
        return departRepository.findAll();
    }

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Staff save(Staff entity) {
        return staffRepository.save(entity);
    }

    @Override
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return staffRepository.existsById(id);
    }

    @Override
    public long count() {
        return staffRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        staffRepository.deleteById(id);
    }

    @Override
    public void delete(Staff staff) {
        staffRepository.delete(staff);
    }

    public Page<Staff> findAll(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }

    public List<Record> getRecordsByStaffIdOrderByDate(Long id) {
        return recordRepository.getRecordsByStaffIdOrderByDate(id);
    }

    @Query("Select Sum(case when r.type = true then 1 else 0 end), " +
            "Sum(case when r.type = false then 1 else 0 end) " +
            "From Record r Where r.staff.id = :id")
    public List<int[]> getCountTypeStaff(Long id) {
        return recordRepository.getCountTypeStaff(id);
    }
}
