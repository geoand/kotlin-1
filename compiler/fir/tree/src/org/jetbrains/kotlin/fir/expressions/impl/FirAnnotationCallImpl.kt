/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.expressions.impl

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall
import org.jetbrains.kotlin.fir.transformSingle
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.visitors.FirTransformer

class FirAnnotationCallImpl(
    session: FirSession,
    psi: PsiElement?,
    override val useSiteTarget: AnnotationUseSiteTarget?,
    override var annotationTypeRef: FirTypeRef
) : FirAnnotationCall(session, psi) {
    override fun <D> transformChildren(transformer: FirTransformer<D>, data: D): FirElement {
        annotationTypeRef = annotationTypeRef.transformSingle(transformer, data)

        return super.transformChildren(transformer, data)
    }
}