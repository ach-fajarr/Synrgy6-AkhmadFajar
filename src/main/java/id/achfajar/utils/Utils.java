package id.achfajar.utils;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Locale;

public class Utils {
    public static String setCurrency (int price){
        Locale locale = new Locale("id", "ID"); // Indonesia
        Currency currency = Currency.getInstance("IDR"); // Mata uang Rupiah

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        currencyFormatter.setCurrency(currency);

        String formattedPrice = currencyFormatter.format(price);
        return formattedPrice;
    }
    public static String getTimeNow(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm", Locale.US);
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }
    public static final String LINE = "==========================";
    public static final String LINE2 = "----------------------------+";
    public static final String SPACE=" ";
    public static final String TAB="\t";
    public static final String NEW_LINE="\n";
    public static final String TAB2="\t \t";
    public static final String TAB_PIPELINE="\t | ";
}
