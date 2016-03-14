package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;

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
        return  this.getBehaviour() instanceof MapMapper ||
                this.getBehaviour() instanceof ListMapper;
    }

    @Override
    protected Object mappedObject() {

        List<AbstractMapper> result = new ArrayList<>();

        //TODO handle this one

        return result;
    }

    @Override
    protected Object mappedObjectFromBundle() {
        return null;
    }
}
