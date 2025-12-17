package org.mathieu.cleanrmapi.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.local.CharacterLocal
import org.mathieu.cleanrmapi.data.local.LocationLocal
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.local.RealmDatabase
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.LocationApi
import org.mathieu.cleanrmapi.data.remote.createHttpClient
import org.mathieu.cleanrmapi.data.repositories.CharacterRepositoryImpl
import org.mathieu.cleanrmapi.data.repositories.LocationRepositoryImpl
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.domain.repositories.LocationRepository

//https://rickandmortyapi.com/documentation/#rest
private const val RMAPI_URL = "https://rickandmortyapi.com/api/"

val dataModule = module {

    single<HttpClient> {
        createHttpClient(
            baseUrl = RMAPI_URL
        )
    }

    single<RealmDatabase> { RMDatabase() }

    single { CharacterLocal(get()) }
    single { LocationLocal(get()) }

    single { CharacterApi(get()) }
    single { LocationApi(get()) }

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get()) }
    single<LocationRepository> { LocationRepositoryImpl(get(),get(), get()) }

}