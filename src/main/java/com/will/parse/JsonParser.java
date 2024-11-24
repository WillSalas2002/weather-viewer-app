package com.will.parse;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonParser<K> {

    K parse(String json) throws JsonProcessingException;

}
