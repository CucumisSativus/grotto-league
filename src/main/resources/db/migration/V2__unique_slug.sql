ALTER TABLE player
    ADD CONSTRAINT uc_player_slug UNIQUE (slug);