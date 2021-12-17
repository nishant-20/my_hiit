package com.ndayal.my_hiit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndayal.my_hiit.vo.ExerciseVO;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestMapper {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("/Users/apple/Desktop/exercises.json");

        List<ExerciseVO> exerciseVOList = objectMapper.readValue(file, new TypeReference<List<ExerciseVO>>(){});

        System.out.println(FileUtils.readFileToString(file, StandardCharsets.UTF_8));

        System.out.println(exerciseVOList);
    }
}
