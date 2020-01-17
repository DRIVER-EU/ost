-- Added by Michal - no table modyfication needed
--ALTER TABLE public.trial_session ADD COLUMN is_manual_stage_change BOOLEAN;
alter table question alter column name type varchar(1000) using name::varchar(1000);