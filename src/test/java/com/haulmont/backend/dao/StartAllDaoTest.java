package com.haulmont.backend.dao;

import com.haulmont.backend.RecipePriority;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
       DoctorDaoTest.class,
       PatientDaoTest.class,
       RecipeDaoTest.class,
        RecipePriorityDaoTest.class})
public class StartAllDaoTest {}