CREATE TABLE public.users
(
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    is_active boolean NOT NULL,
    password character varying(255) COLLATE pg_catalog."default",
    my_profile_id integer,
    CONSTRAINT users_pkey PRIMARY KEY (email)
);

CREATE TABLE public.contact
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    description character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    user_email character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT contact_pkey PRIMARY KEY (id),
    CONSTRAINT fkf6cg7evr3j74ohnmfhogfoc1q FOREIGN KEY (user_email)
        REFERENCES public.users (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE public.users
    ADD CONSTRAINT fknrmpupovi9tb6a9lnrnm9muen FOREIGN KEY (my_profile_id)
        REFERENCES public.contact (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

CREATE TABLE public.activation_token
(
    uuid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_email character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT activation_token_pkey PRIMARY KEY (uuid),
    CONSTRAINT fkrlxrbjd34g9glfx0ob4vdgo4u FOREIGN KEY (user_email)
        REFERENCES public.users (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.address
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    city character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    street character varying(255) COLLATE pg_catalog."default",
    zip character varying(255) COLLATE pg_catalog."default",
    contact_id integer,
    CONSTRAINT address_pkey PRIMARY KEY (id),
    CONSTRAINT fk660ac8r9vntokuyh6agtj8pkh FOREIGN KEY (contact_id)
        REFERENCES public.contact (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.contact_emails
(
    contact_id integer NOT NULL,
    emails character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT fkhxco8ferpny7ohg9plnyff96e FOREIGN KEY (contact_id)
        REFERENCES public.contact (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.phone
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    country_code integer NOT NULL,
    phone_number integer NOT NULL,
    contact_id integer,
    CONSTRAINT phone_pkey PRIMARY KEY (id),
    CONSTRAINT fkcpsuk117j8h3rplllpvtgs38a FOREIGN KEY (contact_id)
        REFERENCES public.contact (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.recovery_token
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_email character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT recovery_token_pkey PRIMARY KEY (id),
    CONSTRAINT fkkjuykx32gc6pbet9fqhjt9qn8 FOREIGN KEY (user_email)
        REFERENCES public.users (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

