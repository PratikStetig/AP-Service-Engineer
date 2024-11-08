CREATE UNIQUE INDEX unique_active_template ON acknowledgment_templates (is_active) WHERE is_active = true;
