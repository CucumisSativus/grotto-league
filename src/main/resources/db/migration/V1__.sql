CREATE SEQUENCE IF NOT EXISTS match_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS player_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE match
(
    id            BIGINT  NOT NULL,
    happened_at   TIMESTAMP WITHOUT TIME ZONE,
    player1_id    BIGINT,
    player1army   VARCHAR(255),
    player1points INTEGER NOT NULL,
    player2_id    BIGINT,
    player2army   VARCHAR(255),
    player2points INTEGER NOT NULL,
    CONSTRAINT pk_match PRIMARY KEY (id)
);

CREATE TABLE player
(
    id       BIGINT NOT NULL,
    name     VARCHAR(255),
    added_at TIMESTAMP WITHOUT TIME ZONE,
    slug     VARCHAR(255),
    CONSTRAINT pk_player PRIMARY KEY (id)
);

ALTER TABLE match
    ADD CONSTRAINT FK_MATCH_ON_PLAYER1 FOREIGN KEY (player1_id) REFERENCES player (id);

ALTER TABLE match
    ADD CONSTRAINT FK_MATCH_ON_PLAYER2 FOREIGN KEY (player2_id) REFERENCES player (id);