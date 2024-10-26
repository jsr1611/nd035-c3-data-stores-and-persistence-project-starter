package com.udacity.jdnd.course3.critter.common;

import com.udacity.jdnd.course3.critter.pet.Pet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Utility {
    private static final Utility INSTANCE = new Utility();
    private Utility() {}

    public static Utility getInstance(){
        return INSTANCE;
    }

    public void throwExceptionIf(List<?> itemList, String errorMessage){
        if (itemList ==null || itemList.isEmpty()){
            throw new NotFoundException(errorMessage);
        }
    }

    public void throwExceptionIf(Set<?> itemList, String errorMessage){
        if (itemList ==null || itemList.isEmpty()){
            throw new NotFoundException(errorMessage);
        }
    }

    public void throwExceptionIf(Optional<?> itemOptional, String errorMessage) {
        if (itemOptional == null || !itemOptional.isPresent()){
            throw new NotFoundException(errorMessage);
        }
    }
}
