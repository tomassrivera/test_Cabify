package com.android.cabifymarketplace.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.android.cabifymarketplace.data.datasource.api.CabifyMarketplaceService
import com.android.cabifymarketplace.data.datasource.db.DbDataSource
import com.android.cabifymarketplace.data.datasource.db.ProductDAO
import com.android.cabifymarketplace.utils.MockObjects
import com.android.cabifymarketplace.utils.enqueueResponse
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
class CabifyMarketplaceRepositoryImpTest {

    private val mockWebServer = MockWebServer()

    private val cabifyMarketplaceService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CabifyMarketplaceService::class.java)

    private lateinit var database: DbDataSource
    private lateinit var productDAO: ProductDAO
    private lateinit var newsRepository: CabifyMarketplaceRepositoryImp

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            DbDataSource::class.java
        ).allowMainThreadQueries().build()
        productDAO = database.productDAO()
        newsRepository = CabifyMarketplaceRepositoryImp(cabifyMarketplaceService, productDAO)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        database.clearAllTables()
        database.close()
    }

    @Test
    fun `Product list response is correct`() {
        mockWebServer.enqueueResponse("product_list_response.json")
        runBlocking {
            val productsList = newsRepository.getProducts()
            Assert.assertEquals(3, productsList.products.size)
        }
    }

    @Test
    fun `Discounts response is correct`() {
        mockWebServer.enqueueResponse("discounts_response.json")
        runBlocking {
            val discounts = newsRepository.getDiscounts()

            Assert.assertEquals(2, discounts.size)
        }
    }

    @Test
    fun `Save product in the DB and get quantity of it is correct`() {
        runBlocking {
            val product = MockObjects.getProductOrder()
            productDAO.insertProduct(product)

            Assert.assertNotNull(productDAO.getQuantityByProductCode(product.code))
            Assert.assertEquals(2, productDAO.getQuantityByProductCode(product.code))
        }
    }

    @Test
    fun `Update product quantity is correct`() {
        runBlocking {
            val product = MockObjects.getProductOrder()
            productDAO.insertProduct(product)
            productDAO.updateQuantity(product.code, 6)
            val productQuantity = productDAO.getQuantityByProductCode(product.code)

            Assert.assertNotNull(productQuantity)
            Assert.assertEquals(8, productQuantity)
        }
    }

    @Test
    fun `Get order from DB working fine`() {
        runBlocking {
            MockObjects.getOrder().forEach {
                productDAO.insertProduct(it)
            }
            val dbOrder = productDAO.getOrder()
            Assert.assertTrue(dbOrder.isNotEmpty())
            Assert.assertEquals(3, dbOrder.size)
        }
    }

    @Test
    fun `Delete product from DB working fine`() {
        runBlocking {
            val product = MockObjects.getProductOrder()
            productDAO.insertProduct(product)
            productDAO.deleteProduct(product.code)
            val productQuantity = productDAO.getQuantityByProductCode(product.code)

            Assert.assertNull(productQuantity)
        }
    }

    @Test
    fun `Delete products from DB working fine`() {
        runBlocking {
            MockObjects.getOrder().forEach {
                productDAO.insertProduct(it)
            }
            productDAO.deleteProducts()
            val dbOrder = productDAO.getOrder()

            Assert.assertTrue(dbOrder.isEmpty())
        }
    }
}
