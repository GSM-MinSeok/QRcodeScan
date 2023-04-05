package com.example.qrcodescan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.qrcodescan.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnQrScan.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setPrompt("QR 코드를 스캔하여 주세요")
            integrator.setCameraId(0)
            integrator.setBeepEnabled(true)
            integrator.setBarcodeImageEnabled(false)
            integrator.setOrientationLocked(false)
            integrator.initiateScan()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        Log.d("testt", "QR 코드 체크")

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "scanned" + result.contents, Toast.LENGTH_LONG).show()
                Log.d("TTT", "QR 코드 URL:${result.contents}")

                binding.webView.settings.javaScriptEnabled = true
                binding.webView.webViewClient = WebViewClient()

                binding.webView.loadUrl(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}