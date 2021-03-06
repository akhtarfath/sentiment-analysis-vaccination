package com.aplikasikaryaanakbangkit.sentiment.core.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.covid.GlobalCovidEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.covid.IDCovidItemEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.news.ArticleCovidEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.news.ArticleVaccinesEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.sentiment.*
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.teams.TeamsEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.tweet.DataItemTweetEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.tweet.TweetEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.tweet.UserItemsTweetEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.vaccination.*
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.remote.response.sentiment.TextTweet
import com.aplikasikaryaanakbangkit.sentiment.core.vo.Resource

interface SAVDataSource {
    // covid
    fun getCovidHeadlines(): LiveData<Resource<PagedList<ArticleCovidEntity>>>
    fun getVaccineNews(): LiveData<Resource<PagedList<ArticleVaccinesEntity>>>
    fun getCovidHeadlinesByUrl(url: String): LiveData<Resource<ArticleCovidEntity>>
    fun getVaccineNewsByUrl(url: String): LiveData<Resource<ArticleVaccinesEntity>>

    // team
    fun getAllTeams(): LiveData<Resource<PagedList<TeamsEntity>>>

    // tweet
    fun getAllProfile(): LiveData<Resource<List<UserItemsTweetEntity>>>
    fun getAllPost(): LiveData<Resource<List<DataItemTweetEntity>>>
    fun getAllTweet(): LiveData<List<TweetEntity>>

    //covid
    fun getAllGlobalCovid(): LiveData<Resource<GlobalCovidEntity>>
    fun getAllIDCovid(): LiveData<Resource<IDCovidItemEntity>>

    //vaksinasi
    fun getVaccination(): LiveData<Resource<VaccinationMonitoringItemEntity>>
    fun getVaccinationStepHealthHR(): LiveData<Resource<VaccinationHealthHREntity>>
    fun getVaccinationStepElderly(): LiveData<Resource<VaccinationElderlyEntity>>
    fun getVaccinationStepPublicOfficer(): LiveData<Resource<VaccinationPublicOfficerEntity>>
    fun getVaccinationCoverage(): LiveData<Resource<VaccinationCoverageEntity>>

    //sentiment
    fun getAnalysis(tweet: TextTweet): LiveData<Resource<SentimentEntity>>
    fun getNeutralAnalysis(): LiveData<NeutralAnalysisResultEntity>
    fun getPositiveAnalysis(): LiveData<ProAnalysisResultEntity>
    fun getNegativeAnalysis(): LiveData<ContraAnalysisResultEntity>
    fun getAllAnalysis(): LiveData<AllAnalysisResultEntity>
}