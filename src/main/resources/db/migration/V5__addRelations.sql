ALTER TABLE part
    ADD CONSTRAINT fk_part_location FOREIGN KEY (location_id)
        REFERENCES location(id) MATCH SIMPLE;

ALTER TABLE part_vendor_information
    ADD CONSTRAINT fk_part_vendor_information_vendor FOREIGN KEY (vendor_id)
        REFERENCES vendor(id) MATCH SIMPLE
        ON DELETE CASCADE,
    ADD CONSTRAINT fk_part_vendor_information_part FOREIGN KEY (part_id)
        REFERENCES part(id) MATCH SIMPLE
        ON DELETE CASCADE;

ALTER TABLE student
    ADD CONSTRAINT fk_student_user FOREIGN KEY (wb_user_id)
    REFERENCES wb_user(id) MATCH SIMPLE
    ON DELETE CASCADE;

ALTER TABLE user_with_role
    ADD CONSTRAINT fk_user_with_role_role_id FOREIGN KEY (role_id)
        REFERENCES role(id) MATCH SIMPLE,
    ADD CONSTRAINT fk_user_with_role_user_id FOREIGN KEY (user_id)
        REFERENCES wb_user(id) MATCH SIMPLE;

ALTER TABLE wb_user
    ADD CONSTRAINT unique_email UNIQUE (email);