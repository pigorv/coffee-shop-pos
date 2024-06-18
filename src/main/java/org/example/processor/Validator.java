package org.example.processor;

public interface Validator<T> {
    void validate(T object);
}
