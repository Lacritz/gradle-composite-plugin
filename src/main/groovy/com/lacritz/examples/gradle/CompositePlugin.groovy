package com.lacritz.examples.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSetContainer

/**
 * Example class for a CompositePlugin.
 * The Plugin adds a integrationTest task which can be used to run integration
 * tests without touching unit tests.
 * IntegrationTests inherit the implementation and testImplementation
 * dependencies, integrationTestImplementation dependencies can be declared
 * as well.
 */
class CompositePlugin implements Plugin<Project> {

    static final String SOURCE_SETS = 'sourceSets'
    static final String SOURCE_MAIN = 'main'
    static final String SOURCE_TEST = 'test'
    static final String SOURCE_INTEGRATION_TEST = 'integrationTest'
    static final String SOURCE_INTEGRATION_TEST_JAVA_DIR = 'src/integration/java'
    static final String SOURCE_INTEGRATION_TEST_RSC_DIR = 'src/integration/resources'

    /**
     * Entrypoint of the Plugin.
     * @param project
     */
    @Override
    void apply(Project project) {

        /*
         * Applying the composite Plugin (the JavaPlugin is necessary to
         * receive the 'main' and 'test' sourceSets for Java specific Code.
         */
        project.getPlugins().apply(JavaPlugin.class)

        /*
         * Create a task for integrationTests
         * @see com.lacritz.examples.gradle.IntegrationTest
         */
        project.tasks.create(SOURCE_INTEGRATION_TEST, IntegrationTest.class)

        /*
         * Get SourceSets - get 'main' and 'test' sourceSets to access their
         * sources and dependencies
         */
        def sourceSets = (SourceSetContainer) project.getExtensions().findByName(SOURCE_SETS)
        def main = sourceSets.getByName(SOURCE_MAIN)
        def test = sourceSets.getByName(SOURCE_TEST)

        // Create a new sourceSet for IntegrationTests
        def integrationTest = sourceSets.create(SOURCE_INTEGRATION_TEST)
        // Add Java SourceDir to IntegrationTest
        integrationTest.java { srcDirs = [SOURCE_INTEGRATION_TEST_JAVA_DIR] }
        // Add Resources Dir to IntegrationTest
        integrationTest.resources { srcDirs = [SOURCE_INTEGRATION_TEST_RSC_DIR] }

        /*
         * Add the 'main' and 'test' dependencies as well as the sources to
         * the integrationTest Classpath
         */
        integrationTest.compileClasspath += test.compileClasspath
        integrationTest.compileClasspath += test.output
        integrationTest.compileClasspath += main.compileClasspath
        integrationTest.compileClasspath += main.output
    }

}
