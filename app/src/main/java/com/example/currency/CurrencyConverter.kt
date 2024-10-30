package com.example.currency

object CurrencyConverter {
    // Tỷ giá hối đoái giả lập
    val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.92,
        "JPY" to 153.0,
        "VND" to 25280.0,
        "KRW" to 1379.0
    )

    // Hàm chuyển đổi tiền tệ
    fun convert(amount: Double, fromCurrency: String, toCurrency: String): Double {
        val fromRate = exchangeRates[fromCurrency] ?: return 0.0
        val toRate = exchangeRates[toCurrency] ?: return 0.0
        return amount * (toRate / fromRate)
    }
}
