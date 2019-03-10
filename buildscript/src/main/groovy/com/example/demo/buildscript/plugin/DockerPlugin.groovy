package com.example.demo.buildscript.plugin

import com.palantir.gradle.docker.PalantirDockerPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.configure project, {
            apply plugin: PalantirDockerPlugin

            docker {
                name project.name
                tag "Latest", "demo/${project.name}:${project.version}"
                dockerfile project.resources.text.fromUri(getClass().getClassLoader().getResource("META-INF/gradle-plugins/docker/Dockerfile")).asFile()
                files tasks.distTar.outputs
                buildArgs([
                        APP_NAME: project.name,
                        APP_VERSION: project.version
                ])
            }

            project.tasks["docker"].dependsOn "distTar"
        }
    }
}
