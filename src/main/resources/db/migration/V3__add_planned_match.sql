CREATE SEQUENCE IF NOT EXISTS planned_match_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE planned_match
(
    id          BIGINT NOT NULL,
    player1_id  BIGINT,
    player2_id  BIGINT,
    happened_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_plannedmatch PRIMARY KEY (id)
);

ALTER TABLE planned_match
    ADD CONSTRAINT FK_PLANNEDMATCH_ON_PLAYER1 FOREIGN KEY (player1_id) REFERENCES player (id);

ALTER TABLE planned_match
    ADD CONSTRAINT FK_PLANNEDMATCH_ON_PLAYER2 FOREIGN KEY (player2_id) REFERENCES player (id);