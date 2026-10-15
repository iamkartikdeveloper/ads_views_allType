package com.kartik.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Home extends AppCompatActivity {

    // Yahan sahi tareeke se variable declare kiya gaya hai
    private InterstitialAd mInterstitialAd;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button myButton = findViewById(R.id.bt2);
        myButton.setOnClickListener(v -> {
            if (mInterstitialAd != null) {

                mInterstitialAd.show(Home.this);
            } else {
                Log.d("TAG", "Ad is not ready.");
            }
        });
        // SDK Initialize
        new Thread(() -> {
            MobileAds.initialize(this, initializationStatus -> {});
        }).start();

        // Ad Load karein
        loadAd();
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        // Ad ID ko String "" me rakha gaya hai
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        Log.d(TAG, "Ad was loaded.");
                        mInterstitialAd = interstitialAd;

                        // Ad load hone ke baad hi callback set karna chahiye
                        setAdCallback();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

    private void setAdCallback() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d(TAG, "The ad was dismissed.");
                    mInterstitialAd = null;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.d(TAG, "The ad failed to show.");
                    mInterstitialAd = null;
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.d(TAG, "The ad was shown.");
                    // Optional: You can set mInterstitialAd to null here too so it doesn't show again
                }

                @Override
                public void onAdImpression() {
                    Log.d(TAG, "The ad recorded an impression.");
                }

                @Override
                public void onAdClicked() {
                    Log.d(TAG, "The ad was clicked.");
                }
            });
        }
    }
}