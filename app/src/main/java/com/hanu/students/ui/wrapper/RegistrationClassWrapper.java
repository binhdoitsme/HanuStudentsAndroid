package com.hanu.students.ui.wrapper;

import com.hanu.students.model.RegistrationClass;

public class RegistrationClassWrapper {
    private final RegistrationClass registrationClass;
    private final boolean initialState;
    private boolean currentState;
    private boolean isDisabled;

    public RegistrationClassWrapper(RegistrationClass c, boolean initialState) {
        registrationClass = c;
        this.initialState = initialState;
        this.currentState = initialState;
    }

    public RegistrationClass getRegistrationClass() {
        return registrationClass;
    }

    public boolean getInitialState() {
        return initialState;
    }

    public boolean getCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean currentState) {
        this.currentState = currentState;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    @Override
    public String toString() {
        return "RegistrationClassWrapper{" +
                "registrationClass=" + registrationClass +
                ", initialState=" + initialState +
                ", currentState=" + currentState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationClassWrapper that = (RegistrationClassWrapper) o;
        return initialState == that.initialState &&
                currentState == that.currentState &&
                registrationClass.equals(that.registrationClass);
    }
}
