ALTER TABLE match
    ADD happened_on date;

ALTER TABLE planned_match
    ADD played_on date;

ALTER TABLE match
    DROP COLUMN happened_at;