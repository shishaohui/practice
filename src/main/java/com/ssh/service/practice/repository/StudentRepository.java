package com.ssh.service.practice.repository;

import com.ssh.service.practice.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {


	//native sql 自动分页查询
/*
	@Query(value = "select DISTINCT d.* from d2_surgery d , d2_surgery_detail e where d.enabled=true and d.tenant_id=:tenantId and d.id = e.surgery_id and e"
		+ ".doctor_id=:doctorId and d.status =:status ORDER BY d.surgery_date \n#pageable\n",
		countQuery = "SELECT count(DISTINCT d.id) from d2_surgery d , d2_surgery_detail e where d.enabled=true and d.tenant_id=:tenantId and d.id = e.surgery_id and e"
			+ ".doctor_id=:doctorId and d.status =:status ",
		nativeQuery = true)
	Page<Surgery> loadAll(@Param("tenantId") Integer tenantId,@Param("doctorId") Integer doctorId,@Param("status") Integer status,Pageable pageable);
*/


}
