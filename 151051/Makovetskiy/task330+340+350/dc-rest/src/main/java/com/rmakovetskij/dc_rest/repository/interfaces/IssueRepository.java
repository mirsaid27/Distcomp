package com.rmakovetskij.dc_rest.repository.interfaces;

import com.rmakovetskij.dc_rest.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IssueRepository extends JpaRepository <Issue, Long> {
}
