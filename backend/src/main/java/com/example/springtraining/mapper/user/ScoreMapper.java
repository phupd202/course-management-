package com.example.springtraining.mapper.user;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.example.springtraining.dto.user.ScoreDto;
import com.example.springtraining.entity.Score;

public class ScoreMapper implements Converter<Score, ScoreDto>{

    @Override
    public ScoreDto convert(MappingContext<Score, ScoreDto> context) {
        Score source = context.getSource();

        ScoreDto destination = new ScoreDto();

        destination.setScore(source.getScore());
        destination.setScoreId(source.getScoreId());
        destination.setStatus(source.getStatus());
        destination.setSubjectId(source.getSubject().getSubjectId());
        destination.setNameSubject(source.getSubject().getNameSubject());
        return destination;
    }
    
}
