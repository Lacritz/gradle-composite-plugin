package com.lacritz.examples.gradle

import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.testing.Test

/**
 * Used by @see com.lacritz.examples.gradle.CompositePlugin. Adds a
 * IntegrationTest task which can be used to run integration tests without
 * touching unit tests.
 * Adds a unique source set for integration tests. Dependencies can be
 * declared isolated for integration tests.
 */
class IntegrationTest extends Test {

    @TaskAction
    def test() {
        def extension = (SourceSetContainer) project.getExtensions().findByName('sourceSets')
        setTestClassesDirs(extension.getByName(CompositePlugin.SOURCE_INTEGRATION_TEST).output.classesDirs)
        setClasspath(extension.getByName(CompositePlugin.SOURCE_INTEGRATION_TEST).runtimeClasspath)
    }
}
