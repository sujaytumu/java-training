package com.sujay.week3.service;
import com.sujay.week3.domain.Department;
import com.sujay.week3.dto.ApiDtos.*;
import com.sujay.week3.repository.DepartmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service public class DepartmentService {
 private final DepartmentRepository repo;
 public DepartmentService(DepartmentRepository r){repo=r;}
 @Transactional public DepartmentResponse create(DepartmentRequest r){Department d=repo.save(new Department(r.name().trim()));return new DepartmentResponse(d.getId(),d.getName());}
 @Transactional(readOnly=true) public List<DepartmentResponse> list(){return repo.findAll().stream().map(d->new DepartmentResponse(d.getId(),d.getName())).toList();}
}