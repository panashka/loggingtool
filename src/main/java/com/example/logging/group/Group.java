package com.example.logging.group;

import java.nio.file.Path;
import java.util.Map;

public abstract class Group {
    public abstract Map<String, Long> group(Path path);
}
