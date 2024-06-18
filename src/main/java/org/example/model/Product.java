package org.example.model;

import java.util.List;

public record Product(String name, List<Extra> extras) {
}
