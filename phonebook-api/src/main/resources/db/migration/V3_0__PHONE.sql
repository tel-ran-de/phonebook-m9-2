CREATE TABLE public.code
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    code character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT code_pkey PRIMARY KEY (id)
);

ALTER TABLE public.phone
     RENAME COLUMN country_code TO country_code_Id;

ALTER TABLE public.phone
     ADD CONSTRAINT fkl1mdvx2cchi5gllrrvhe5hmv5 FOREIGN KEY (country_code_id)
         REFERENCES public.code (id) MATCH SIMPLE;

ALTER TABLE public.phone
     ALTER COLUMN phone_number TYPE bigint;

