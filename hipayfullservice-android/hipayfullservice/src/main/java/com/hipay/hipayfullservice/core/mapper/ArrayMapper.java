package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.ListBehaviour;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapBehaviour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nfillion on 28/01/16.
 */
public class ArrayMapper extends AbstractMapper {

    public ArrayMapper() {
    }

    @Override
    protected boolean isValid() {
        return  this.getBehaviour() instanceof MapBehaviour ||
                this.getBehaviour() instanceof ListBehaviour;
    }

    @Override
    protected Object mappedObject() {

        List<AbstractMapper> result = new ArrayList<>();

        //TODO handle this one

        return result;
    }
}
