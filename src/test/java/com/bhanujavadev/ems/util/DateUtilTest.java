package com.bhanujavadev.ems.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void testNow() {

        LocalDateTime before = LocalDateTime.now();

        LocalDateTime now = DateUtil.now();

        LocalDateTime after = LocalDateTime.now();

        assertNotNull(now);
        assertFalse(now.isBefore(before));
        assertFalse(now.isAfter(after));
    }

    @Test
    void testPrivateConstructor() throws Exception {

        Constructor<DateUtil> constructor =
                DateUtil.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        DateUtil instance = constructor.newInstance();

        assertNotNull(instance);
    }
}