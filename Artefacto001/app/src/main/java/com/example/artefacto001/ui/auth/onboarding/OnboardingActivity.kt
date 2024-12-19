package com.example.artefacto001.ui.auth.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.artefacto001.R
import com.example.artefacto001.ui.auth.login.LoginActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        supportActionBar?.hide()

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val dotsIndicator: DotsIndicator = findViewById(R.id.dotsIndicator)
        val nextButton: ImageButton = findViewById(R.id.nextButton)

        val layouts = listOf(
            R.layout.onboarding_page_1,
            R.layout.onboarding_page_2,
            R.layout.onboarding_page_3
        )

        val adapter = OnboardingAdapter(layouts)
        viewPager.adapter = adapter
        dotsIndicator.setViewPager2(viewPager)

        nextButton.setOnClickListener {
            if (viewPager.currentItem < layouts.lastIndex) {
                viewPager.currentItem += 1
            } else {
                startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}
