package com.rmakovetskij.dc_rest.repository.interfaces;

import com.rmakovetskij.dc_rest.model.Editor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorRepository extends JpaRepository <Editor, Long> {
}
