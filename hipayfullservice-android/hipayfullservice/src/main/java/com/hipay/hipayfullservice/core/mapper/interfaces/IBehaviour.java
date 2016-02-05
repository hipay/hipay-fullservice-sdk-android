package com.hipay.hipayfullservice.core.mapper.interfaces;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by nfillion on 28/01/16.
 */
public interface IBehaviour {

    // Object?
    Object getObjectForKey(String key);

    String getStringForKey(String key);
    String getLowercaseStringForKey(String key);
    Number getNumberForKey(String key);
    Integer getIntergerForKey(String key);

    Date getDateISO8601ForKey(String key);
    Date getDateBasicForKey(String key);

    Date getDateForKey(String key);
    String getEnumCharForKey(String key);

    Boolean getBoolNumberForKey(String key);

    Boolean getBoolForKey(String key);
    Map getMapForKey(String key);

    //public getYearAndMonthForKey

    URL getURLForKey(String key);
    List getArrayFromObject(Object object);

}
