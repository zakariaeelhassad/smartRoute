package org.example.smartroute.mappers.api;

public interface GenericMapper<Entity , Dto> {
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);
}
