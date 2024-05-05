package com.example.rv.impl.editor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class EditorRepositoryTest {

    EditorRepository underTestEditorRepository = new EditorRepository();


    @Test
    void checkingOptionalToEditor(){
        Editor respTo = new Editor(BigInteger.ONE, "miki", "123", "me", "me");

        Editor ed = underTestEditorRepository.save(respTo);

        Assertions.assertNotNull(ed);
        Assertions.assertEquals(ed.getId(), respTo.getId());
    }

}