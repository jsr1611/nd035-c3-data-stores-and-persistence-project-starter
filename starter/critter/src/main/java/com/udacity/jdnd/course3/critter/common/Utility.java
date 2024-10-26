package com.udacity.jdnd.course3.critter.common;

import java.util.List;

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
}
