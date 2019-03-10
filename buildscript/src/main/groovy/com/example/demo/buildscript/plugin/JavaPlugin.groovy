package com.example.demo.buildscript.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.configure project, {
            apply plugin: 'java-library'

            sourceCompatibility = 1.10
            targetCompatibility = 1.10
        }
    }
}
