ALTER TABLE player
    ADD army VARCHAR(255);

ALTER TABLE match
    DROP COLUMN player1army;

ALTER TABLE match
    DROP COLUMN player2army;