package com.project.model;

import java.io.Serializable;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name="classes")
public class Classes implements Serializable {
	
	
	public Classes(int classId, String className) {
		super();
		this.classId = classId;
		this.className = className;
	}
	
	
	public Classes(List<Student> student) {
		super();
		this.student = student;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
      private int classId;
	@Column(name="className")
	private String className;
	//@JsonBackReference
	@JsonIgnore
	@OneToMany(mappedBy = "classes",fetch = FetchType.LAZY)
	private List<Student> student;
	}
