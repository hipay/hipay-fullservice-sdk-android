package com.hipay.fullservice.core.mapper.interfaces;

/**
 * Created by nfillion on 22/03/2018.
 */

public interface IMapper {

    /**
     * Map and return an object from JSON Object
     *
     * @return
     */
    Object mappedObject();


    /**
     * Valid and set behaviour according the mapper
     *
     * @return boolean
     */
    boolean isValid();
}
