package com.bryant.mapper;

import com.bryant.model.TtsVoice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TtsVoiceMapper {

    void insert(TtsVoice ttsVoice);

    TtsVoice getById(@Param("id") Long id);
}
