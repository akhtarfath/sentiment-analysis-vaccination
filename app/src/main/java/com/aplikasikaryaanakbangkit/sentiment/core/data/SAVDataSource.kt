package com.aplikasikaryaanakbangkit.sentiment.core.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.covid.*
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.news.ArticleCovidEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.news.ArticleVaccinesEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.teams.TeamsEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.tweet.DataItemTweetEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.tweet.PublicMetricsTweetEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.tweet.TweetEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.tweet.UserItemsTweetEntity
import com.aplikasikaryaanakbangkit.sentiment.core.data.source.local.entity.vaccination.*
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
    fun getPublicMetrics(id: String): LiveData<Resource<PublicMetricsTweetEntity>>
    fun getAllTweet(): LiveData<List<TweetEntity>>

    //covid
    fun getConfirmed(): LiveData<Resource<ConfirmedGlobalCovidEntity>>
    fun getDeath(): LiveData<Resource<DeathGlobalCovidEntity>>
    fun getRecovered(): LiveData<Resource<RecoveredGlobalCovidEntity>>
    fun getAllGlobalCovid(): LiveData<GlobalCovidEntity>
    fun getAllIDCovid(): LiveData<Resource<IDCovidItemEntity>>

    //vaksinasi
    fun getVaccination(): LiveData<Resource<VaccinationMonitoringItemEntity>>
    fun getTahapanSDM(): LiveData<Resource<VaccinationSdmKesehatanEntity>>
    fun getTahapanLansia(): LiveData<Resource<VaccinationLansiaEntity>>
    fun getTahapanPetugas(): LiveData<Resource<VaccinationPetugasPublikEntity>>
    fun getCakupanVaccination(): LiveData<Resource<VaccinationCakupanEntity>>
}