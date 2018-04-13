ALTER TABLE event ADD COLUMN trial_user_id bigint;
ALTER TABLE event ADD CONSTRAINT fkkkkaqpo3y5yxf0h6jecltd0v3 FOREIGN KEY (trial_user_id)
    REFERENCES public.trial_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE event ADD COLUMN trial_role_id bigint;
ALTER TABLE event ADD CONSTRAINT fttttaqpo3y5yxf0h6jecltd0v3 FOREIGN KEY (trial_role_id)
    REFERENCES public.trial_role (id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION