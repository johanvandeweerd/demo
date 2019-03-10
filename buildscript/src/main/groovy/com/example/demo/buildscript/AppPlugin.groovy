package com.example.demo.buildscript

import com.example.demo.buildscript.plugin.DockerPlugin
import com.example.demo.buildscript.plugin.JavaPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin

class AppPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.configure project, {
            apply plugin: ApplicationPlugin
            apply plugin: JavaPlugin
            apply plugin: DockerPlugin
        }
    }
}
