package com.dc24.tranning.Utils;


import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MergingLists {

    private static ModelMapper modelMapper = new ModelMapper();


    public static <S, T> List<T> mergeList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
