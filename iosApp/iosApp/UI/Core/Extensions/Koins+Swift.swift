import ComposeApp

func get<T: Any>(qualifier: Koin_coreQualifier? = nil, parameters: Any...) -> T {
    var paramsHolder: (() -> Koin_coreParametersHolder)?
    if (!parameters.isEmpty) {
        paramsHolder = {
            Koin_coreParametersHolder(_values: NSMutableArray(array: parameters), useIndexedValues: nil)
        }
    }
    
    return KoinsKt.get(type: T.self, qualifier: qualifier, parameters: paramsHolder) as! T
}
