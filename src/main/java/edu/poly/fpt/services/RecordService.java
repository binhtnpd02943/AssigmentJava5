package edu.poly.fpt.services;

import edu.poly.fpt.models.Record;
import edu.poly.fpt.models.Staff;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordService {
    Record save(Record record);

    List<Staff> findAllStaffs();


    @Query("Select r.staff.id, r.staff.name, r.staff.depart.name," +
            "Sum(case when r.type = true then 1 else 0 end), " +
            "Sum(case when r.type = false then 1 else 0 end) " +
            "From Record r Group by r.staff.id, r.staff.name")
    List<Object[]> getListResultStaffs();

    @Query("Select r.staff.depart.name, " +
            "Sum(case when r.type = true then 1 else 0 end), " +
            "Sum(case when r.type = false then 1 else 0 end) " +
            "From Record r Group by r.staff.depart.name")
    List<Object[]> getListResultDeparts();

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
}
