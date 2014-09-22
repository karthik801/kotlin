package org.jetbrains.jet.plugin.quickfix.createFromUsage

import org.jetbrains.jet.plugin.quickfix.JetSingleIntentionActionFactory
import org.jetbrains.jet.lang.diagnostics.Diagnostic
import com.intellij.codeInsight.intention.IntentionAction
import org.jetbrains.jet.lang.diagnostics.DiagnosticFactory
import org.jetbrains.jet.lang.diagnostics.Errors
import org.jetbrains.jet.lang.types.Variance
import org.jetbrains.jet.plugin.quickfix.QuickFixUtil
import org.jetbrains.jet.lang.psi.JetForExpression
import org.jetbrains.jet.lang.types.lang.KotlinBuiltIns

object CreateHasNextFunctionActionFactory : JetSingleIntentionActionFactory() {
    override fun createAction(diagnostic: Diagnostic): IntentionAction? {
        val diagnosticWithParameters = DiagnosticFactory.cast(diagnostic, Errors.HAS_NEXT_MISSING, Errors.HAS_NEXT_FUNCTION_NONE_APPLICABLE)
        val ownerType = TypeOrExpressionThereof(diagnosticWithParameters.getA(), Variance.IN_VARIANCE)

        val forExpr = QuickFixUtil.getParentElementOfType(diagnostic, javaClass<JetForExpression>()) ?: return null
        val returnType = TypeOrExpressionThereof(KotlinBuiltIns.getInstance().getBooleanType(), Variance.OUT_VARIANCE)
        return CreateFunctionFromUsageFix(forExpr, ownerType, "hasNext", returnType)
    }
}