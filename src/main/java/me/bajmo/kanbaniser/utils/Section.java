package me.bajmo.kanbaniser.utils;

import lombok.Getter;

@Getter
public enum Section {
    ToDo("To do"),
    Doing("Doing"),
    Done("Done");

    private final String value;

    Section(String value) {
        this.value = value;
    }

}
