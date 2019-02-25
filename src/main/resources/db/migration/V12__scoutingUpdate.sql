CREATE TABLE team
(
    key character varying(255) NOT NULL,
    city character varying(255),
    country character varying(255),
    name text,
    nickname character varying(255),
    state_prov character varying(255),
    team_number integer NOT NULL,
    CONSTRAINT team_pkey PRIMARY KEY (key)
);

CREATE TABLE regional
(
    key character varying(255) NOT NULL,
    address character varying(255),
    city character varying(255),
    location_name character varying(255),
    name character varying(255),
    short_name character varying(255),
    start_date timestamp without time zone,
    timezone character varying(255),
    year integer NOT NULL,
    CONSTRAINT regional_pkey PRIMARY KEY (key)
);

CREATE TABLE regional_teams
(
    regional_key character varying(255) NOT NULL,
    team_key character varying(255) NOT NULL,
    CONSTRAINT regional_teams_regional_key FOREIGN KEY (regional_key)
        REFERENCES regional (key) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT regional_teams_team_key FOREIGN KEY (team_key)
        REFERENCES team (key) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE match
(
    id character varying(255) NOT NULL,
    regional_key character varying(255),
    CONSTRAINT match_pkey PRIMARY KEY (id),
    CONSTRAINT match_regional_key FOREIGN KEY (regional_key)
        REFERENCES regional (key) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE match_data
(
    id character varying(255) NOT NULL,
    alliance character varying(255) NOT NULL,
    crossed boolean NOT NULL,
    hab_level integer NOT NULL,
    "time" double precision NOT NULL,
    average_cargo double precision NOT NULL,
    average_hatch double precision NOT NULL,
    cargo_count integer NOT NULL,
    hatch_count integer NOT NULL,
    scouted_by character varying(255) NOT NULL,
    match_id character varying(255) NOT NULL,
    team_key character varying(255) NOT NULL,
    CONSTRAINT match_data_pkey PRIMARY KEY (id),
    CONSTRAINT fk_match_data_team_key FOREIGN KEY (team_key)
        REFERENCES team (key) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_match_data_match_id FOREIGN KEY (match_id)
        REFERENCES match (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE cargo
(
    match_id character varying(255) NOT NULL,
    cycle_time double precision NOT NULL,
    drop_off character varying(255),
    pickup character varying(255),
    pickup_time double precision NOT NULL,
    CONSTRAINT fk_match_cargo FOREIGN KEY (match_id)
        REFERENCES match_data (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE climb
(
    match_id character varying(255) NOT NULL,
    can_support boolean NOT NULL,
    cycle_time double precision NOT NULL,
    dropped boolean NOT NULL,
    hab_level integer NOT NULL,
    "time" double precision NOT NULL,
    CONSTRAINT fk_match_climb FOREIGN KEY (match_id)
        REFERENCES match_data (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE disconnect
(
    match_id character varying(255) NOT NULL,
    duration double precision NOT NULL,
    start_time double precision NOT NULL,
    CONSTRAINT fk_match_disconnect FOREIGN KEY (match_id)
        REFERENCES match_data (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE foul
(
    match_id character varying(255) NOT NULL,
    reason character varying(255),
    "time" double precision NOT NULL,
    CONSTRAINT fk_foul_disconnect FOREIGN KEY (match_id)
        REFERENCES match_data (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE hatch
(
    match_id character varying(255) NOT NULL,
    cycle_time double precision NOT NULL,
    drop_off character varying(255),
    pickup character varying(255),
    pickup_time double precision NOT NULL,
    CONSTRAINT fk_hatch_foul FOREIGN KEY (match_id)
        REFERENCES match_data (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.match_match_data
(
    match_id character varying(255) NOT NULL,
    match_data_id character varying(255) NOT NULL,
    CONSTRAINT uk_match_data_id UNIQUE (match_data_id)
,
    CONSTRAINT fk_match_data_id FOREIGN KEY (match_data_id)
        REFERENCES match_data (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_match_id FOREIGN KEY (match_id)
        REFERENCES match (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);