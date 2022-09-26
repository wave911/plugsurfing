package com.plugsurfing.hometask.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Map;

@AllArgsConstructor
@Getter
@Accessors(chain = true)
@RedisHash("albumCover")
public class CoverArt {
  @Id
  private String id;
  @Indexed
  //private Map<String, Object> data;
  String value;
}
