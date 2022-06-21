/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author otalo
 */
public class Util {

    public static final String WEB_SERVER_BASE_API_URL = "/api/v1/";

    public static final int DEFAULT_PAGE_LIMIT = 10;

    public static final Date formatDate(String date) {

        Date formattedDate = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

            formattedDate = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }
    
    public static final Date formatHour(String date) {

        Date formattedDate = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            formattedDate = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }
}
