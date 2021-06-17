package com.utn.TPfinal.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class EntityURLBuilder{

    public static URI buildURL(String entity, Integer id)
    {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(("/{entity}/{id}"))
                .buildAndExpand(entity, id)
                .toUri();

    }

    public static URI buildURLString(String entity, String serialNumber)
    {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(("/{entity}/{serialNumber}"))
                .buildAndExpand(entity, serialNumber)
                .toUri();

    }

}
