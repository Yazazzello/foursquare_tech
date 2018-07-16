package com.yazazzello.adyen.features.foursquare

import android.location.Location
import com.nhaarman.mockito_kotlin.*
import com.yazazzello.adyen.di.Params
import com.yazazzello.adyen.features.di.testApp
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mockito
import kotlin.test.assertEquals

class FoursquarePresenterTest : KoinTest {

    val view: FoursquareContract.View = Mockito.mock(FoursquareContract.View::class.java)
    val presenter: FoursquareContract.Presenter by inject { mapOf(Params.FOURSQUARE_VIEW to view) }

    @Before
    fun before() {
        StandAloneContext.startKoin(testApp)
    }

    @After
    fun after() {
        StandAloneContext.closeKoin()
    }

    @Test
    fun `testLoadObjects happy flow`() {
        val location = mock<Location>()
        presenter.loadObjects(location, "good")
        verify(view, times(2)).flipProgress(any())
        verify(view).displayVenues(com.nhaarman.mockito_kotlin.check {
            assertEquals(10, it.size)
            assertEquals("Cannibale Royale", it[0].name)
            assertEquals("Ruysdaelkade 149", it[0].location.address)
        })
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `testLoadObjects error flow`() {
        val location = mock<Location>()
        presenter.loadObjects(location, "")
        verify(view, times(2)).flipProgress(any())
        verify(view).showError(eq("something bad"))
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `testLoadPhotos happy flow`() {
        presenter.loadPhoto("good")
        verify(view, times(2)).flipProgress(any())
        verify(view).onPhotoUrlLoaded(com.nhaarman.mockito_kotlin.check {
            assertEquals("https://igx.4sqi.net/img/general/500x500/1022386_wEd7VXnWzp5lajvRLtAPunoiufDSIq8PMEtEuIH2Tzg.jpg", it)
        })
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `testLoadPhotos error flow`() {
        presenter.loadPhoto("")
        verify(view, times(2)).flipProgress(any())
        verify(view).showError(eq("something bad"))
        verifyNoMoreInteractions(view)
    }
}