CREATE TABLE match_data_scouted_by (
    match_data_id character varying(255) NOT NULL,
    user_id INTEGER NOT NULL
);

INSERT INTO match_data_scouted_by (match_data_id, user_id) SELECT match_data.id, wb_user.id FROM match_data JOIN wb_user ON CONCAT(wb_user.first_name, ' ', wb_user.last_name) = match_data.scouted_by;

ALTER TABLE match_data DROP COLUMN scouted_by, ADD COLUMN scouted_by_id INTEGER;

UPDATE match_data
SET scouted_by_id=subquery.user_id
FROM (SELECT match_data_id, user_id
      FROM  match_data_scouted_by) AS subquery
WHERE match_data.id=subquery.match_data_id;