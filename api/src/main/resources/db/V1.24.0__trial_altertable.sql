ALTER TABLE trial ADD COLUMN init_id bigint;
ALTER TABLE trial ADD CONSTRAINT fkkkkaqpo4y6yxf0h6jecltd0v3 FOREIGN KEY (init_id)
    REFERENCES public.observation_type (id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;