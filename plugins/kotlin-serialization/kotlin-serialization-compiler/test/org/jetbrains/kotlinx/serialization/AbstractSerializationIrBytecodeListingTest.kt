/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlinx.serialization

import junit.framework.TestCase
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.codegen.AbstractAsmLikeInstructionListingTest
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.JVMConfigurationKeys
import org.jetbrains.kotlinx.serialization.compiler.extensions.SerializationComponentRegistrar
import java.io.File

abstract class AbstractSerializationIrBytecodeListingTest : AbstractAsmLikeInstructionListingTest() {
    private val runtimeLibraryPath = getSerializationLibraryRuntimeJar()

    fun testRuntimeLibraryExists() {
        TestCase.assertNotNull(
            "kotlinx-serialization runtime library is not found. Make sure it is present in test classpath",
            runtimeLibraryPath
        )
    }

    override fun getExpectedTextFileName(wholeFile: File): String {
        return wholeFile.nameWithoutExtension + ".ir.txt"
    }

    override fun updateConfiguration(configuration: CompilerConfiguration) {
        configuration.put(JVMConfigurationKeys.IR, true)
    }

    override fun setupEnvironment(environment: KotlinCoreEnvironment) {
        SerializationComponentRegistrar.registerExtensions(environment.project)
        environment.updateClasspath(listOf(JvmClasspathRoot(runtimeLibraryPath!!)))
    }
}