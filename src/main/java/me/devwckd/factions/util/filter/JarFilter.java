package me.devwckd.factions.util.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileFilter;

/**
 * @author devwckd
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JarFilter implements FileFilter {

    private static final JarFilter INSTANCE = new JarFilter();

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(".jar");
    }

    public static JarFilter getInstance() {
        return INSTANCE;
    }

}
