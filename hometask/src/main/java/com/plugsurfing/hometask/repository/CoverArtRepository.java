package com.plugsurfing.hometask.repository;

import com.plugsurfing.hometask.entity.CoverArt;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RedisHash("albumCover")
public interface CoverArtRepository extends CrudRepository<CoverArt, String> {

}
