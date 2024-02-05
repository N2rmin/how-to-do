package com.alinem.howtodo.service.impl;

import com.alinem.howtodo.dto.requestDto.TopicRequestDto;
import com.alinem.howtodo.dto.responseDto.TopicResponseDto;
import com.alinem.howtodo.entity.Topic;
import com.alinem.howtodo.repository.TopicRepository;
import com.alinem.howtodo.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

    @Override
    public TopicResponseDto addTopic(TopicRequestDto requestDto) {
        Topic topic=modelMapper.map(requestDto,Topic.class);

        topic.setCreatedDate(new Date());
        topic.setCreatedBy("Narmin");

        topic=topicRepository.save(topic);

        return modelMapper.map(topicRepository.save(topic),TopicResponseDto.class );
    }

    @Override
    public List<TopicResponseDto> getTopics() {
        List<Topic> topics=topicRepository.findAll();
        List<TopicResponseDto> topicResponseDtos=topics.stream().map(topic -> modelMapper.map(topic,TopicResponseDto.class)).collect(Collectors.toList());
        return topicResponseDtos;
    }

    @Override
    public TopicResponseDto getTopic(Long id) {
        Optional<Topic> section = topicRepository.findById(id);
        if(section.isPresent()){
            return modelMapper.map(section.get(),TopicResponseDto.class);
        }
        throw new RuntimeException("Topic tapilmadi") ;
    }

    @Override
    public TopicResponseDto updateTopic(Long id, TopicRequestDto requestDto) {
        Optional<Topic> topic = topicRepository.findById(id);
        if(topic.isPresent()){
            topic.get().setName(requestDto.getName());
            topic.get().setUpdateDate(new Date());
            topic.get().setUpdatedBy("Narmin");


            return modelMapper.map(topicRepository.save(topic.get()),TopicResponseDto.class);
        }
        throw new RuntimeException("Topic tapilmadi") ;
    }

    @Override
    public Boolean deleteTopic(Long id) {
        Optional<Topic> topic=topicRepository.findById(id);

        if(topic.isPresent()){
            topicRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
