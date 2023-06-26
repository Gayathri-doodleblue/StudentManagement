package com.project.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.model.Classes;
import com.project.model.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCardSubjectDto {
    private int subjectId;
    
    private Long marks;
}
