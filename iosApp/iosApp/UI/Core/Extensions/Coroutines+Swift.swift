//
//  Coroutines+Swift.swift
//  iOS
//
//  Created by Vincent Lemonnier on 07/09/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import ComposeApp

typealias Flow = Kotlinx_coroutines_coreFlow
typealias StateFlow = Kotlinx_coroutines_coreStateFlow
typealias Job = Kotlinx_coroutines_coreJob

extension Flow {
    
    func collect<T>(onEach: @escaping (T) -> Void) -> Job {
        return ViewModelKt.collect(self) { anyValue in
            if let value = anyValue as? T {
                onEach(value)
            }
        }
    }
    
}

extension Job {
    func add(to bag: DisposeBag) {
        bag.add(job: self)
    }
}

class DisposeBag {
    private var container = [Job]()
    
    func add(job: Job) {
        container.append(job)
    }
    
    func dispose() {
        container.forEach {
            $0.cancel(cause: nil)
        }
        container = []
    }
}
