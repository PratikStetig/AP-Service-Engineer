package com.asianpaints.apse.service_engineer.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private static final  Integer EXPIRE_MIN = 30;
    private LoadingCache<String,String> otpCache;


    public OtpService(EmailService emailService) {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return "";
                    }
                });
    }

    public String generateOtp(String email){
        String otp = getRandomOTP();
        otpCache.put(email,otp);
        return  otp;
    }

    private String getRandomOTP() {
        String otp =  new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
         return otp;
    }
    //get saved otp
    public String getCacheOtp(String key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            return "";
        }
    }
    //clear stored otp
    public void clearOtp(String key){
        otpCache.invalidate(key);
    }
}
