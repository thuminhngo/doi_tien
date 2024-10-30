package com.example.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.currency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedSource = true  // Xác định đồng tiền nguồn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currencies = CurrencyConverter.exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)

        binding.sourceCurrencySpinner.adapter = adapter
        binding.targetCurrencySpinner.adapter = adapter

        // Xử lý nhập số tiền nguồn
        binding.sourceEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (selectedSource) {
                    updateConversion()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Xử lý sự kiện chọn Spinner
        binding.sourceCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                if (selectedSource) {
                    updateConversion()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.targetCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                if (!selectedSource) {
                    updateConversion()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Chọn đồng tiền nguồn hoặc đích khi người dùng nhấn vào EditText
        binding.sourceEditText.setOnFocusChangeListener { _, hasFocus ->
            selectedSource = hasFocus
        }

        binding.targetEditText.setOnFocusChangeListener { _, hasFocus ->
            selectedSource = !hasFocus
        }
    }

    private fun updateConversion() {
        val sourceAmountText = binding.sourceEditText.text.toString()
        val sourceCurrency = binding.sourceCurrencySpinner.selectedItem.toString()
        val targetCurrency = binding.targetCurrencySpinner.selectedItem.toString()

        val sourceAmount = sourceAmountText.toDoubleOrNull() ?: 0.0
        val convertedAmount = CurrencyConverter.convert(sourceAmount, sourceCurrency, targetCurrency)

        if (selectedSource) {
            binding.targetEditText.setText(convertedAmount.toString())
        } else {
            binding.sourceEditText.setText(convertedAmount.toString())
        }
    }
}
