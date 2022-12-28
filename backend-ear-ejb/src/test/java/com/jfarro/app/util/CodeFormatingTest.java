package com.jfarro.app.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CodeFormatingTest {

    @Test
    void userCode() {
        long cant = 5;
        String code = "USU-" + String.format("%6d", cant).replace(" ", "0");
        System.out.println(code);
    }
}