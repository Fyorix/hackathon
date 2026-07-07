package com.greentrip.domain.mappers;

public interface GenericMapper<Model, Entity> {
    Entity toEntity(Model model);
    Model toModel(Entity entity);
}
