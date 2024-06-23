package org.example.service.validator;

public interface Validator<T> {
    void validate(T object);
}
