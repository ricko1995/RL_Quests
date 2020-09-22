package com.ricko.rlquests.di

import android.app.Application
import androidx.room.Room
import com.ricko.rlquests.db.QuestDao
import com.ricko.rlquests.db.QuestsDatabase
import com.ricko.rlquests.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDb(app: Application): QuestsDatabase {
        return Room.databaseBuilder(app, QuestsDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideQuestDao(db: QuestsDatabase): QuestDao {
        return db.getQuestDao()
    }

}