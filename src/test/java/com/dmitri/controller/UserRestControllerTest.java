package com.dmitri.controller;

import lombok.extern.java.Log;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Log
public class UserRestControllerTest {

    UserRestController restController = new UserRestController();

    @Test
    public void testSimpleDefineOffset() {
        List<String> list = Arrays.asList(null, "0", "1");
        for (String item : list) {
            int actualValue = restController.defineOffset(item);
            int expectedValue = 1;
            assertEquals(expectedValue, actualValue);
        }
    }

    @Test
    public void testIncorrectDefineOffset() {
        List<String> list = Arrays.asList("x2", "j001101", "1.02");
        for (String item : list) {
            int actualValue = restController.defineOffset(item);
            int expectedValue = 1;
            assertEquals(expectedValue, actualValue);
        }
    }
    @Test
    public void testDefineOffset() {
        List<String> list = Arrays.asList("2", "11","100","10");
        for (String item : list) {
            int actualValue = restController.defineOffset(item);
            int expectedValue = Integer.parseInt(item);
            assertEquals(expectedValue, actualValue);
        }
    }
}