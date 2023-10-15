ALTER TABLE player
    ADD CONSTRAINT uc_player_name UNIQUE (name);

ALTER TABLE player
    DROP COLUMN slug;