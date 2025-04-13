package org.mathieu.cleanrmapi.di.extensions

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.ObjCObject
import kotlinx.cinterop.ObjCProtocol
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools

@OptIn(BetaInteropApi::class)
fun <T> get(type: ObjCObject, qualifier: Qualifier? = null, parameters: ParametersDefinition? = null): T = KoinPlatformTools.defaultContext().get().get(
    clazz = when (type) {
        is ObjCProtocol -> getOriginalKotlinClass(type)!!
        is ObjCClass -> getOriginalKotlinClass(type)!!
        else -> error("Cannot convert $type to KClass<*>")
    },
    qualifier,
    parameters
)