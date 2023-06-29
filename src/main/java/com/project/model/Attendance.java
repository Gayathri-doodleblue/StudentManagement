package com.project.model;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
@NoArgsConstructor

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "class_name", referencedColumnName = "className")
    private Classes classes;

    @ManyToOne
    @JoinColumn(name = "student_name", referencedColumnName = "firstName")
    private Student student;

    @Column(name = "attendance_status")
    private String attendanceStatus;
    
    @Column(name = "attendance_date")
    private Date attendanceDate;

	

	public Attendance(Student student) {
		super();
		this.student = student;
	}

	public Attendance(String attendanceStatus) {
		super();
		this.attendanceStatus = attendanceStatus;
	}

	public Attendance(int id, String attendanceStatus,Date attendanceDate) {
		super();
		this.id = id;
		this.attendanceStatus = attendanceStatus;
		this.attendanceDate = attendanceDate;
	}

	public int getId() {
		return id;
	}

	

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date date) {
		this.attendanceDate = date;
	}

	public void setId(int id) {
		this.id = id;
	}


}
