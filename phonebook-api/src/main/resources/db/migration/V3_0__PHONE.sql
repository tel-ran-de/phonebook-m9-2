CREATE TABLE public.country_code
(
    code integer,
    country character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT code_pkey PRIMARY KEY (code)
);

ALTER TABLE public.phone
     ALTER COLUMN phone_number TYPE bigint;

