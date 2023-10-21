ALTER TABLE match
    DROP COLUMN happened_at;

ALTER TABLE match
    ADD happened_at date;