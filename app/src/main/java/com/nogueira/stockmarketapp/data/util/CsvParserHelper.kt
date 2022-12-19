package com.nogueira.stockmarketapp.data.util

import com.nogueira.stockmarketapp.data.model.DailyInfoResponse
import com.nogueira.stockmarketapp.data.model.StockBaseInfoResponse
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader

class CsvParserHelper {
     suspend fun parseStockList(stream: InputStream) : List<StockBaseInfoResponse> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader.readAll()
                .drop(1)
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    val ipoDate = line.getOrNull(4)
                    StockBaseInfoResponse(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null,
                        ipoDate = ipoDate ?: return@mapNotNull null,
                    )
                }.also {
                    csvReader.close()
                }
        }
    }
    suspend fun parseHistoricList(stream: InputStream) : List<DailyInfoResponse> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader.readAll()
                .drop(1)
                .mapNotNull { line ->
                    val date = line.getOrNull(0)
                    val closeValue = line.getOrNull(4)
                    DailyInfoResponse(
                        date = date ?: return@mapNotNull null,
                        closeValue = closeValue ?: return@mapNotNull null,
                    )
                }.also {
                    csvReader.close()
                }
        }
    }
}