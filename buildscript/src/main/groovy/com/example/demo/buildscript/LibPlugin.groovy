package com.example.demo.buildscript

import com.example.demo.buildscript.plugin.JavaPlugin;
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.configure project, {
            apply plugin: JavaPlugin
        }
    }
}
