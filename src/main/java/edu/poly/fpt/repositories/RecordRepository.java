package edu.poly.fpt.repositories;

import edu.poly.fpt.models.Record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("Select r.staff.id, r.staff.name, r.staff.depart.name," +
            "Sum(case when r.type = true then 1 else 0 end), " +
            "Sum(case when r.type = false then 1 else 0 end) " +
            "From Record r Group by r.staff.id, r.staff.name")
    List<Object[]> getListResultStaffs();

    @Query(value = "SELECT s.Id, s.Name StaffName, d.Name DepartName," +
            "(SUM(CASE WHEN r.Type = TRUE THEN 1 ELSE 0 END) - SUM(CASE WHEN r.Type = FALSE THEN 1 ELSE 0 END)) Total " +
            "FROM Records r JOIN Staffs s "+
            "ON r.staff_id = s.id "+
            "JOIN Departs d "+
            "ON d.id = s.depart_id "+
            "GROUP BY s.Id "+
            "ORDER BY Total Desc "+
            "LIMIT 10", nativeQuery = true)
    List<Object[]> getTopResultStaffs();

    @Query("Select r.staff.depart.name, "+
            "Sum(case when r.type = true then 1 else 0 end), " +
            "Sum(case when r.type = false then 1 else 0 end) " +
            "From Record r Group by r.staff.depart.name")
    List<Object[]> getListResultDeparts();

    List<Record> getRecordsByStaffIdOrderByDate(Long id);

    @Query("Select Sum(case when r.type = true then 1 else 0 end), " +
            "Sum(case when r.type = false then 1 else 0 end) " +
            "From Record r Where r.staff.id = :id")
    List<int[]> getCountTypeStaff(@Param("id")Long id);


}
