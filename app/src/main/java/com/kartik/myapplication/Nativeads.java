package com.kartik.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class Nativeads extends AppCompatActivity {

    private NativeAd mNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nativeads); // .xml hata diya hai

        // 1. MobileAds initialize karein
        MobileAds.initialize(this, initializationStatus -> {
        });

        // 2. AdLoader banayein (Test ID use ki gayi hai)
        // 2. AdLoader banayein
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) { // Yahan method shuru hua

                        if (mNativeAd != null) {
                            mNativeAd.destroy();
                        }
                        mNativeAd = nativeAd;

                        // XML layout ko inflate karein
                        FrameLayout frameLayout = findViewById(R.id.native_ad_container);
                        NativeAdView adView = (NativeAdView) getLayoutInflater()
                                .inflate(R.layout.native_ad_layout, null);

                        // Ad ke data ko views me set karein
                        populateNativeAdView(nativeAd, adView);

                        // FrameLayout ko clear karke naya ad daal dein
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);

                    } // YAHAN method close hona chahiye
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Toast.makeText(Nativeads.this, "Ad load failed: " + adError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        // 3. Ad Load karne ki request bhejein
        adLoader.loadAd(new AdRequest.Builder().build());
    }
    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        // XML views ko unki ID se dhoondein
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));

        // Data set karein
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        if (nativeAd.getIcon() != null) {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        } else {
            adView.getIconView().setVisibility(View.GONE);
        }

        // Sabse important step: NativeAd ko NativeAdView ke sath register karna
        adView.setNativeAd(nativeAd);
    }

    @Override
    protected void onDestroy() {
        if (mNativeAd != null) {
            mNativeAd.destroy();
        }
        super.onDestroy();
    }
}
