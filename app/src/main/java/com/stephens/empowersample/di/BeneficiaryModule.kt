package com.stephens.empowersample.di

import android.content.Context
import com.stephens.empowersample.data.BeneficiaryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
class BeneficiaryModule {
    @Provides
    fun providesBeneficiaryRepository(@ApplicationContext context: Context): BeneficiaryRepository {
        return BeneficiaryRepository(context)
    }
}