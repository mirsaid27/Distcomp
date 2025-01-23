package com.rmakovetskij.dc_rest.repository.interfaces;

import com.rmakovetskij.dc_rest.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LabelRepository extends JpaRepository <Label, Long> {
}
