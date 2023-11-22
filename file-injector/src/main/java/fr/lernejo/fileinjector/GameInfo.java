package fr.lernejo.fileinjector;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.lang.NonNull;

import java.io.Serializable;

// EXEMPLE DATA JSON
        //"id": 1,
        //"title": "Dauntless",
        //"thumbnail": "https:\/\/www.freetogame.com\/g\/1\/thumbnail.jpg",
        //"short_description": "A free-to-play, co-op action RPG with gameplay similar to Monster Hunter.",
        //"game_url": "https:\/\/www.freetogame.com\/open\/dauntless",
        //"genre": "MMORPG",
        //"platform": "PC (Windows)",
        //"publisher": "Phoenix Labs",
        //"developer": "Phoenix Labs, Iron Galaxy",
        //"release_date": "2019-05-21",
        //"freetogame_profile_url": "https:\/\/www.freetogame.com\/dauntless"

public record GameInfo(
    int id,
    String title,
    String thumbnail,
    String short_description,
    String game_url,
    String genre,
    String platform,
    String publisher,
    String developer,
    String release_date,
    String freetogame_profile_url
) implements Serializable {}
