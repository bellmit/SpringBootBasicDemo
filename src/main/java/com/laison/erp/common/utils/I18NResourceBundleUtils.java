package com.laison.erp.common.utils;


import com.laison.erp.common.constants.ConfigConstant;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class I18NResourceBundleUtils {
    private static ResourceBundle rb;
    private static String language;
    private static HashMap<String, ResourceBundle> rbMap;

    static {
        rbMap = new HashMap<>();
        try {
            rbMap.put("zh_CN", ResourceBundle.getBundle("zh_CN"));
            rbMap.put("en_US", ResourceBundle.getBundle("en_US"));
            //rbMap.put("pt_PT", ResourceBundle.getBundle("pt_PT"));
            rbMap.put("ar_EG", ResourceBundle.getBundle("ar_EG"));
            rb = rbMap.get("ar_EG");
            language = ConfigConstant.I18N;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static boolean contains(String language) {
        return rbMap.containsKey(language);
    }

    public static String getLocalizedText(String text) {
        try {
            ResourceBundle chooseRB = chooseRB();
            String pattern = "\\{.[^{]+\\}";
            Pattern r = Pattern.compile(pattern);
            Matcher matcher = r.matcher(text);
            boolean result = matcher.find();
            if (result) {
                do {
                    String matherResult = matcher.group(0);
                    String replaceResult = chooseRB.getString(matherResult);
                    if (StringUtils.isBlank(replaceResult)) {
                        replaceResult = matherResult;
                    }
                    text = text.replace(matherResult, replaceResult);
                    result = matcher.find();
                } while (result);
                return text;
            } else {
                if (!StringUtils.startsWith(text, "{") && !TranslateSet.contains(text)) {
                    return text;
                }
                String i18nText;
                try {
                    i18nText = chooseRB.getString(text);
                    if (i18nText != null && StringUtils.isNotBlank(i18nText)) {
                        text = i18nText;
                    }
                    return text;
                } catch (Exception e) {
                    return text;
                }
            }
        } catch (Exception e) {
            return text;
        }

    }

    public static ResourceBundle chooseRB() {
        String userlanguage = LoginUserUtil.getLanguage();
        ResourceBundle resourceBundle = rbMap.get(userlanguage);
        return resourceBundle != null ? resourceBundle : rb;

    }

    public static String changeLanguage(String text) {
        if (rbMap.containsKey(text)) {
            rb = rbMap.get(text);
            language = text;
        }
        return language;
    }
}
