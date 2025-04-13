import Foundation
import ComposeApp

func fetchData<T>(
    source: @escaping () async throws -> T,
    onResult: @escaping (FetchResult<T>) -> Void
) {
    Task {
        do {
            let result = try await source()
            DispatchQueue.main.async { onResult(.success(result)) }
        } catch {
            DispatchQueue.main.async { onResult(.failure(error)) }
        }
    }
}

/// As long as the source manages completions, we do not have to cancel any subscription, however we handle it anyway.
func collectData<T>(
    source: @escaping () async throws -> CommonFlow<T>,
    onResult: @escaping (FetchResult<T>) -> Void
) {
    
    Task {
    
        var disposeBag = DisposeBag()
        
        do {
            try await source().collect { (result: T) in
                DispatchQueue.main.async { onResult(.success(result)) }
            }.add(to: disposeBag)
            
        } catch {
            DispatchQueue.main.async { onResult(.failure(error)) }
            disposeBag.dispose()
        }
        
    }
    
}

enum FetchResult<T> {
    
    var valueOrNil: T? {
        if case .success(let value) = self { return value }
        return nil
    }
    
    func isSuccess() -> Bool {
        if case .success = self { return true }
        return false
    }
    
    case success(_ value: T)
    
    case failure(_ error: Error)
    
    func onSuccess(completion: (T) -> Void) {
        
        if case .success(let value) = self { completion(value) }
        
    }
    
    func onFailure(completion: (Error) -> Void) {
        
        if case .failure(let error) = self { completion(error) }
        
    }
    
}
