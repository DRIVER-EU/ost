ALTER TABLE public.attachment
    ALTER COLUMN uri DROP NOT NULL,
    ADD COLUMN description text,
    ADD COLUMN longitude float4,
    ADD COLUMN latitude float4,
    ADD COLUMN altitude float4;