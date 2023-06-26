package com.project.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.core.util.Json;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.ParameterMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="student")
@NamedStoredProcedureQuery(
	    name = "getByStudents",
	    procedureName = "getStudents",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "studentIds", type = String.class),
	        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "studentDetails", type = String.class)
	    }
	)
public class Student implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
      private int studentId;
	@Column(name="firstName")
	private String firstName;
	@Column(name="lastName")
	private String lastName;
	@Column(name="address")
	private String address;
	@Column(name="phoneNumber")
	private Long phoneNumber;  
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id")
    private Classes classes;
	
	
	@OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
	private List<ReportCard> reportCard;
	@Transient
    private Integer totalMarks;

    public Integer getTotalMarks() {
        int totalMarks = 0;
        if (reportCard != null) {
            for (ReportCard card : reportCard) {
                totalMarks += card.getMarks();
            }
        }
        return totalMarks;
    }
//    
}
