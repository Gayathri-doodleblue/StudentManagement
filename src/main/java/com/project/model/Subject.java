package com.project.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name="subject")
public class Subject implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
      private int subjectId;
	@Column(name="subjectName")
	private String subjectName;
	
	
	
	public Subject(int subjectId, String subjectName) {
		super();
		this.subjectId = subjectId;
		this.subjectName = subjectName;
	}



	public Subject(List<ReportCard> reportCard) {
		super();
		this.reportCard = reportCard;
	}



	//@JsonBackReference
	@JsonIgnore
	@OneToMany(mappedBy = "subject",fetch = FetchType.LAZY)
	private List<ReportCard> reportCard;
	
}
