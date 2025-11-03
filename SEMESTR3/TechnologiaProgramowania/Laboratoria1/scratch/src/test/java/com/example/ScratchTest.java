package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ScratchTest {
    @Test
    void test() {
        Scratch app = new Scratch();
        assertEquals("Hello, world!", app.greet());
    }
}
