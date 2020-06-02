package com.hanu.students.model.mapper;

import com.hanu.students.base.Converter;
import com.hanu.students.model.Registration;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.ui.wrapper.RegistrationClassWrapper;

public class RegistrationMapper extends Converter<RegistrationClassWrapper, Registration> {
    private static int studentId;
    public RegistrationMapper(int studentId) {
        super(RegistrationMapper::fromRegistrationClass, RegistrationMapper::fromRegistration);
        this.studentId = studentId;
    }

    private static RegistrationClassWrapper fromRegistration(Registration registration) {
        return null;
    }

    private static Registration fromRegistrationClass(RegistrationClassWrapper registrationClassWrapper) {
        RegistrationClass registrationClass = registrationClassWrapper.getRegistrationClass();
        return new Registration(registrationClass.getClassCode(), registrationClass.getCourseCode(),
                registrationClass.getSemester(), studentId);
    }
}
