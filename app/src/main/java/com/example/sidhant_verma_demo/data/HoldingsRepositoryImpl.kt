package com.example.sidhant_verma_demo.data

import com.example.sidhant_verma_demo.data.local.db.HoldingsDao
import com.example.sidhant_verma_demo.data.local.entity.HoldingsEntity
import com.example.sidhant_verma_demo.data.remote.HoldingsApiService
import com.example.sidhant_verma_demo.domain.HoldingsRepository
import com.example.sidhant_verma_demo.domain.model.Holding

class HoldingsRepositoryImpl(
    private val api: HoldingsApiService,
    private val dao: HoldingsDao
) : HoldingsRepository {

    override suspend fun getHoldings(): List<Holding> {
        return try {
            val response = api.getHoldings()

            val entities = response.data.userHolding.map {
                HoldingsEntity(
                    symbol = it.symbol,
                    quantity = it.quantity,
                    ltp = it.ltp,
                    avgPrice = it.avgPrice,
                    close = it.close
                )
            }
            dao.insertAll(entities)
            entities.map { it.toDomain() }
        } catch (e: Exception) {
            val local = dao.getAll()
            if (local.isNotEmpty()) {
                local.map { it.toDomain() }
            } else {
                throw e
            }
        }
    }

    private fun HoldingsEntity.toDomain() = Holding(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close
    )
}

