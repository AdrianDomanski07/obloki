package com.example.obloki.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ClientService {

    String ipAddress;
    String ipInfo;
    String clientTime;

    public ClientService () {
        ipAddress = getIpAddressF();
        ipInfo = getIpInfoF(ipAddress);
        clientTime = getTime(getTimeZoneFromIpInfo(ipInfo));

    }

    private String getTime(String timeZoneIP) {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(timeZoneIP));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);

    }

    private String getIpAddressF() {
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String ipAddress = reader.readLine().trim();

            if (!ipAddress.isEmpty()) {
                return ipAddress;
            } else {
                System.out.println("BRAK");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "-1";
    }

    private String getIpInfoF(String ipAddress) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://api.ipstack.com/" + ipAddress + "?access_key=f341a578e5a05f6f2f9c1b347b12a34d";
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
    private static String getTimeZoneFromIpInfo(String ipInfo) {
        Map<String, String> timeZoneMap = createTimeZoneMap();

        //Wyobrębniamy potrzebne informacje oferowane przez API
        String[] parts = ipInfo.split("\"continent_code\":");
        if (parts.length > 1) {
            String continentCode = parts[1].trim().replace("\"", "").replace(",", "");

            parts = ipInfo.split("\"country_code\":");
            if (parts.length > 1) {
                String countryCode = parts[1].trim().replace("\"", "").replace(",", "");
                String timeZoneId = timeZoneMap.get(continentCode + countryCode);
                if (timeZoneId != null) {
                    return timeZoneId;
                }
            }
        }

        return ZoneId.systemDefault().getId(); // Ustaw domyślną strefę czasową
    }
    private static Map<String, String> createTimeZoneMap() {
        Map<String, String> timeZoneMap = new HashMap<>();
        // Dodaj tutaj mapowanie kontynentu i kraju na odpowiednią strefę czasową
        // Przykład: timeZoneMap.put("EUPL", "Europe/Warsaw");
        return timeZoneMap;
    }


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpInfo() {
        return ipInfo;
    }

    public void setIpInfo(String ipInfo) {
        this.ipInfo = ipInfo;
    }

    public String getClientTime() {
        return clientTime;
    }

    public void setClientTime(String clientTime) {
        this.clientTime = clientTime;
    }
}
