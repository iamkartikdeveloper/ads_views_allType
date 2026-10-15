package com.kartik.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Rewardviewads extends AppCompatActivity {

    private RewardedAd rewardedAd;
    private final String TAG = "RewardAd";

    // Aapke game ke coins
    private int myCoins = 0;
    private TextView tvCoinBalance;
    private Button btnWatchAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewardviewads);

        tvCoinBalance = findViewById(R.id.tv_coin_balance);
        btnWatchAd = findViewById(R.id.btn_watch_ad);

        // 1. MobileAds Initialize karein
        MobileAds.initialize(this, initializationStatus -> {});

        // 2. Ad load karna shuru karein
        loadRewardedAd();

        // 3. Button par click karne par Ad show karein
        btnWatchAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewardedAd();
            }
        });
    }

    // Ad load karne ka function
    private void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        // Ye Google AdMob ki Test ID hai
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d(TAG, loadAdError.toString());
                        rewardedAd = null;
                        Toast.makeText(Rewardviewads.this, "Ad load fail ho gaya", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d(TAG, "Ad load ho gaya hai!");
                        Toast.makeText(Rewardviewads.this, "Ad Ready Hai! Button dabayein", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Ad show karne ka aur coins dene ka function
    private void showRewardedAd() {
        if (rewardedAd != null) {
            rewardedAd.show(Rewardviewads.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // YAHAN PAR USER KO REWARD MILTA HAI

                    // Ad poora dekhne par 50 coins add karein
                    myCoins = myCoins + 50;

                    // Screen par naye coins dikhayein
                    tvCoinBalance.setText("Coins: " + myCoins);

                    Toast.makeText(Rewardviewads.this, "Aapko 50 Coins mil gaye!", Toast.LENGTH_LONG).show();

                    // Ad dekh liya, ab naya ad load karke ready rakhein
                    loadRewardedAd();
                }
            });
        } else {
            Toast.makeText(this, "Ad abhi load nahi hua hai, thoda wait karein", Toast.LENGTH_SHORT).show();
        }
    }
}