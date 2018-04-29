package ru.beetlesoft.clientapp.constant;

public class VkConstants {

    private static final String CLIENT_ID = "6464186";
    public static final String BASE_API_URL = "https://api.vk.com/";
    public static final String CALLBACK_URL = "https://oauth.vk.com/blank.html&";

    public static String AUTH_URL = "https://oauth.vk.com/authorize?" +
            "client_id=" + VkConstants.CLIENT_ID + "&" +
            "display=page&"+
            "redirect_uri=" + VkConstants.CALLBACK_URL + "&"+
            "scope=wall,photos&"+
            "response_type=token&" +
            "v=5.74";
}
