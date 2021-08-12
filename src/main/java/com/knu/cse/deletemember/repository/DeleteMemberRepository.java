package com.knu.cse.deletemember.repository;

import com.knu.cse.deletemember.domain.DeleteMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteMemberRepository extends JpaRepository<DeleteMember, Long> {
}
