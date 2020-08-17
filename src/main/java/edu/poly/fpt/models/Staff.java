package edu.poly.fpt.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "staffs")
public class Staff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50)
    private String name;

    @Column()
    private Boolean gender;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthday;

    @Column(length = 100)
    private String photo;

    @Column(length = 50)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column
    private String address;

    @Column
    private String about;

    @Column()
    private Float salary;

    @Column()
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departId")
    private Depart depart;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private Set<Record> records;

    public Staff() {
    }

    public Staff(long id, String name, String photo, String email, String phone, String notes,
            Boolean gender, Float salary, Date birthday, Depart depart){
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.gender = gender;
        this.email = email;
        this.salary = salary;
        this.phone = phone;
        this.notes = notes;
        this.birthday = birthday;
        this.depart = depart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }
}
